package com.ccit.area.sales.service.dingding;

import com.ccit.area.sales.dao.dingding.po.DeptListPO;
import com.ccit.area.sales.dao.dingding.po.TApprovalDictionaryPO;
import com.ccit.area.sales.dao.dingding.vo.CreateLivingExampleResponse;
import com.ccit.area.sales.dao.dingding.vo.CreateTemplateVO;
import com.ccit.area.sales.dao.dingding.vo.GetTokenResponse;
import com.ccit.area.sales.dao.dingding.vo.RootDeptListVO;

import java.util.List;


public interface ExamineService {

    //获取审批功能企业Token
    public GetTokenResponse getToken();

    //创建模板
    public CreateTemplateVO createTemplate(String businessName,String companyName,String companyNo);

    //创建钉钉OA审批实例接口
    public CreateLivingExampleResponse createApprovalLivingExample(Long businessId, String databaseName, String businessName,String companyNo,String userName, TApprovalDictionaryPO approvalDictionary);

    List<RootDeptListVO> selectDeptList(DeptListPO deptListPO);


}
