package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 部门列表参数对象。
 */
@Schema(description = "部门列表参数对象")
@Data
public class DeptListPO {

    /**
     * 部门ID列表。
     */
    @Schema(description = "部门ID列表", required = true)
    private List<String> deptIdList;
}
