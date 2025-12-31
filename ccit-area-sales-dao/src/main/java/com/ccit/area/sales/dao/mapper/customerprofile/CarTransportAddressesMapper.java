package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TCarTransportAddressesPO;
import com.ccit.area.sales.dao.vo.customer.TCarTransportAddresseVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_car_transport_addresses(客户汽运配送地址表)】的数据库操作Mapper
* @createDate 2024-10-14 14:59:57
* @Entity com.ccit.area.sales.dao.bean.po.TCarTransportAddressesPO
*/
public interface CarTransportAddressesMapper extends BaseMapper<TCarTransportAddressesPO> {
    List<TCarTransportAddresseVO> selectPageList(Page<TCarTransportAddresseVO> pages,
                                                 @Param(value = "param") Map<String, Object> param);
    @Select("SELECT COLUMN_NAME, COLUMN_COMMENT " +
            "FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_NAME = #{param.tableName} AND TABLE_SCHEMA = #{param.databaseName}")
    List<Map<String, String>> getColumnComments(@Param(value = "param") Map<String, String> param);

}




