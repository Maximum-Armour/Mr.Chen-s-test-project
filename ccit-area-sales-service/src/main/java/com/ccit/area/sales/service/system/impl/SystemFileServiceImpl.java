package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.minio.MinioConfiguration;
import com.ccit.area.sales.dao.domain.system.SystemFilePO;
import com.ccit.area.sales.dao.dto.system.SystemFileDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemFilePageListDTO;
import com.ccit.area.sales.dao.mapper.system.SystemFileMapper;
import com.ccit.area.sales.dao.vo.system.SystemFilePageListVO;
import com.ccit.area.sales.dao.vo.system.SystemFileUploadVO;
import com.ccit.area.sales.service.system.ISystemFileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * 
 * 描述 : “附件”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 上午09:57:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemFileServiceImpl
 */
@Service
public class SystemFileServiceImpl extends ServiceImpl<SystemFileMapper, SystemFilePO> implements ISystemFileService {

	/**
	 * MinIO客户端
	 */
	@Autowired
	private MinioClient minioClient;
	
	/**
	 * MinIO配置
	 */
	@Autowired
    private MinioConfiguration minioConfiguration;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 下午2:08:49
	 * 描述 : 上传附件
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : upload
	 *  SystemFileUploadVO  
	 *  @throws
	 */
	@Override
	public SystemFileUploadVO upload(String businessId, String businessCode, MultipartFile file) {
		if (file.isEmpty()) {
			throw new BusinessException("上传附件失败，请选中附件！");
        }
		if (StringUtils.isBlank(businessCode)) {
			throw new BusinessException("业务编码不能为空，请进行检查！");
		}
		// 附件全称
		String fileName = file.getOriginalFilename();
		// 附件后缀
		String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 校验上传附件后缀范围
		String uploadScope = minioConfiguration.getUploadScope();
		if (StringUtils.isNoneBlank(uploadScope) && uploadScope.indexOf(fileSuffix.toLowerCase()) == -1) {
			throw new BusinessException("上传附件失败，目前附件只能上传【" + uploadScope + "】格式的附件");
		}
		try {
			// 设置附件绝对路径
			String filePath = businessCode + "/" + LocalDate.now().toString().replaceAll("-", "") + "/"
					+ UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + fileSuffix;
			// 执行上传
			ObjectWriteResponse putObject = minioClient.putObject(PutObjectArgs.builder()
					.bucket(minioConfiguration.getBucketName()).object(filePath)
					.stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
			// 创建附件对象并进行入库
			SystemFilePO systemFile = new SystemFilePO();
			systemFile.setFileName(fileName);
			systemFile.setFileSuffix(fileSuffix);
			systemFile.setBusinessId(businessId);
			systemFile.setBusinessCode(businessCode);
			systemFile.setMinioEtag(putObject.etag());
			systemFile.setFileSize(this.formatFileSize(file.getSize()));
			systemFile.setFilePath("/" + minioConfiguration.getBucketName() + "/" + filePath);
			int insertFlag = this.baseMapper.insert(systemFile);
			if (insertFlag > 0) {
				SystemFileUploadVO systemFileUploadVO = new SystemFileUploadVO();
				systemFileUploadVO.setId(systemFile.getId());
				systemFileUploadVO.setFilePath(systemFile.getFilePath());
				return systemFileUploadVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("上传附件失败，请刷新浏览器重新操作！");
		}
		throw new BusinessException("上传附件失败，请刷新浏览器重新操作！");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月29日 下午2:35:18
	 * 描述 : 转换文件大小（私有方法）
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : formatFileSize
	 *  String  
	 *  @throws
	 */
	private String formatFileSize(Long fileSize) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = df.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format((double) fileSize / 1024) + "KB";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format((double) fileSize / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年09月14日 上午08:55:23
	 * 描述 : 查询和高级查询
	 * 包名 : com.ccit.area.sales.service.bidding.impl
	 * 方法名 : selectPageList
	 *  Result<Page<SystemFilePageListVO>>
	 *  @throws
	 */
	@Override
	public List<SystemFilePageListVO> selectPageList(SystemFilePageListDTO entity) {
		return this.baseMapper.selectPageList(entity.getBusinessCode());
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年09月14日 下午03:55:22
	 * 描述 : 删除附件
	 * 包名 : com.ccit.area.sales.service.bidding.impl
	 * 方法名 : delete
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SystemFileDeleteDTO entity) {
		Long id = entity.getId();
		if (null == id) {
			throw new BusinessException("附件ID不能为空");
		}
		LambdaQueryWrapper<SystemFilePO> wrapper = SystemFilePO.wrapper();
		wrapper.in(SystemFilePO::getId, entity.getId());
		int updateFlag = this.baseMapper.update(new SystemFilePO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

	/**
	 * 创建人 : tb
	 * 创建时间 : 2024年11月7日 下午2:05:33
	 * 描述 : 下载附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : downloadFile
	 * void
	 * @throws
	 */
	@Override
	public void downloadFile(String fileName, String businessId, String businessCode, HttpServletResponse response) throws IOException  {
		String filePath = this.baseMapper.uploadFile(fileName,businessId,businessCode);
		if (null == filePath) {
			throw new BusinessException("附件未找到！");
		}
		String[] split = filePath.split("/");
		String objectName = "/"+split[2]+"/"+split[3]+"/"+split[4];
		try {
			InputStream is = minioClient.getObject(
					GetObjectArgs.builder()
							.bucket(minioConfiguration.getBucketName())
							.object(objectName)
							.build());;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + objectName + "\"");
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (MinioException e) {
			throw new IOException("File download failed: " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * 创建人 : tb
	 * 创建时间 : 2024年11月29日 下午2:05:33
	 * 描述 : 图片预览
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewImage
	 * void
	 * @return
	 * @throws
	 */
	@Override
	public InputStream  previewImage(String fileName, String businessId, String businessCode, HttpServletResponse response) throws Exception{
		String filePath = this.baseMapper.uploadFile(fileName,businessId,businessCode);
		if (null == filePath) {
			throw new BusinessException("附件未找到！");
		}
		String[] split = filePath.split("/");
		String objectName = "/"+split[2]+"/"+split[3]+"/"+split[4];
		return minioClient.getObject(GetObjectArgs.builder().bucket(minioConfiguration.getBucketName()).object(objectName).build());
	}



	/**
	 * 创建人 : cf
	 * 创建时间 : 2024年11月30日 下午17:31:33
	 * 描述 : 预览附件
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : previewAttachment
	 * void
	 * @throws
	 */
	@Override
	public void previewAttachment(String fileName, String businessId, String businessCode, HttpServletResponse response) throws IOException {

		String filePath = baseMapper.uploadFile(fileName, businessId, businessCode);
		if (filePath == null) {
			throw new BusinessException("附件未找到！");
		}
		// 去掉多余的前缀部分
		String bucketName = minioConfiguration.getBucketName();
		if (filePath.startsWith("/" + bucketName)) {
			filePath = filePath.substring(bucketName.length() + 1);
		}

		String fileSuffix = getFileSuffix(filePath);

		// 设置响应头和内容类型
		String contentType = getContentType(fileSuffix);
		response.setContentType(contentType);
		if ("pdf".equalsIgnoreCase(fileSuffix) || "doc".equalsIgnoreCase(fileSuffix) || "docx".equalsIgnoreCase(fileSuffix)) {
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		}
		// 使用 try-with-resources 确保 InputStream 和 OutputStream 都被正确关闭
		try (InputStream is = minioClient.getObject(
				GetObjectArgs.builder()
						.bucket(bucketName)
						.object(filePath)
						.build());
			 // 包含输出流以确保它也会被关闭
			 java.io.OutputStream os = response.getOutputStream()) {

			org.apache.commons.io.IOUtils.copy(is, os);
			os.flush();

		} catch (ErrorResponseException e) {
			ErrorResponse errorResponse = e.errorResponse();
			if ("NoSuchKey".equals(errorResponse.errorCode())) {
				throw new IOException("文件不存在：" + errorResponse.message(), e);
			} else {
				throw new IOException("获取文件对象时发生异常：" + errorResponse.message(), e);
			}
		} catch (InsufficientDataException | InternalException | InvalidBucketNameException |
				 InvalidResponseException | ServerException | XmlParserException | InvalidKeyException | NoSuchAlgorithmException e) {
			throw new IOException("获取文件对象时发生异常：" + e.getMessage(), e);
		}

	}

	private String getFileSuffix(String filePath) {
		return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
	}

	private String getContentType(String fileSuffix) {
		switch (fileSuffix) {
			case "png":
				return "image/png";
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "pdf":
				return "application/pdf";
			case "doc":
				return "application/msword";
			case "docx":
				return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			default:
				throw new BusinessException("不支持的文件格式进行预览");
		}
	}
}
