package com.ccit.area.sales.service.dingding.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.common.dingding.utils.HttpClientUtil;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.dingding.po.*;
import com.ccit.area.sales.dao.dingding.vo.*;
import com.ccit.area.sales.dao.dingding.vo.Dictionary;
import com.ccit.area.sales.dao.vo.marketing.*;
import com.ccit.area.sales.dao.vo.sales.OrderLineVO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsSubmitVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.dingding.ApprovalDictionaryService;
import com.ccit.area.sales.service.dingding.DingdingApprovalTemplateService;
import com.ccit.area.sales.service.dingding.ExamineService;
import com.ccit.area.sales.service.marketing.IOrderBiddingService;
import com.ccit.area.sales.service.marketing.IPriceSchemeService;
import com.ccit.area.sales.service.marketing.IProductPriceService;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import com.ccit.area.sales.service.sales.OrderService;
import com.ccit.area.sales.service.sales.OrderStatisticsService;
import com.ccit.area.sales.service.system.ISystemUserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamineServiceImpl implements ExamineService {

    @Value("${dingding.get_token}")
    private String getTokenUrl;

    @Value("${dingding.create_living_example}")
    private String createLivingExampleUrl;

    @Value("${dingding.query_approval_node}")
    private String queryApprovalNodeUrl;

    @Value("${dingding.create_template}")
    private String createTemplate;

    @Value("${dingding.agent_code}")
    private Long agentCode;
    @Value("${dingding.app_key}")
    private String appKey;
    @Value("${dingding.app_secret}")
    private String appSecret;

    @Value("${dingding.get_datp_list}")
    private String getDetpList;

    @Autowired
    private ApprovalDictionaryService tApprovalDictionaryService;
    @Autowired
    private ISalesBiddingService iSalesBiddingService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private IPriceSchemeService iPriceSchemeService;

    @Autowired
    private IProductPriceService iProductPriceService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private IOrderBiddingService iOrderBiddingService;

    @Autowired
    private OrderStatisticsService orderingStatisticsService;

    @Autowired
    private DingdingApprovalTemplateService dingdingApprovalTemplateService;

    /**
     * 创建或修改审批模板
     */
    @Override
    public CreateTemplateVO createTemplate(String businessName, String companyName, String companyNo) {
        //查询钉钉字典表获取字典值信息.
        LambdaQueryWrapper<TApprovalDictionaryPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TApprovalDictionaryPO::getBusiness, businessName);
        TApprovalDictionaryPO dictionaryServiceOne = tApprovalDictionaryService.getOne(queryWrapper);
        List<Dictionary> dictionaryList = JSON.parseArray(dictionaryServiceOne.getDictionary(), Dictionary.class);

        //构建入参
        List<FormComponent> formComponentList = dictionaryList.stream().map(t -> {
            return FormComponent.builder()
                    .componentType(t.getFieldType())
                    .props(Props.builder()
                            .label(t.getFieldName())
                            .placeholder("请输入")
                            .componentId(t.getFieldValue())
                            .required(false)
                            .build())
                    .build();
        }).collect(Collectors.toList());

        //判断业务类型
//        LambdaQueryWrapper<DingdingApprovalTemplatePO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(DingdingApprovalTemplatePO::getCompanyNo, companyNo);
//        wrapper.eq(DingdingApprovalTemplatePO::getBusinessType, businessName);
//        DingdingApprovalTemplatePO dingdingApprovalTemplatePO = dingdingApprovalTemplateService.getOne(wrapper);
//        String processCode = dingdingApprovalTemplatePO.getProcessCode();

        String[] split = businessName.split("_");
        DingTalkFormTemplateRequest dingTalkFormTemplateRequest = DingTalkFormTemplateRequest.builder()
                //传任意值为修改,不传为新增
//                .processCode(processCode)
                //模板名称
                .name(companyName + "地销平台" + split[0] + split[1] + "审批")
                //模板描述
                .description(companyName + "地销平台" + split[0] + split[1] + "审批")
                .formComponents(formComponentList)
                .build();

        //设置请求头
        Map<String, String> header = new HashMap<>();
        header.put("x-acs-dingtalk-access-token", this.getToken().getAccessToken());

        //发起创建或更新钉钉模板方法.
        String CreateTemplateResponse = HttpClientUtil.sendPostWithHeaders(createTemplate, header, JSON.toJSONString(dingTalkFormTemplateRequest));
        return JSON.parseObject(CreateTemplateResponse, CreateTemplateVO.class);
    }

    /**
     * 创建审批实例接口
     *
     * @param businessId
     * @param databaseName
     * @param businessName
     * @param approvalDictionary
     * @param
     * @return
     */
    @Override
    public CreateLivingExampleResponse createApprovalLivingExample(Long businessId, String databaseName, String businessName, String companyNo, String userName, TApprovalDictionaryPO approvalDictionary) {

        //查询业务对应钉钉模板
        LambdaQueryWrapper<DingdingApprovalTemplatePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DingdingApprovalTemplatePO::getCompanyNo, companyNo);
        wrapper.eq(DingdingApprovalTemplatePO::getBusinessType, businessName);
        DingdingApprovalTemplatePO dingdingApprovalTemplatePO = dingdingApprovalTemplateService.getOne(wrapper);
        String processCode = dingdingApprovalTemplatePO.getProcessCode();

        if (processCode == null) {
            throw new BusinessException("钉钉模板不存在!");
        }

        // 声明通用对象
        Object targetObject = null;
        //用户信息
        SystemUserDetailVO UserDetail = getUserDetails(userName);
        //订单编码
        String orderNumber = null;
        if ("t_sales_bidding".equals(databaseName)) {
            if ("竞价结果管理_提交".equals(businessName)) {
                OrderBiddingSubmitVO orderBiddingSubmitVO = iOrderBiddingService.submit(businessId);
                orderNumber = orderBiddingSubmitVO.getTenderNumber();
                targetObject = orderBiddingSubmitVO;
            } else {
                SalesBiddingDetailsVO salesBiddingDetailsVO = iSalesBiddingService.get(businessId);
                orderNumber = salesBiddingDetailsVO.getBiddingNo();
                targetObject = salesBiddingDetailsVO;
            }
        } else if ("t_price_scheme".equals(databaseName)) {
            PriceSchemeSelectVO priceSchemeSelectVO = iPriceSchemeService.select(businessId);
            orderNumber = priceSchemeSelectVO.getSchemeNo();
            targetObject = priceSchemeSelectVO;
        } else if ("t_product_price".equals(databaseName)) {
            ProductPriceSelectVO productPriceSelectVO = iProductPriceService.select(businessId);
            orderNumber = productPriceSelectVO.getProductPriceNo();
            targetObject = productPriceSelectVO;
        } else if ("t_order_line".equals(databaseName)) {
            OrderLineVO orderLineVO = orderService.get(businessId);
            orderNumber = orderLineVO.getOrderLineNo();
            targetObject = orderLineVO;
        } else if ("t_order_statistics".equals(databaseName)){
            OrderStatisticsSubmitVO orderStatisticsSubmitVO = orderingStatisticsService.submit(businessId);
            orderNumber = orderStatisticsSubmitVO.getTakeOrderNo();
            targetObject = orderStatisticsSubmitVO;
        }else {
            throw new BusinessException("参数错误!");
        }

        //设置请求头
        Map<String, String> header = new HashMap<>();
        header.put("x-acs-dingtalk-access-token", this.getToken().getAccessToken());

        // 构建入参
        List<FormComponentValues> formComponentValuesList = new ArrayList<>();
        try {
            Class<?> clazz = targetObject.getClass();
            Field[] fields = clazz.getDeclaredFields();

            // 解析字典一次，避免重复解析
            List<Dictionary> dictionaries = JSON.parseArray(approvalDictionary.getDictionary(), Dictionary.class);
            Map<String, Dictionary> dictionaryMap = dictionaries.stream()
                    .collect(Collectors.toMap(Dictionary::getFieldValue, dict -> dict));

            for (Field field : fields) {
                String fieldName = field.getName();
                Dictionary dict = dictionaryMap.get(fieldName);
                if (dict != null) {
                    field.setAccessible(true); // 设置字段可访问
                    Object value = field.get(targetObject);
                    if (value != null) {
                        String stringValue = convertToString(value);
                        formComponentValuesList.add(FormComponentValues.builder()
                                .id(dict.getFieldValue())
                                .name(dict.getFieldName())
                                .value(stringValue)
                                .componentType(dict.getFieldType())
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            log.error("构建实例请求参数失败: {}, 异常: {}", e.getMessage(), e);
        }

        formComponentValuesList = Collections.unmodifiableList(formComponentValuesList);

        //构建入参
        QueryApprovalNodeRequest queryApprovalNodeRequest = QueryApprovalNodeRequest.builder()
                .processCode(processCode)
                .deptId(UserDetail.getDingdingDeptId())
                .userId(UserDetail.getDingdingUserId())
                .formComponentValues(formComponentValuesList)
                .build();

        //查询流程节点
        String sendPostWithHeaders = HttpClientUtil.sendPostWithHeaders(queryApprovalNodeUrl, header, JSON.toJSONString(queryApprovalNodeRequest));
        QueryApproverNodeResponse queryApproverNodeResponse = JSON.parseObject(sendPostWithHeaders, QueryApproverNodeResponse.class);
        //构建入参
        List<TargetSelectActioners> selectActionersList = queryApproverNodeResponse.getResult().getWorkflowActivityRules().stream().map(item -> {
            TargetSelectActioners targetSelectActioners = new TargetSelectActioners();
            if (!StringUtil.isNullOrEmpty(item.getWorkflowActor().getActorKey())) {
                targetSelectActioners.setActionerKey(item.getWorkflowActor().getActorKey());
            }
            if (!CollectionUtils.isEmpty(item.getWorkflowActor().getActorSelectionRange().getApprovals())) {
                List<String> userIdList = new ArrayList<>();
                for (ApprovalMember approval : item.getWorkflowActor().getActorSelectionRange().getApprovals()) {
                    if (!StringUtil.isNullOrEmpty(approval.getWorkNo())) {
                        userIdList.add(approval.getWorkNo());
                    }
                }
                targetSelectActioners.setUserId(userIdList);
            }

            return targetSelectActioners;
        }).collect(Collectors.toList());

        //构建入参
        ApprovalRequest approvalRequest = ApprovalRequest.builder()
                //发起人userID
                .originatorUserId(UserDetail.getDingdingUserId())
                //模板code
                .processCode(processCode)
                //发起人部门id
                .deptId(Long.valueOf(UserDetail.getDingdingDeptId()))
                //应用标识
                .microappAgentId(agentCode)
                //不使用审批流模板时，直接指定的审批人列表
//                .approvers(approvers)
                //抄送人id集合
//                .ccList()
                //抄送时间点，取值：
                //START：开始时抄送
                //FINISH：结束时抄送
                //START_FINISH：开始和结束时都抄送
//                .ccPosition()
                .targetSelectActioners(selectActionersList)
                .formComponentValues(formComponentValuesList)
                .build();

        //发起钉钉实例
        String LivingExampleResponses = HttpClientUtil.sendPostWithHeaders(createLivingExampleUrl, header, JSON.toJSONString(approvalRequest));
        CreateLivingExampleResponse createLivingExampleResponse = JSON.parseObject(LivingExampleResponses, CreateLivingExampleResponse.class);
        if (createLivingExampleResponse.getInstanceId() == null) {
            throw new RuntimeException("提交钉钉异常!");
        }
        createLivingExampleResponse.setRealName(UserDetail.getRealName());
        createLivingExampleResponse.setProcessCode(processCode);
        createLivingExampleResponse.setOrderNumber(orderNumber);
        return createLivingExampleResponse;
    }

    @Override
    public List<RootDeptListVO> selectDeptList(DeptListPO deptListPO) {
        List<RootDeptListVO> rootDeptListVOList = new ArrayList<>();
        for (String deptId : deptListPO.getDeptIdList()) {
            RootDeptListVO listVO = new RootDeptListVO();
            JSONObject request = new JSONObject();
            request.put("dept_id", deptId);
            String httpPost = HttpClientUtil.sendPost(getDetpList + "?access_token=" + this.getToken().getAccessToken(), request.toString());
            DeptListVO deptListVO = JSONObject.parseObject(httpPost, DeptListVO.class);
            log.info("父部门响应参数:{}", deptListVO);
            listVO.setCode("200");
            listVO.setMessages("查询成功");
            listVO.setParentNode(deptId);
            List<Department> departmentList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(deptListVO.getResult())) {
                log.info("进入循环:{}", deptListVO);
                for (DeptBaseResponse deptBaseResponse : deptListVO.getResult()) {
                    Department department = Department.builder()
                            .deptId(deptBaseResponse.getDeptId())
                            .name(deptBaseResponse.getName())
                            .nodeStatus(true)
                            .build();
                    departmentList.add(department);
                }

            } else {
                departmentList.add(Department.builder()
                        .nodeStatus(false)
                        .build());
            }
            listVO.setDepartmentList(departmentList);
            rootDeptListVOList.add(listVO);
        }
        return rootDeptListVOList;
    }


    /**
     * 获取企业Token接口
     *
     * @return null
     */
    @Override
    public GetTokenResponse getToken() {
        String sendGet = HttpClientUtil.sendGet(getTokenUrl + "?appkey=" + appKey + "&appsecret=" + appSecret, null);
        return JSON.parseObject(sendGet, GetTokenResponse.class);
    }

    /**
     * 根据用户名获取 userid deptid username 参数
     *
     * @param userName
     * @return
     */
    public SystemUserDetailVO getUserDetails(String userName) {
        return iSystemUserService.selectUserDetails(userName);
    }

    /**
     * 格式化时间格式
     *
     * @param value
     * @return
     */
    private static String convertToString(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format((Date) value);
        } else if (value instanceof Number) {
            return value.toString();
        } else {
            return value.toString();
        }
    }
}
