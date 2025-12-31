package com.ccit.area.sales.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.dingding.constant.Constant;
import com.ccit.area.sales.common.dingding.utils.HttpClientUtil;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.dao.dingding.dto.ApprovalProcessTemplatesDTO;
import com.ccit.area.sales.dao.dingding.dto.ManualOperationApproveDTO;
import com.ccit.area.sales.dao.dingding.dto.TApprovalDictionaryDTO;
import com.ccit.area.sales.dao.dingding.dto.TApproverInformationDTO;
import com.ccit.area.sales.dao.dingding.po.*;
import com.ccit.area.sales.dao.dingding.vo.*;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.dto.ExamineApprovalRequests;
import com.ccit.area.sales.service.dingding.*;
import com.ccit.area.sales.service.system.ISystemUserService;
import com.ccit.area.sales.vo.ResultData;
import com.ccit.area.sales.vo.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Tag(name = "OA审批管理")
@RequestMapping(value = "/webapi/system/examine")
public class SystemExamineController {
    @Autowired
    private RedisUtils redisUtils;

    @Value("${dingding.get_datp_list}")
    private String getDetpList;

    @Value("${dingding.get_approval_living_example}")
    private String approvalLivingExample;

    @Value("${dingding.manual_approval}")
    private String manualApproval;

    @Value("${dingding.get_approval_node_page}")
    private String getApprovalNodePage;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private ProcessOperationService tProcessOperationService;

    @Autowired
    private ApprovalDictionaryService tApprovalDictionaryService;

    @Autowired
    private ApproverInformationService approverInformationService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ApprovalProcessTemplatesService approvalProcessTemplatesService;

    // 审批接口方法
    @Operation(summary = "审批", description = "钉钉OA审批接口")
    @PostMapping("/examineApproval")
    public ResultResponse examineApproval(@RequestBody ExamineApprovalRequests examineApprovalRequests) {
        // 初始化响应对象
        ResultResponse resultResponse = new ResultResponse();

        // 参数校验
        if (examineApprovalRequests == null) {
            // 若请求体为空，则返回错误代码和参数异常消息
            resultResponse.setCode(Constant.ERROR_CODE);
            resultResponse.setMessages(Constant.PARAMETER_EXCEPTION_MESSAGE);
            return resultResponse;
        } else if (examineApprovalRequests.getBusinessName() == null || examineApprovalRequests.getDatabaseName() == null || examineApprovalRequests.getCompanyNo() == null) {
            // 若业务名称、数据库名称或公司编号为空，则返回错误代码和参数异常消息
            resultResponse.setCode(Constant.ERROR_CODE);
            resultResponse.setMessages(Constant.PARAMETER_EXCEPTION_MESSAGE);
            return resultResponse;
        } else if (CollectionUtils.isEmpty(examineApprovalRequests.getData().getIds())) {
            // 若业务ID列表为空，则返回错误代码和参数缺失消息
            resultResponse.setCode(Constant.ERROR_CODE);
            resultResponse.setMessages(Constant.PARAMETER_MISSING_MESSAGE);
            return resultResponse;
        } else {
            // 初始化结果数据列表
            List<ResultData> resultDataList = new ArrayList<>();
            // 遍历业务ID列表
            for (Long businessId : examineApprovalRequests.getData().getIds()) {
                try {
                    // 获取字段映射
                    TApprovalDictionaryPO approvalDictionary = getFieldMappings(examineApprovalRequests.getBusinessName());
                    // 创建钉钉OA审批实例
                    CreateLivingExampleResponse approvalLivingExample = examineService.createApprovalLivingExample(businessId, examineApprovalRequests.getDatabaseName(), examineApprovalRequests.getBusinessName(), examineApprovalRequests.getCompanyNo(), examineApprovalRequests.getUserName(), approvalDictionary);
                    // 初始化结果数据对象
                    ResultData resultData = new ResultData();
                    // 根据审批实例状态设置结果数据
                    resultData.setResultCode(Constant.SUCCESS_CODE);
                    resultData.setResultMessages("提交成功");
                    resultData.setApprovalId(approvalLivingExample.getInstanceId());
                    resultData.setBusinessId(businessId);
                    // 插入审批流程操作记录到数据库
                    this.insertProcessOperations(examineApprovalRequests.getDatabaseName(), examineApprovalRequests.getBusinessName(), examineApprovalRequests.getCompanyNo(), examineApprovalRequests.getUserName(), businessId, approvalLivingExample);
                    // 将结果数据添加到列表中
                    resultDataList.add(resultData);
                } catch (Exception ex) {
                    // 若其他异常，则记录日志并添加失败结果数据
                    log.error("处理审批提交过程中发生未知错误 审配类型: {} 审批ID: {} 错误信息: {}", examineApprovalRequests.getBusinessName(), businessId, ex);
                    ResultData resultData = new ResultData();
                    resultData.setResultCode(Constant.ERROR_CODE);
                    resultData.setResultMessages("提交钉钉过程中出错! 业务类型: "+examineApprovalRequests.getBusinessName()+"审批id: "+businessId);
                    resultData.setBusinessId(businessId);
                    resultDataList.add(resultData);
                }
            }
            // 设置成功响应并返回结果数据列表
            resultResponse.setCode(Constant.SUCCESS_CODE);
            resultResponse.setMessages("请求成功");
            resultResponse.setData(resultDataList);
        }
        return resultResponse;
    }

