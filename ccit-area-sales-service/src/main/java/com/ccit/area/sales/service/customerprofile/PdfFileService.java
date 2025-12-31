package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TPdfFilePO;
import com.ccit.area.sales.dao.vo.customer.TPdfFileVO;

import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_pdf_file(PDF文件表)】的数据库操作Service
 * @createDate 2024-10-21 15:06:20
 */
public interface PdfFileService extends IService<TPdfFilePO> {

    Page<TPdfFileVO> selectAttachment(Map<String, Object> param);

    Page<TPdfFileVO> selectQualification(Map<String, Object> param);
}
