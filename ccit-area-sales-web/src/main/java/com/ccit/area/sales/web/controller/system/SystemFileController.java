package com.ccit.area.sales.web.controller.system;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.system.SystemFileDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemFilePageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemFilePageListVO;
import com.ccit.area.sales.dao.vo.system.SystemFileUploadVO;
import com.ccit.area.sales.service.system.ISystemFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 
 * 描述 : “附件管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 下午1:59:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemFileController
 */
@RestController
@Tag(name = "附件管理")
@RequestMapping(value = "/webapi/system/file")
public class SystemFileController {
	
	/**
	 * “附件”服务类
	 */
	@Autowired
	private ISystemFileService systemFileService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 下午2:05:33
	 * 描述 : 上传附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : upload
	 *  ResponseVO<SystemMenuDetailsVO>  
	 *  @throws
	 */
	@Operation(summary = "上传附件")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "businessId", description = "业务ID", 
				required = true, schema = @Schema(type = "String")),
		@Parameter(in = ParameterIn.QUERY, name = "businessCode", description = "业务编码", 
			required = true, schema = @Schema(type = "String")),
		@Parameter(in = ParameterIn.QUERY, name = "file", description = "附件", 
			required = true, schema = @Schema(type = "MultipartFile"))
	})
	@PostMapping(value = "/{businessId}/{businessCode}/upload")
	public ResponseVO<SystemFileUploadVO> upload(@PathVariable(value = "businessId") String businessId,
			@PathVariable(value = "businessCode") String businessCode,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		return ResponseVO.success(systemFileService.upload(businessId, businessCode, file));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 上午08:51:23
	 * 描述 : 附件列表查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.bidding
	 * 方法名 : selectPageList
	 *  Result<Page<SystemFilePageListVO>>
	 *  @throws
	 */
	@Permissions(value = "systemFile:select")
	@Log(moduleName = "附件", description = "附件列表", businessType = BusinessType.SELECT)
	@Operation(summary = "附件列表", description = "附件列表查询和高级查询")
	@PostMapping(value = "/page/list")
	public ResponseVO<List<SystemFilePageListVO>> selectPageList(@RequestBody SystemFilePageListDTO entity) {
		return ResponseVO.success(systemFileService.selectPageList(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 下午03:55:22
	 * 描述 : 删除附件
	 * 包名 : com.ccit.area.sales.web.controller.bidding
	 * 方法名 : delete
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "systemFile:delete")
	@Log(moduleName = "附件", description = "删除附件", businessType = BusinessType.DELETE)
	@Operation(summary = "删除附件")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SystemFileDeleteDTO entity) {
		return ResponseVO.success(systemFileService.delete(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月7日 下午2:05:33
	 * 描述 : 下载附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : downloadFile
	 *  void
	 *  @throws
	 */
	@GetMapping("/download")
	public void downloadFile(@RequestParam("fileName") String fileName, @RequestParam("businessId") String businessId,
							 @RequestParam("businessCode") String businessCode, HttpServletResponse response) throws IOException {
		systemFileService.downloadFile(fileName,businessId,businessCode,response);
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月29日 下午2:05:33
	 * 描述 : 图片预览
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewImage
	 *  void
	 *  @throws
	 */
	@GetMapping("/previewImage")
	public void previewImage(@RequestParam("fileName") String fileName, @RequestParam("businessId") String businessId,
							 @RequestParam("businessCode") String businessCode, HttpServletResponse response){
		try (InputStream is = systemFileService.previewImage(fileName,businessId,businessCode,response)) {
			response.setContentType("png/jpg");
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}



	/**
	 *
	 * 创建人 : cf
	 * 创建时间 : 2024年11月29日 下午15:16:33
	 * 描述 : 预览附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewFile
	 *  void
	 *  @throws
	 */
	@GetMapping("/preview/{fileName}")
	public void previewAttachment(@PathVariable("fileName") String fileName,
							@RequestParam("businessId") String businessId,
							@RequestParam("businessCode") String businessCode,
							HttpServletResponse response) throws IOException {
		systemFileService.previewAttachment(fileName, businessId, businessCode, response);
	}
}