    // 新增审批流程记录方法
    public void insertProcessOperations(String databaseName, String businessName, String companyNo, String userName, Long businessId, CreateLivingExampleResponse approvalLivingExample) {
        try {
            // 获取当前日期和时间
            Date currentDate = getCurrentDate();
            // 获取标识
            String[] split = businessName.split("_");
            // 构建并保存审批流程操作记录
            TProcessOperation processOperation = TProcessOperation.builder()
                    .parentName(split[0])
                    .technologicalProcess(split[1])
                    .operatorName(approvalLivingExample.getRealName())
                    .userName(userName)
                    .dataName(databaseName)
                    .operatorTime(currentDate)
                    .operation("YFQ")
                    .gmtCreate(currentDate)
                    .gmtModified(currentDate)
                    .processCode(approvalLivingExample.getProcessCode())
                    .approvalId(approvalLivingExample.getInstanceId())
                    .businessId(businessId)
                    .orderNumber(approvalLivingExample.getOrderNumber())
                    .companyNo(companyNo)
                    .build();
            this.tProcessOperationService.save(processOperation);
        } catch (Exception e) {
            log.error("新增审批流程记录失败, 业务类型: {} 业务ID: {} 错误信息: {}", businessName, businessId, e);
            throw new BusinessException("新增流程操作失败");
        }
    }


