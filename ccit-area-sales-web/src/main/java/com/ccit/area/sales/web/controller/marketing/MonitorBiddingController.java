package com.ccit.area.sales.web.controller.marketing;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingAddDTO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingVO;
import com.ccit.area.sales.service.marketing.IMonitorBiddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  * 描述 : “竞价监控管理”前端控制器
 *  * 创建人 : tb
 *  * 创建时间 : 2024年10月9日 上午10:56:23
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.bidding
 *  * 类名 : MonitorBiddingController
 */
@RestController
@Tag(name = "竞价监控管理")
@RequestMapping(value = "/webapi/bidding/monitorBidding")
public class MonitorBiddingController {

    /**
     * “竞价监控”服务类
     */
    @Autowired
    private IMonitorBiddingService monitorBiddingService;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年10月9日 上午09:10:23
     * 描述 : 获取竞价监控
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : get
     * Result<MonitorBiddingDetailsVO>
     *
     * @throws
     */
    @Permissions(value = "monitorBidding:get")
    @Log(moduleName = "竞价监控管理", description = "获取竞价监控", businessType = BusinessType.GET)
    @Operation(summary = "获取竞价监控")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "tenderNumber", description = "招标编号",
                    required = true, schema = @Schema(type = "String"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<MonitorBiddingDetailsVO> get(String tenderNumber) {
        return ResponseVO.success(monitorBiddingService.get(tenderNumber));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 上午09:10:23
     * 描述 : 竞价过程
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : process
     * Result<MonitorBiddingDetailsVO>
     *
     * @throws
     */
    @Log(moduleName = "竞价过程", description = "竞价过程", businessType = BusinessType.GET)
    @Operation(summary = "竞价过程")
    @PostMapping(value = "/process")
    public ResponseVO<MonitorBiddingDetailsVO> process(@RequestBody ApplyBiddingAddDTO entity) {
        return ResponseVO.success(monitorBiddingService.process(entity));
    }

    @PostMapping(value = "/supervisoryControl")
    public ResponseVO<MonitorBiddingVO> supervisoryControl(@RequestParam String tenderNumber){
        return ResponseVO.success(monitorBiddingService.supervisoryControl(tenderNumber));
    }
}
