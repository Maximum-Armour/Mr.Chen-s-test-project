package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemFilePO;
import com.ccit.area.sales.dao.dto.system.SystemFileDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemFilePageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemFilePageListVO;
import com.ccit.area.sales.dao.vo.system.SystemFileUploadVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 	
 * 描述 : “附件”服务类
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 上午09:57:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : SystemFileService
 */
public interface ISystemFileService extends IService<SystemFilePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 下午2:08:23
	 * 描述 : 上传附件
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : upload
	 *  SystemFileUploadVO  
	 *  @throws
	 */
	SystemFileUploadVO upload(String businessId, String businessCode, MultipartFile file);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 上午08:54:25
	 * 描述 : 附件查询
	 * 包名 : com.ccit.area.sales.service.bidding
	 * 方法名 : selectPageList
	 *  String
	 *  @throws
	 */
	List<SystemFilePageListVO> selectPageList(SystemFilePageListDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 下午03:55:22
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.bidding
	 * 方法名 : delete
	 *  SystemFileDeleteDTO
	 *  @throws
	 */
	String delete(SystemFileDeleteDTO entity);

	/**
	 * 创建人 : tb
	 * 创建时间 : 2024年11月7日 下午2:05:33
	 * 描述 : 下载附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : downloadFile
	 * void
	 * @return
	 * @throws
	 */
	void downloadFile(String fileName, String businessId, String businessCode, HttpServletResponse response) throws IOException;



	/**
	 * 创建人 : cf
	 * 创建时间 : 2024年11月30日 下午14:57:33
	 * 描述 : 预览附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewAttachment
	 * void
	 * @return
	 * @throws
	 */
	void previewAttachment(String fileName, String businessId, String businessCode, HttpServletResponse response) throws IOException;


	/**
	 * 创建人 : tb
	 * 创建时间 : 2024年11月29日 下午2:05:33
	 * 描述 : 图片预览
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewImage
	 * InputStream
	 * @return
	 * @throws
	 */
	InputStream previewImage(String fileName, String businessId, String businessCode, HttpServletResponse response) throws Exception;
}