    //获取字典值map
    public TApprovalDictionaryPO getFieldMappings(String businessName) {
        LambdaQueryWrapper<TApprovalDictionaryPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TApprovalDictionaryPO::getBusiness, businessName);
        return tApprovalDictionaryService.getOne(queryWrapper);
    }

    //获取当前时间.
    private Date getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    //    @Permissions(value = "approvalLivingExample:createTemplate")
    @Operation(summary = "审批", description = "新增审批模板")
    @PostMapping("/createTemplate")
    public CreateTemplateVO createTemplate(@RequestParam String businessName,
                                           @RequestParam String companyName,
                                           @RequestParam String companyNo) {
        return examineService.createTemplate(businessName, companyName, companyNo);
    }

    @Operation(summary = "审批", description = "新增审批人信息")
    @PostMapping("/addApproverInformation")
    public ResponseVO<String> addApproverInformation(@RequestBody TApproverInformationDTO tApproverInformationDTO) {
        TApproverInformationPO tApproverInformationPO = new TApproverInformationPO();
        BeanUtils.copyProperties(tApproverInformationDTO, tApproverInformationPO);
        return ResponseVO.success(approverInformationService.addApproverInformation(tApproverInformationPO));
    }

    //格式化日期
    private String formattedDate(String date) {
        // 将 ISO 8601 时间字符串转换为 LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        // 格式化输出
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    @Operation(summary = "审批", description = "查询根部门下,所有子部门列表")
    @PostMapping("/selectRootDeptList")
    public RootDeptListVO SelectRootDeptList() {
        RootDeptListVO rootDeptListVO = new RootDeptListVO();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dept_id", 1);
            String httpPost = HttpClientUtil.sendPost(getDetpList + "?access_token=" + this.examineService.getToken().getAccessToken(), jsonObject.toString());
            DeptListVO deptListVO = JSONObject.parseObject(httpPost, DeptListVO.class);
            log.info("父部门响应参数:{}", deptListVO);
            rootDeptListVO.setCode("200");
            rootDeptListVO.setMessages("查询成功");
            if (deptListVO != null && !CollectionUtils.isEmpty(deptListVO.getResult())) {
                List<Department> departmentList = deptListVO.getResult().stream().map(t -> {
                    Department department = Department.builder()
                            .deptId(t.getDeptId())
                            .name(t.getName())
                            .nodeStatus(true)
                            .build();
                    return department;
                }).collect(Collectors.toList());
                rootDeptListVO.setDepartmentList(departmentList);
            } else {
                List<Department> departmentList = new ArrayList<>();
                departmentList.add(Department.builder()
                        .nodeStatus(false)
                        .build());
                rootDeptListVO.setDepartmentList(departmentList);
            }
        } catch (Exception e) {
            log.error("查询失败:{}", e.getMessage());
            rootDeptListVO.setCode("500");
            rootDeptListVO.setMessages(e.getMessage());
        }
        return rootDeptListVO;
    }

    @Operation(summary = "审批", description = "根据父部门Id,查询所有子部门列表")
    @PostMapping("/selectDeptList")
    public RootDeptListsVO SelectDeptList(@RequestBody DeptListPO deptListPO) {

        RootDeptListsVO rootDeptListVO = new RootDeptListsVO();
        try {
            rootDeptListVO.setCode("200");
            rootDeptListVO.setMessages("查询成功");
            List<RootDeptListVO> rootDeptListVOList = examineService.selectDeptList(deptListPO);
            rootDeptListVO.setDepartmentLists(rootDeptListVOList);
            return rootDeptListVO;
        } catch (Exception e) {
            log.error("查询子部门列表失败:{}", e.getMessage());
            rootDeptListVO.setCode("500");
            rootDeptListVO.setMessages(e.getMessage());
            return rootDeptListVO;
        }
    }

    /**
     * 审批操作接口，用于获取当前用户待处理的审批实例详情
     * 此方法首先获取当前用户信息，然后根据用户ID查询待处理的审批实例，
     * 并丰富每个审批实例的详细信息，如审批状态和流程分类等
     *
     * @return 返回一个包含审批实例详情列表的ResponseVO对象
     */
    @Operation(summary = "审批", description = "根据用户id查询待处理审批实例")
    @GetMapping("/getApproveDetail")
    public ResponseVO<List<TApproverInformationVO>> getApproveDetail() {
        try {
            // 获取当前用户信息
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            SystemCurrentUserVO systemCurrentUserVO = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemCurrentUserVO.getUserName());

            // 创建查询请求对象并设置用户代码
            ToBeReviewedPageRequest entity = new ToBeReviewedPageRequest();
            entity.setUserCode(selectUserDetails.getUserName());

            // 调用服务方法获取审批详情列表
            List<TApproverInformationVO> approverDetail = approverInformationService.getApproverDetail(entity);

            // 处理并丰富审批详情信息
            List<TApproverInformationVO> informationVOList = approverDetail.stream().map(t -> {
                // 设置审批状态
                t.setApproverStatus(redisUtils.getDict("approval_", t.getApproverStatus()));
                // 查询并设置流程分类信息
                LambdaQueryWrapper<TProcessOperation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(TProcessOperation::getApprovalId, t.getApproverId());
                TProcessOperation operation = tProcessOperationService.getOne(lambdaQueryWrapper);

                t.setProcessClassification(operation.getParentName());
                t.setProcessInstanceName("请审批_" + operation.getOperatorName() + "提交的" + operation.getParentName() + "审批");
                t.setBusinessId(operation.getBusinessId());
                t.setOrderNumber(operation.getOrderNumber());
                t.setBusinessName(operation.getParentName() + "_" + operation.getTechnologicalProcess());

                // 格式化日期时间
                LocalDateTime localDateTime = operation.getGmtCreate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = localDateTime.format(formatter);

                t.setCreateTime(formattedDate);
                return t;
            }).collect(Collectors.toList());

            return ResponseVO.success(informationVOList);
        } catch (Exception e) {
            // 记录错误日志并返回成功但无数据的响应
            log.error("查询待审批实例信息失败: {}", e.getMessage());
            return ResponseVO.success(null);
        }
    }

    @Operation(summary = "审批", description = "根据用户id查询已办审批实例")
    @GetMapping("/processedApproveDetail")
    public ResponseVO<List<TApproverInformationVO>> processedApproveDetail() {

        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            SystemCurrentUserVO systemCurrentUserVO = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemCurrentUserVO.getUserName());

            ToBeReviewedPageRequest entity = new ToBeReviewedPageRequest();
            entity.setUserCode(selectUserDetails.getUserName());
            List<TApproverInformationVO> approverDetail = approverInformationService.getProcessedApproveDetail(entity);

            // 处理并丰富审批详情信息
            List<TApproverInformationVO> informationVOList = approverDetail.stream().map(t -> {
                // 设置审批状态
                t.setApproverStatus(redisUtils.getDict("approval_", t.getApproverStatus()));
                // 查询并设置流程分类信息
                LambdaQueryWrapper<TProcessOperation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(TProcessOperation::getApprovalId, t.getApproverId());
                TProcessOperation operation = tProcessOperationService.getOne(lambdaQueryWrapper);

                t.setProcessClassification(operation.getParentName());
                t.setProcessInstanceName("请审批_" + operation.getOperatorName() + "提交的" + operation.getParentName() + "审批");
                t.setBusinessId(operation.getBusinessId());
                t.setOrderNumber(operation.getOrderNumber());
                t.setBusinessName(operation.getParentName() + "_" + operation.getTechnologicalProcess());

                // 格式化日期时间
                LocalDateTime localDateTime = operation.getGmtCreate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = localDateTime.format(formatter);

                t.setCreateTime(formattedDate);
                return t;
            }).collect(Collectors.toList());

            return ResponseVO.success(informationVOList);
        } catch (Exception e) {
            log.error("查询已审批实例信息失败: {}", e.getMessage());
            return ResponseVO.success(null);
        }

    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Operation(summary = "审批", description = "根据用户id查询已办结审批实例")
    @GetMapping("/haveDoneApproveDetail")
    public ResponseVO<List<ApproveDetailVO>> haveDoneApproveDetail() {

        try {
            // 获取当前用户信息
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            SystemCurrentUserVO systemCurrentUserVO = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemCurrentUserVO.getUserName());

            // 创建查询请求对象并设置用户代码
            ToBeReviewedPageRequest entity = new ToBeReviewedPageRequest();
            entity.setUserCode(selectUserDetails.getUserName());

            List<ProcessOperationVO> operationList = tProcessOperationService.getHaveDoneApproveDetail(entity);
            List<ApproveDetailVO> approveDetailVOList = operationList.stream().map(t -> {
                TApproverInformationPO tApproverInformationPO = null;
                if ("YTG".equals(t.getOperation())) {
                    LambdaQueryWrapper<TApproverInformationPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(TApproverInformationPO::getApproverId, t.getApprovalId());
                    lambdaQueryWrapper.eq(TApproverInformationPO::getUserCode, selectUserDetails.getUserName());
                    List<TApproverInformationPO> list = approverInformationService.list(lambdaQueryWrapper);
                    if (!list.isEmpty()) {
                        tApproverInformationPO = list.get(0);
                    }
                }
                if (tApproverInformationPO != null) {
                    ApproveDetailVO approveDetailVO = new ApproveDetailVO();
                    approveDetailVO.setUserName(tApproverInformationPO.getUserName());
                    approveDetailVO.setUserCode(tApproverInformationPO.getUserCode());
                    approveDetailVO.setApproverId(t.getApprovalId());
                    approveDetailVO.setApproverStatus(redisUtils.getDict("approval_", t.getOperation()));
                    approveDetailVO.setProcessClassification(t.getParentName());
                    approveDetailVO.setProcessInstanceName("请审批_" + t.getOperatorName() + "提交的" + t.getParentName() + "审批");
                    approveDetailVO.setBusinessId(t.getBusinessId());
                    approveDetailVO.setOrderNumber(t.getOrderNumber());
                    approveDetailVO.setBusinessName(t.getParentName() + "_" + t.getTechnologicalProcess());

                    // 格式化日期时间
                    LocalDateTime localDateTime = t.getGmtCreate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    String formattedDate = localDateTime.format(DATE_TIME_FORMATTER);
                    approveDetailVO.setCreateTime(formattedDate);
                    return approveDetailVO;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());

            return ResponseVO.success(approveDetailVOList);
        } catch (Exception e) {
            log.error("查询已审批实例信息失败: {}", e.getMessage(), e);
            return ResponseVO.success(null);
        }

    }


    @Operation(summary = "审批", description = "根据实例Id手动审批")
    @PostMapping("/manualOperationApprove")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public ResponseVO<String> manualOperationApprove(@RequestBody ManualOperationApproveDTO manualOperation) {

        try {
            Map<String, String> header = new HashMap<>();
            header.put("x-acs-dingtalk-access-token", this.examineService.getToken().getAccessToken());
            Long taskId = manualOperation.getTaskId();
            ManualOperationApprovePO manualOperationApprovePO = ManualOperationApprovePO.builder()
                    .processInstanceId(manualOperation.getProcessInstanceId())
                    .result(manualOperation.getResult())
                    .actionerUserId(manualOperation.getUserId())
                    .taskId(taskId)
                    .build();
            if (manualOperation.getRemark() != null && !manualOperation.getRemark().isEmpty()) {
                manualOperationApprovePO.setRemark(manualOperation.getRemark());
            }

            //发起审批请求
            String sendPost = HttpClientUtil.sendPostWithHeaders(manualApproval, header, JSON.toJSONString(manualOperationApprovePO));
            ManualOperationApproveVO operationApproveVO = JSON.parseObject(sendPost, ManualOperationApproveVO.class);
            if (operationApproveVO.getSuccess()) {
                return ResponseVO.success("审批成功");
            } else {
                return ResponseVO.success("审批失败");
            }
        } catch (BusinessException e) {
            log.error("手动审批钉钉实例失败: {}", e.getMessage());
            return ResponseVO.success("审批失败");
        }

    }


    /**
     * 审批流程节点信息获取接口
     * 根据approvalId获取所有流程节点信息
     *
     * @param approvalId 审批ID
     * @return 包含流程节点信息的ResponseVO对象
     */
    @Operation(summary = "审批", description = "根据approvalId获取所有流程节点信息")
    @PostMapping("/getApprovalNode")
    public ResponseVO<List<ApprovalNodeResponse>> getApprovalNode(@RequestParam String approvalId) {

        try {
            // 根据审批ID查询TProcessOperation表中的记录
            LambdaQueryWrapper<TProcessOperation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TProcessOperation::getApprovalId, approvalId);
            TProcessOperation tProcessOperation = tProcessOperationService.getOne(queryWrapper);

            // 根据业务名称和公司编号查询ApprovalProcessTemplatesPO表中的记录
            LambdaQueryWrapper<ApprovalProcessTemplatesPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ApprovalProcessTemplatesPO::getBusinessName, tProcessOperation.getParentName() + "_" + tProcessOperation.getTechnologicalProcess());
            wrapper.eq(ApprovalProcessTemplatesPO::getCompanyNo, tProcessOperation.getCompanyNo());
            List<ApprovalProcessTemplatesPO> templatesPOList = approvalProcessTemplatesService.list(wrapper);

            LambdaQueryWrapper<SystemUserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userPOLambdaQueryWrapper.eq(SystemUserPO::getUserName, tProcessOperation.getUserName());
            SystemUserPO userServiceOne = iSystemUserService.getOne(userPOLambdaQueryWrapper);

            // 将查询结果转换为ApprovalNodeResponse对象列表
            List<ApprovalNodeResponse> nodeResponseList = templatesPOList.stream().map(t -> {
                ApprovalNodeResponse approvalNodeResponse = new ApprovalNodeResponse();
                approvalNodeResponse.setNode(t.getLevel());
                approvalNodeResponse.setNodeType(t.getType());
                approvalNodeResponse.setNodeName(t.getTypeDescription());
                if (t.getLevel() == 1) {
                    approvalNodeResponse.setUserName(userServiceOne.getUserName());
                    approvalNodeResponse.setRealName(userServiceOne.getRealName());
                } else {
                    approvalNodeResponse.setUserName(t.getApprovalUserName());
                    approvalNodeResponse.setRealName(t.getApprovalRealName());
                }
                return approvalNodeResponse;
            }).collect(Collectors.toList());

            // 返回成功响应，包含流程节点信息列表
            return ResponseVO.success(nodeResponseList);
        } catch (Exception e) {
            // 记录错误日志并返回空列表的响应
            log.error("查询所有流程节点信息失败: {}", e.getMessage());
            return ResponseVO.success(null);
        }
    }

    /**
     * 获取流程模板
     */
    public List<ApprovalNodeResponse> getProcessTemplate(TProcessOperation operationServiceOne, List<ApprovalNodeResponse> nodeResponseList) {
        //获取业务标识
        String businessName = operationServiceOne.getParentName() + "_" + operationServiceOne.getTechnologicalProcess();
        String databaseName = operationServiceOne.getDataName();
        //获取流程模板
        LambdaQueryWrapper<ApprovalProcessTemplatesPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApprovalProcessTemplatesPO::getBusinessName, businessName);
        List<ApprovalProcessTemplatesPO> templatesPOS = approvalProcessTemplatesService.list(wrapper);

