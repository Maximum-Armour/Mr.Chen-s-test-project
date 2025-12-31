package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TCarTransportAddressesPO;
import com.ccit.area.sales.dao.vo.customer.TCarTransportAddresseVO;
import com.ccit.area.sales.dao.vo.customer.TCarTransportAddressesVO;

import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_car_transport_addresses(客户汽运配送地址表)】的数据库操作Service
 * @createDate 2024-10-14 14:59:57
 */
public interface CarTransportAddressesService extends IService<TCarTransportAddressesPO> {

    Page<TCarTransportAddresseVO> selectPageList(Map<String, Object> param);

    TCarTransportAddressesVO getRegisterAccountMessage(Long id);

    byte[] automobileDeriveExcel();
}
