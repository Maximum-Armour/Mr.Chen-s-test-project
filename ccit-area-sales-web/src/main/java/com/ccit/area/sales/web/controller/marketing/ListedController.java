package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.vo.marketing.ListedDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ListedPageListVO;
import com.ccit.area.sales.service.marketing.IListedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 描述 : “挂牌管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:22
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.marketing
 * 类名 : ListedController
 */
@RestController
@Tag(name = "挂牌管理")
@RequestMapping(value = "/webapi/marketing/listed")
public class ListedController {

	/**
	 * “挂牌”服务类
	 */
	@Autowired
    private IListedService listedService;
    
    /**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:22
	 * 描述 : 挂牌列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  Result<Page<ListedPageListVO>>  
	 *  @throws
	 */
    @Permissions(value = "listed:select")
	@Log(moduleName = "挂牌管理", description = "挂牌列表", businessType = BusinessType.SELECT)
	@Operation(summary = "挂牌列表", description = "挂牌列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<ListedPageListVO>> selectPageList(@RequestBody PageEntity<ListedPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(listedService.selectPageList(param));
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年8月20日 下午2:25:51
     * 描述 : 新增挂牌
     * 包名 : com.ccit.area.sales.web.controller.marketing
     * 方法名 : add
     *  ResponseVO<String>  
     *  @throws
     */
    //@Permissions(value = "listed:add")
    @Log(moduleName = "挂牌管理", description = "新增挂牌", businessType = BusinessType.INSERT)
	@Operation(summary = "新增挂牌")
    @PostMapping(value = "/add")
    public ResponseVO<String> add(@Valid @RequestBody ListedAddDTO entity) {
		return ResponseVO.success(listedService.add(entity));
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年8月21日 上午10:09:07
     * 描述 : 获取挂牌
     * 包名 : com.ccit.area.sales.web.controller.marketing
     * 方法名 : get
     *  ResponseVO<ListedDetailsVO>  
     *  @throws
     */
	@Permissions(value = "listed:get")
	@Log(moduleName = "新增挂牌", description = "获取挂牌", businessType = BusinessType.GET)
	@Operation(summary = "获取挂牌")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "定价方案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<ListedDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(listedService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月21日 下午3:27:35
	 * 描述 : 修改挂牌
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "listed:edit")
	@Log(moduleName = "挂牌管理", description = "修改挂牌", businessType = BusinessType.EDIT)
	@Operation(summary = "修改挂牌")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody ListedEditDTO entity) {
		return ResponseVO.success(listedService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月21日 下午3:27:54
	 * 描述 : 删除挂牌
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "listed:delete")
	@Log(moduleName = "挂牌管理", description = "删除挂牌", businessType = BusinessType.DELETE)
	@Operation(summary = "删除挂牌")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody ListedDeleteDTO entity) {
		return ResponseVO.success(listedService.delete(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年8月21日 下午3:27:54
	 * 描述 : 挂牌提交
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : submit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "listed:submit")
	@Log(moduleName = "挂牌管理", description = "挂牌提交", businessType = BusinessType.DELETE)
	@Operation(summary = "挂牌提交")
	@PostMapping(value = "/submit")
	public ResponseVO<String> submit(@Valid @RequestBody ListedSubmitDTO entity) {
		return ResponseVO.success(listedService.submit(entity));
	}
}