//
//        if ("t_sales_bidding".equals(databaseName)) {
////                SalesBiddingDetailsVO salesBiddingDetailsVO = iSalesBiddingService.get(businessId);
////
////                nodeResponseList.stream().map(t->{
////                    for (ApprovalProcessTemplatesPO templatesPO : templatesPOS) {
////                        if (templatesPO.getParentId() == t.getNode()){
////
////                        }
////                    }
////                })
//
//        } else if ("t_price_scheme".equals(databaseName)) {
//
//        } else if ("t_product_price".equals(databaseName)) {

//            ProductPriceDetailsVO productPriceDetailsVO = iProductPriceService.get(operationServiceOne.getBusinessId());
        return nodeResponseList.stream().map(t -> {
            // 找到符合条件的模板，避免重复遍历
            for (ApprovalProcessTemplatesPO templatesPO : templatesPOS) {
                // 检查 node 是否匹配，并且根据 type 是否为空做不同处理
                if (t.getNode() == templatesPO.getLevel()) {
                    // 如果 type 不为空，才设置 userName 和 realName
//                        if (templatesPO.getType() != null) {
//                            if (templatesPO.getType().equals(productPriceDetailsVO.getTradeType())){
//                                t.setUserName(templatesPO.getApprovalUserName());
//                                t.setRealName(templatesPO.getApprovalRealName());
//                            }
//                        } else {
                    // 如果 type 为空，直接设置
                    t.setUserName(templatesPO.getApprovalUserName());
                    t.setRealName(templatesPO.getApprovalRealName());
//                        }
                }
            }
            return t;
        }).collect(Collectors.toList());

