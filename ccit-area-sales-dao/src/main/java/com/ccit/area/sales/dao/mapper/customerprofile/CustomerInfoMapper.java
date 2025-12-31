package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.CustomerPO;
import com.ccit.area.sales.dao.vo.customer.CustomerVO;
import com.ccit.area.sales.dao.vo.customer.TCompanyInfoUpdateVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_company_info(企业基本信息表)】的数据库操作Mapper
 * @createDate 2024-10-12 14:14:30
 * @Entity com.ccit.area.sales.dao.bean.po.TCompanyInfoPO
 */
public interface CustomerInfoMapper extends BaseMapper<CustomerPO> {

    @Select("SELECT COLUMN_NAME, COLUMN_COMMENT " +
            "FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_NAME = #{param.tableName} AND TABLE_SCHEMA = #{param.databaseName}")
    List<Map<String, String>> getColumnComments(@Param(value = "param") Map<String, String> param);


    List<CustomerVO> selectPageList(Page<CustomerVO> pages,
                                    @Param(value = "param") Map<String, Object> param);

    List<TCompanyInfoUpdateVO> selectCompanyInfoUpdatePageList(Page<TCompanyInfoUpdateVO> pages,
                                                                  @Param(value = "param") Map<String, Object> param);
}




