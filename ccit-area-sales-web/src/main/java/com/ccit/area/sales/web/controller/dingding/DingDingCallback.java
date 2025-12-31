package com.ccit.area.sales.web.controller.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ccit.area.sales.common.dingding.constant.RabbitmqConstant;
import com.ccit.area.sales.common.dingding.utils.DingCallbackCrypto;
import com.ccit.area.sales.common.dingding.utils.HttpClientUtil;
import com.ccit.area.sales.dao.dingding.po.*;
import com.ccit.area.sales.dao.dingding.vo.GetApprovalLivingExample;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.service.dingding.ApprovalProcessTemplatesService;
import com.ccit.area.sales.service.dingding.ApproverInformationService;
import com.ccit.area.sales.service.dingding.ExamineService;
import com.ccit.area.sales.service.dingding.ProcessOperationService;
import com.ccit.area.sales.service.marketing.IOrderBiddingService;
import com.ccit.area.sales.service.system.ISystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Tag(name = "OA审批管理")
@RestController
@RequestMapping(("/webapi/system/callback"))
public class DingDingCallback {

    @Value("${dingding.aes_key}")
    private String aesKey;
    @Value("${dingding.aes_token}")
    private String aesToken;
    @Value("${dingding.owner_key}")
    private String ownerKey;

    @Value("${dingding.get_approval_living_example}")
    private String approvalLivingExample;

    @Autowired
    private ApproverInformationService informationService;
    @Autowired
    private ProcessOperationService tProcessOperationService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private ApprovalProcessTemplatesService templatesService;


    @Autowired
    private IOrderBiddingService orderBiddingService;

