package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class UpdateAccountInformationDTO {

    List<Integer> ids;

    String status;
}
