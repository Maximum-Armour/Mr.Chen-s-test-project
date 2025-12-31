package com.ccit.area.sales.feign;

import com.ccit.area.sales.dto.ExamineApprovalRequests;
import com.ccit.area.sales.vo.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ccit-area-sales-web")
public interface ExamineFeign {

    @Operation(summary = "钉钉OA审批接口")
    @PostMapping("/webapi/system/examine/examineApproval")
    ResponseEntity<ResultResponse> examineApproval(@RequestBody ExamineApprovalRequests examineApprovalRequests);

}