//        } else if ("t_order_line".equals(databaseName)) {
//
//        }
//        return null;
    }

    @Operation(summary = "审批", description = "根据approvalId查询流程节点下已审批人")
    @PostMapping("/selectApprovalLivingExample")
    public ResponseVO<List<ApprovalLivingResponseVO>> SelectApprovalLivingExample(@RequestParam String approvalId) {

        try {
            //查询流程申请表
            LambdaQueryWrapper<TProcessOperation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TProcessOperation::getApprovalId, approvalId);
            TProcessOperation operationServiceOne = tProcessOperationService.getOne(queryWrapper);

            Map<String, String> header = new HashMap<>();
            header.put("x-acs-dingtalk-access-token", this.examineService.getToken().getAccessToken());
            String sendGet = HttpClientUtil.sendGet(approvalLivingExample + "?processInstanceId=" + operationServiceOne.getApprovalId(), header);
            log.info("实例详情响应参数:{}", sendGet);
            GetApprovalLivingExample getApprovalLivingExample = JSON.parseObject(sendGet, GetApprovalLivingExample.class);

            LambdaQueryWrapper<TApproverInformationPO> poLambdaQueryWrapper = new LambdaQueryWrapper<>();
            poLambdaQueryWrapper.eq(TApproverInformationPO::getApproverId, approvalId);
            List<TApproverInformationPO> tApproverInformationPOList = approverInformationService.list(poLambdaQueryWrapper);

            List<ApprovalLivingResponseVO> approvalLivingResponseVOS = tApproverInformationPOList.stream().map(t -> {
                // 格式化日期时间
                LocalDateTime localDateTime = t.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = localDateTime.format(formatter);

                ApprovalLivingResponseVO approvalLivingResponseVO = ApprovalLivingResponseVO
                        .builder()
                        .approver(t.getUserName())
                        .approvalTime(formattedDate)
                        .approvalStatus(t.getStatus())
                        .approvalName(t.getApprovelType())
                        .node(t.getLevel())
                        .build();

                if (approvalLivingResponseVO.getNode() == 1) {
                    LambdaQueryWrapper<SystemUserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userPOLambdaQueryWrapper.eq(SystemUserPO::getUserName, operationServiceOne.getUserName());
                    SystemUserPO userServiceOne = iSystemUserService.getOne(userPOLambdaQueryWrapper);
                    approvalLivingResponseVO.setApprover(userServiceOne.getRealName());
                    approvalLivingResponseVO.setRemark("同意");
                }

                for (OperationRecord operationRecord : getApprovalLivingExample.getResult().getOperationRecords()) {
                    if (operationRecord.getActivityId() != null && operationRecord.getActivityId().equals(t.getRemarkCode()) && operationRecord.getUserId().equals(t.getDingdingCode()) && !"PROCESS_CC".equals(operationRecord.getType())) {
                        approvalLivingResponseVO.setRemark(operationRecord.getRemark());
                    }
                }

                if (t.getTaskId() != null) {
                    approvalLivingResponseVO.setTaskId(t.getTaskId());
                }
                return approvalLivingResponseVO;
            }).collect(Collectors.toList());
//
            return ResponseVO.success(approvalLivingResponseVOS);
        } catch (Exception e) {
            log.error("查询所有流程节点已审批人信息失败: {}", e.getMessage());
            return ResponseVO.success(null);
        }
    }

    @Permissions(value = "approvalLivingExample:getTemplate")
    @Operation(summary = "审批", description = "查询审批模板列表")
    @GetMapping("/getApprovalDictionary")
    public ResponseVO<List<TApprovalDictionaryPO>> getApprovalDictionary() {
        LambdaQueryWrapper<TApprovalDictionaryPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TApprovalDictionaryPO::getIsDeleted, 0);
        List<TApprovalDictionaryPO> approvalDictionaryPOList = tApprovalDictionaryService.list(wrapper);
        return ResponseVO.success(approvalDictionaryPOList);
    }

    @Permissions(value = "approvalLivingExample:createDictionary")
    @Operation(summary = "审批", description = "新增OA审批字典值信息")
    @PostMapping("/addApprovalDictionary")
    public ResponseVO<String> addApprovalDictionary(@RequestBody TApprovalDictionaryDTO tApprovalDictionaryDTO) {
        TApprovalDictionaryPO tApprovalDictionaryPO = new TApprovalDictionaryPO();
        BeanUtils.copyProperties(tApprovalDictionaryDTO, tApprovalDictionaryPO);
        return ResponseVO.success(tApprovalDictionaryService.addApprovalDictionary(tApprovalDictionaryPO));
    }

    @Permissions(value = "approvalLivingExample:getById")
    @Operation(summary = "审批", description = "根据Id查询字典值信息")
    @GetMapping("/getById/{id}")
    public ResponseVO<TApprovalDictionaryPO> getById(@PathVariable Long id) {
        return ResponseVO.success(tApprovalDictionaryService.getById(id));
    }

    @Permissions(value = "approvalLivingExample:updateDictionary")
    @Operation(summary = "审批", description = "根据Id查询字典值信息")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    @PostMapping("/updateApprovalDictionary")
    public ResponseVO<TApprovalDictionaryPO> updateApprovalDictionary(@RequestBody TApprovalDictionaryDTO tApprovalDictionaryDTO) {
        tApprovalDictionaryService.updateApprovalDictionary(tApprovalDictionaryDTO);
        return ResponseVO.success();
    }

    @Operation(summary = "审批", description = "获取审批人流程模板")
    @GetMapping("/getProcessTemplates")
    public ResponseVO<List<ApprovalProcessTemplatesPO>> getProcessTemplates() {
        LambdaQueryWrapper<ApprovalProcessTemplatesPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApprovalProcessTemplatesPO::getIsDeleted, 0);
        List<ApprovalProcessTemplatesPO> processTemplatesPOList = approvalProcessTemplatesService.list(wrapper);
        return ResponseVO.success(processTemplatesPOList);
    }

    @Operation(summary = "审批", description = "新增审批人流程模板")
    @PostMapping("/insertProcessTemplates")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public ResponseVO<String> insertProcessTemplates(ApprovalProcessTemplatesDTO approvalProcessTemplatesDTO) {
        ApprovalProcessTemplatesPO approvalProcessTemplatesPO = new ApprovalProcessTemplatesPO();
        BeanUtils.copyProperties(approvalProcessTemplatesDTO, approvalProcessTemplatesPO);
        boolean save = approvalProcessTemplatesService.save(approvalProcessTemplatesPO);
        if (save) {
            return ResponseVO.success("保存成功");
        }

        throw new BusinessException("保存失败");
    }

    @Operation(summary = "审批", description = "更新审批人流程模板")
    @PostMapping("/updateProcessTemplates/{id}")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public ResponseVO<String> updateProcessTemplates(@PathVariable Long id,
                                                     @RequestBody ApprovalProcessTemplatesDTO approvalProcessTemplatesDTO) {
        LambdaUpdateWrapper<ApprovalProcessTemplatesPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ApprovalProcessTemplatesPO::getId, id);
        wrapper.set(ApprovalProcessTemplatesPO::getBusinessName, approvalProcessTemplatesDTO.getBusinessName());
        wrapper.set(ApprovalProcessTemplatesPO::getApprovalRealName, approvalProcessTemplatesDTO.getApprovalRealName());
        wrapper.set(ApprovalProcessTemplatesPO::getApprovalUserName, approvalProcessTemplatesDTO.getApprovalUserName());
        wrapper.set(ApprovalProcessTemplatesPO::getParentId, approvalProcessTemplatesDTO.getParentId());
        wrapper.set(ApprovalProcessTemplatesPO::getLevel, approvalProcessTemplatesDTO.getLevel());
        wrapper.set(ApprovalProcessTemplatesPO::getType, approvalProcessTemplatesDTO.getType());
        wrapper.set(ApprovalProcessTemplatesPO::getTypeDescription, approvalProcessTemplatesDTO.getTypeDescription());
        wrapper.set(ApprovalProcessTemplatesPO::getCompanyNo, approvalProcessTemplatesDTO.getCompanyNo());
        boolean update = approvalProcessTemplatesService.update(wrapper);
        if (update) {
            return ResponseVO.success("修改成功");
        }
        throw new BusinessException("修改失败");
    }

    @Operation(summary = "审批", description = "删除审批人流程模板")
    @PostMapping("/deletedProcessTemplates/{id}")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public ResponseVO<String> deletedProcessTemplates(@PathVariable Long id) {
        LambdaUpdateWrapper<ApprovalProcessTemplatesPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ApprovalProcessTemplatesPO::getId, id);
        wrapper.set(ApprovalProcessTemplatesPO::getIsDeleted, 1);
        boolean deleted = approvalProcessTemplatesService.update(wrapper);
        if (deleted) {
            return ResponseVO.success("保存成功");
        }
        throw new BusinessException("保存失败");
    }


}