    /**
     * 钉钉审批回调
     * * @param callback 钉钉回调请求参数
     */
    @Operation(summary = "审批", description = "钉钉OA审批结束回调")
    @PostMapping("/ApprovalFinishCallback")
    public Map<String, String> ApprovalFinishCallback(@RequestParam(value = "msg_signature", required = false) String msg_signature,
                                                      @RequestParam(value = "timestamp", required = false) String timeStamp,
                                                      @RequestParam(value = "nonce", required = false) String nonce,
                                                      @RequestBody(required = false) JSONObject json) {

        try {
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(aesToken, aesKey, ownerKey);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);
            BpmTaskChangeEvent bpmTaskChangeEvent = JSON.parseObject(decryptMsg, BpmTaskChangeEvent.class);

            if (bpmTaskChangeEvent == null) {
                // 返回success的加密数据
                return callbackCrypto.getEncryptedMap("success");
            } else if (bpmTaskChangeEvent.getType() == null) {
                // 返回success的加密数据
                return callbackCrypto.getEncryptedMap("success");
            }
            // 1. 判断事件类型
            if ("bpms_task_change".equals(bpmTaskChangeEvent.getEventType())) {
                LambdaQueryWrapper<SystemUserPO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SystemUserPO::getDingdingUserId, bpmTaskChangeEvent.getStaffId());
                SystemUserPO systemUserPO = iSystemUserService.getOne(queryWrapper);
                //判断审批人是否存在系统中
                if (systemUserPO != null) {

                    log.info("审批流回调解密参数: {}", bpmTaskChangeEvent);

                    LambdaQueryWrapper<TProcessOperation> operationLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    operationLambdaQueryWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                    operationLambdaQueryWrapper.eq(TProcessOperation::getProcessCode, bpmTaskChangeEvent.getProcessCode());
                    TProcessOperation operationServiceOne = tProcessOperationService.getOne(operationLambdaQueryWrapper);
                    if (operationServiceOne != null) {

                        Map<String, String> header = new HashMap<>();
                        header.put("x-acs-dingtalk-access-token", this.examineService.getToken().getAccessToken());
                        String sendGet = HttpClientUtil.sendGet(approvalLivingExample + "?processInstanceId=" + bpmTaskChangeEvent.getProcessInstanceId(), header);
                        GetApprovalLivingExample getApprovalLivingExample = JSON.parseObject(sendGet, GetApprovalLivingExample.class);
                        log.info("实例详情响应参数:{}", getApprovalLivingExample);

                        //判断回调类型为已发起
                        if ("start".equals(bpmTaskChangeEvent.getType())) {
                            LambdaQueryWrapper<TApproverInformationPO> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(TApproverInformationPO::getApproverStatus, "YFQ");
                            wrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                            TApproverInformationPO informationPO = informationService.getOne(wrapper);

                            if (informationPO == null) {
                                //查询审批人信息
                                LambdaQueryWrapper<SystemUserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                userPOLambdaQueryWrapper.eq(SystemUserPO::getUserName, operationServiceOne.getUserName());
                                SystemUserPO userServiceOne = iSystemUserService.getOne(userPOLambdaQueryWrapper);
                                //新增审批人信息表 发起人信息
                                TApproverInformationPO tApproverInformation = new TApproverInformationPO();
                                tApproverInformation.setUserName(userServiceOne.getRealName());
                                tApproverInformation.setUserCode(userServiceOne.getUserName());
                                tApproverInformation.setDingdingCode(userServiceOne.getDingdingUserId());
                                tApproverInformation.setApproverId(bpmTaskChangeEvent.getProcessInstanceId());
                                tApproverInformation.setApproverStatus("YFQ");
                                tApproverInformation.setReviseName(userServiceOne.getRealName());
                                tApproverInformation.setTaskIdStatus(1);
                                tApproverInformation.setLevel(1);
                                tApproverInformation.setBusinessName(operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                tApproverInformation.setStatus("同意");
                                tApproverInformation.setTaskId(0L);
                                tApproverInformation.setApprovelType("发起人");
                                informationService.save(tApproverInformation);

                                //新增审批人信息表 审批人信息
                                getApprovalLivingExample.getResult().getTasks().stream().forEach(t -> {
                                    if ("NONE".equals(t.getResult())) {
                                        LambdaQueryWrapper<SystemUserPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                                        lambdaQueryWrapper.eq(SystemUserPO::getDingdingUserId, t.getUserId());
                                        SystemUserPO systemUserServiceOne = iSystemUserService.getOne(lambdaQueryWrapper);
                                        TApproverInformationPO tApproverInformationPO = new TApproverInformationPO();
                                        tApproverInformationPO.setUserName(systemUserServiceOne.getRealName());
                                        tApproverInformationPO.setUserCode(systemUserServiceOne.getUserName());
                                        tApproverInformationPO.setDingdingCode(systemUserServiceOne.getDingdingUserId());
                                        tApproverInformationPO.setApproverId(bpmTaskChangeEvent.getProcessInstanceId());
                                        tApproverInformationPO.setApproverStatus("SPZ");
                                        tApproverInformationPO.setApprovelType("审批人");
                                        tApproverInformationPO.setReviseName(systemUserServiceOne.getRealName());
                                        tApproverInformationPO.setEventId("");
                                        tApproverInformationPO.setLevel(2);
                                        tApproverInformationPO.setBusinessName(operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                        tApproverInformationPO.setTaskId(t.getTaskId());
                                        tApproverInformationPO.setRemarkCode(t.getActivityId());
                                        informationService.save(tApproverInformationPO);
                                    }
                                });

                                //更新流程操作表状态
                                LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                lambdaUpdateWrapper.set(TProcessOperation::getOperation, "SPZ");
                                lambdaUpdateWrapper.set(TProcessOperation::getLevel, 2);
                                tProcessOperationService.update(lambdaUpdateWrapper);

                                //流程结束后发送消息异步修改状态
                                try {
                                    LambdaQueryWrapper<TProcessOperation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                                    lambdaQueryWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                    TProcessOperation operation = tProcessOperationService.getOne(lambdaQueryWrapper);
                                    RabbitApprovalRequest rabbitApprovalRequest = new RabbitApprovalRequest();
                                    rabbitApprovalRequest.setId(operation.getBusinessId());
                                    rabbitApprovalRequest.setDatabaseName(operation.getDataName());
                                    rabbitApprovalRequest.setApprovalStatus(operation.getOperation());
                                    rabbitApprovalRequest.setStatus("审核中");
                                    rabbitApprovalRequest.setWorkflowId(operation.getApprovalId());
                                    rabbitApprovalRequest.setBusinessName(operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                    rabbitTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_DIRECT, RabbitmqConstant.ROUTING_KEY, rabbitApprovalRequest);
                                } catch (AmqpException e) {
                                    log.error("发送MQ消息出错,无法发送消息", e);
                                }
                            }

                        } else if ("finish".equals(bpmTaskChangeEvent.getType())) {
                            //回调类型为审批中
                            if ("RUNNING".equals(getApprovalLivingExample.getResult().getStatus())) {
                                //判断当前节点类型
                                LambdaQueryWrapper<ApprovalProcessTemplatesPO> poLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                poLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getBusinessName, operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                poLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getCompanyNo, operationServiceOne.getCompanyNo());
                                poLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getLevel, operationServiceOne.getLevel());
                                poLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getType, "HQ");
                                List<ApprovalProcessTemplatesPO> templatesPO = templatesService.list(poLambdaQueryWrapper);
                                //判断审批类型是否为会签节点
                                if (templatesPO == null) {
                                    //非会签节点 更新当前节点流程操作表状态
                                    LambdaUpdateWrapper<TApproverInformationPO> updateWrapper = new LambdaUpdateWrapper<>();
                                    updateWrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                    updateWrapper.eq(TApproverInformationPO::getTaskIdStatus, 0);
                                    updateWrapper.set(TApproverInformationPO::getTaskIdStatus, 1);
                                    updateWrapper.set(TApproverInformationPO::getStatus, "同意");
                                    informationService.update(updateWrapper);

                                    //审批可能为钉钉转页面审批 手动初始化状态值.
                                    int lever = operationServiceOne.getLevel() + 1;

                                    //不为空为会签节点
                                    getApprovalLivingExample.getResult().getTasks().stream().forEach(t -> {
                                        if ("NONE".equals(t.getResult())) {
                                            LambdaQueryWrapper<SystemUserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                            userPOLambdaQueryWrapper.eq(SystemUserPO::getDingdingUserId, t.getUserId());
                                            SystemUserPO userServiceOne = iSystemUserService.getOne(userPOLambdaQueryWrapper);

                                            TApproverInformationPO tApproverInformationPO = new TApproverInformationPO();
                                            tApproverInformationPO.setUserName(userServiceOne.getRealName());
                                            tApproverInformationPO.setUserCode(userServiceOne.getUserName());
                                            tApproverInformationPO.setDingdingCode(userServiceOne.getDingdingUserId());
                                            tApproverInformationPO.setApproverId(bpmTaskChangeEvent.getProcessInstanceId());
                                            tApproverInformationPO.setApproverStatus("SPZ");
                                            tApproverInformationPO.setApprovelType("审批人");
                                            tApproverInformationPO.setReviseName(userServiceOne.getRealName());
                                            tApproverInformationPO.setEventId("");
                                            tApproverInformationPO.setLevel(lever);
                                            tApproverInformationPO.setBusinessName(operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                            tApproverInformationPO.setTaskId(t.getTaskId());
                                            tApproverInformationPO.setRemarkCode(t.getActivityId());
                                            informationService.save(tApproverInformationPO);
                                        }
                                    });
                                    //更新节点信息
                                    LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                    lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                    lambdaUpdateWrapper.set(TProcessOperation::getLevel, lever);
                                    tProcessOperationService.update(lambdaUpdateWrapper);
                                } else {
                                    //当前为会签节点 更新节点状态
                                    getApprovalLivingExample.getResult().getTasks().stream().forEach(t -> {
                                        if ("AGREE".equals(t.getResult())) {
                                            LambdaUpdateWrapper<TApproverInformationPO> updateWrapper = new LambdaUpdateWrapper<>();
                                            updateWrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                            updateWrapper.eq(TApproverInformationPO::getTaskId, t.getTaskId());
                                            updateWrapper.eq(TApproverInformationPO::getTaskIdStatus, 0);
                                            updateWrapper.set(TApproverInformationPO::getTaskIdStatus, 1);
                                            updateWrapper.set(TApproverInformationPO::getStatus, "同意");
                                            informationService.update(updateWrapper);
                                        }
                                    });
                                    // 检查节点是否全部同意
                                    LambdaQueryWrapper<TApproverInformationPO> informationPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                    informationPOLambdaQueryWrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                    informationPOLambdaQueryWrapper.eq(TApproverInformationPO::getLevel, operationServiceOne.getLevel());
                                    informationPOLambdaQueryWrapper.eq(TApproverInformationPO::getTaskIdStatus, 1);
                                    List<TApproverInformationPO> list = informationService.list(informationPOLambdaQueryWrapper);

                                    LambdaQueryWrapper<ApprovalProcessTemplatesPO> processTemplatesPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                    processTemplatesPOLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getBusinessName, operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                    processTemplatesPOLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getCompanyNo, operationServiceOne.getCompanyNo());
                                    processTemplatesPOLambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getLevel, operationServiceOne.getLevel());
                                    List<ApprovalProcessTemplatesPO> processTemplatesPOS = templatesService.list(processTemplatesPOLambdaQueryWrapper);

                                    // 全部同意后新增下一级节点
                                    if (processTemplatesPOS.size() == list.size()) {

                                        int level = operationServiceOne.getLevel() + 1;
                                        //更新节点
                                        LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                        lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                        lambdaUpdateWrapper.set(TProcessOperation::getLevel, level);
                                        tProcessOperationService.update(lambdaUpdateWrapper);
                                        //查询下一级节点类型
                                        LambdaQueryWrapper<ApprovalProcessTemplatesPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                                        lambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getBusinessName, operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                        lambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getCompanyNo, operationServiceOne.getCompanyNo());
                                        lambdaQueryWrapper.eq(ApprovalProcessTemplatesPO::getLevel, level);
                                        List<ApprovalProcessTemplatesPO> template = templatesService.list(lambdaQueryWrapper);

                                        if (template != null) {
                                            //下一级节点为会签节点
                                            getApprovalLivingExample.getResult().getTasks().stream().forEach(t -> {
                                                if ("NONE".equals(t.getResult())) {
                                                    LambdaQueryWrapper<SystemUserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                                    userPOLambdaQueryWrapper.eq(SystemUserPO::getDingdingUserId, t.getUserId());
                                                    SystemUserPO userServiceOne = iSystemUserService.getOne(userPOLambdaQueryWrapper);

                                                    TApproverInformationPO tApproverInformationPO = new TApproverInformationPO();
                                                    tApproverInformationPO.setUserName(userServiceOne.getRealName());
                                                    tApproverInformationPO.setUserCode(userServiceOne.getUserName());
                                                    tApproverInformationPO.setDingdingCode(userServiceOne.getDingdingUserId());
                                                    tApproverInformationPO.setApproverId(bpmTaskChangeEvent.getProcessInstanceId());
                                                    tApproverInformationPO.setApproverStatus("SPZ");
                                                    tApproverInformationPO.setReviseName(userServiceOne.getRealName());
                                                    tApproverInformationPO.setEventId("");
                                                    tApproverInformationPO.setLevel(level);
                                                    tApproverInformationPO.setApprovelType("审批人");
                                                    tApproverInformationPO.setBusinessName(operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess());
                                                    tApproverInformationPO.setTaskId(t.getTaskId());
                                                    tApproverInformationPO.setRemarkCode(t.getActivityId());
                                                    informationService.save(tApproverInformationPO);
                                                }
                                            });
                                        }
                                    }
                                }

                                //更新流程操作表状态
                                LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                lambdaUpdateWrapper.set(TProcessOperation::getOperation, "SPZ");
                                tProcessOperationService.update(lambdaUpdateWrapper);
                            } else if ("TERMINATED".equals(getApprovalLivingExample.getResult().getStatus())) {

                                LambdaUpdateWrapper<TApproverInformationPO> wrappers = new LambdaUpdateWrapper<>();
                                wrappers.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                wrappers.eq(TApproverInformationPO::getTaskIdStatus, 0);
                                wrappers.set(TApproverInformationPO::getTaskIdStatus, 1);
                                wrappers.set(TApproverInformationPO::getStatus, "拒绝");
                                informationService.update(wrappers);

                                LambdaUpdateWrapper<TApproverInformationPO> updateWrapper = new LambdaUpdateWrapper<>();
                                updateWrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                updateWrapper.set(TApproverInformationPO::getApproverStatus, "YJJ");
                                informationService.update(updateWrapper);

                                //更新流程操作表状态
                                LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                lambdaUpdateWrapper.set(TProcessOperation::getOperation, "YJJ");
                                lambdaUpdateWrapper.set(TProcessOperation::getGmtModified, LocalDateTime.now());
                                tProcessOperationService.update(lambdaUpdateWrapper);

                                //流程结束后发送消息异步修改状态
                                try {
                                    RabbitApprovalRequest rabbitApprovalRequest = new RabbitApprovalRequest();
                                    rabbitApprovalRequest.setId(operationServiceOne.getBusinessId());
                                    rabbitApprovalRequest.setDatabaseName(operationServiceOne.getDataName());
                                    rabbitApprovalRequest.setApprovalStatus("YJJ");
                                    rabbitApprovalRequest.setStatus("审核拒绝");
                                    rabbitTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_DIRECT, RabbitmqConstant.ROUTING_KEY, rabbitApprovalRequest);
                                } catch (AmqpException e) {
                                    log.error("发送MQ消息出错,无法发送消息", e);
                                }

                            } else if ("COMPLETED".equals(getApprovalLivingExample.getResult().getStatus())) {

                                LambdaUpdateWrapper<TApproverInformationPO> wrappers = new LambdaUpdateWrapper<>();
                                wrappers.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                wrappers.eq(TApproverInformationPO::getTaskIdStatus, 0);
                                wrappers.set(TApproverInformationPO::getTaskIdStatus, 1);
                                if ("refuse".equals(getApprovalLivingExample.getResult().getResult())) {
                                    wrappers.set(TApproverInformationPO::getStatus, "拒绝");
                                } else {
                                    wrappers.set(TApproverInformationPO::getStatus, "同意");
                                }
                                informationService.update(wrappers);
                                LambdaUpdateWrapper<TApproverInformationPO> updateWrapper = new LambdaUpdateWrapper<>();
                                updateWrapper.eq(TApproverInformationPO::getApproverId, bpmTaskChangeEvent.getProcessInstanceId());
                                if ("refuse".equals(getApprovalLivingExample.getResult().getResult())) {
                                    updateWrapper.set(TApproverInformationPO::getApproverStatus, "YJJ");
                                } else {
                                    updateWrapper.set(TApproverInformationPO::getApproverStatus, "YTG");
                                }
                                informationService.update(updateWrapper);

                                //更新流程操作表状态
                                LambdaUpdateWrapper<TProcessOperation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                                lambdaUpdateWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                if ("refuse".equals(getApprovalLivingExample.getResult().getResult())) {
                                    lambdaUpdateWrapper.set(TProcessOperation::getOperation, "YJJ");
                                } else {
                                    lambdaUpdateWrapper.set(TProcessOperation::getOperation, "YTG");
                                }
                                lambdaUpdateWrapper.set(TProcessOperation::getGmtModified, LocalDateTime.now());
                                tProcessOperationService.update(lambdaUpdateWrapper);

                                LambdaQueryWrapper<TProcessOperation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                                lambdaQueryWrapper.eq(TProcessOperation::getApprovalId, bpmTaskChangeEvent.getProcessInstanceId());
                                TProcessOperation processOperationServiceOne = tProcessOperationService.getOne(lambdaQueryWrapper);

                                //流程结束后发送消息异步修改状态
                                try {
                                    RabbitApprovalRequest rabbitApprovalRequest = new RabbitApprovalRequest();
                                    rabbitApprovalRequest.setId(processOperationServiceOne.getBusinessId());
                                    rabbitApprovalRequest.setDatabaseName(processOperationServiceOne.getDataName());
                                    rabbitApprovalRequest.setApprovalStatus(processOperationServiceOne.getOperation());
                                    rabbitApprovalRequest.setBusinessName(processOperationServiceOne.getParentName() + "_" + processOperationServiceOne.getTechnologicalProcess());
                                    if ("竞价管理_提交".equals(processOperationServiceOne.getParentName() + "_" + processOperationServiceOne.getTechnologicalProcess())) {

                                        if ("refuse".equals(getApprovalLivingExample.getResult().getResult())) {
                                            rabbitApprovalRequest.setStatus("审核拒绝");
                                        } else {
                                            rabbitApprovalRequest.setStatus("报名中");
                                        }

                                    } else {

                                        if ("refuse".equals(getApprovalLivingExample.getResult().getResult())) {
                                            rabbitApprovalRequest.setStatus("审核拒绝");
                                        } else {
                                            rabbitApprovalRequest.setStatus("审核完成");
                                        }

                                    }
                                    rabbitTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_DIRECT, RabbitmqConstant.ROUTING_KEY, rabbitApprovalRequest);
                                    if ("竞价结果管理_提交".equals(rabbitApprovalRequest.getBusinessName())){
                                        if ("YTG".equals(rabbitApprovalRequest.getApprovalStatus())){
                                            String order = null;
                                            try {
                                                order = orderBiddingService.createOrder(rabbitApprovalRequest.getId());
                                                log.info("订单创建状态:{}",order);
                                            } catch (Exception e) {
                                                log.error("订单创建失败!",e);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    log.error("发送MQ消息出错,无法发送消息", e);
                                }
                            }
                        }

                    }
                }

            }
            // 返回success的加密数据
            return callbackCrypto.getEncryptedMap("success");
        } catch (
                Exception e) {
            log.error("审批流回调失败 请求入参 msg_signature:{} timestamp:{} nonce:{} JSONObject:{}", msg_signature, timeStamp, nonce, json,e);
        }
        return null;
    }

}
