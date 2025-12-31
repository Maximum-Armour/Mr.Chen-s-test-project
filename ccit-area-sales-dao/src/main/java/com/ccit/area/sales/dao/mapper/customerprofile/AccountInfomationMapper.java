package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TAccountInfomationPO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_account_infomation(企业注册账户信息表)】的数据库操作Mapper
* @createDate 2024-10-11 14:20:25
* @Entity com.ccit.area.sales.dao.bean.po.TAccountInfomation
*/
public interface AccountInfomationMapper extends BaseMapper<TAccountInfomationPO> {

    List<TAccountInfomationVO> selectPageList(Page<TAccountInfomationVO> pages,
                                              @Param(value = "param") Map<String, Object> param);
}




