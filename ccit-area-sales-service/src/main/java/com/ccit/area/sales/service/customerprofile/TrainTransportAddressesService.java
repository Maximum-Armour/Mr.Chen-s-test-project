package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TTrainTransportAddressesPO;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddresseVO;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddressesVO;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_train_transport_addresses(客户铁运配送地址表)】的数据库操作Service
* @createDate 2024-10-14 15:00:57
*/
public interface TrainTransportAddressesService extends IService<TTrainTransportAddressesPO> {

    Page<TTrainTransportAddresseVO> selectPageList(Map<String, Object> param);

    TTrainTransportAddressesVO getRailwayRransportMessage(Long id);

    byte[] railwayDeriveExcel();
}
