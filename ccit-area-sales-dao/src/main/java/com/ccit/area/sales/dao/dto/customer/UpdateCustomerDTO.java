package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCustomerDTO {
    List<Long> ids;
    String status;
}
