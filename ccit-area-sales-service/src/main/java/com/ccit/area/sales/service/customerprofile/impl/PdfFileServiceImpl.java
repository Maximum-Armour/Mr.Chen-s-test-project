package com.ccit.area.sales.service.customerprofile.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.dao.domain.customer.TPdfFilePO;
import com.ccit.area.sales.dao.mapper.customerprofile.PdfFileMapper;
import com.ccit.area.sales.dao.vo.customer.TPdfFileVO;
import com.ccit.area.sales.service.customerprofile.PdfFileService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_pdf_file(PDF文件表)】的数据库操作Service实现
* @createDate 2024-10-21 15:06:20
*/
@Service
public class PdfFileServiceImpl extends ServiceImpl<PdfFileMapper, TPdfFilePO>
    implements PdfFileService {

    @Override
    public Page<TPdfFileVO> selectAttachment(Map<String, Object> param) {
        // 获取并验证页码和每页大小
        Integer page = param.containsKey("page") ? (Integer) param.get("page") : null;
        Integer size = param.containsKey("size") ? (Integer) param.get("size") : null;

        int defaultPage = 1; // 默认页码
        int defaultSize = 10; // 默认每页大小

        if (page == null || page <= 0) {
            page = defaultPage;
        }
        if (size == null || size <= 0) {
            size = defaultSize;
        }

        // 创建分页对象
        Page<TPdfFileVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);

        // 执行数据库查询
        pages.setRecords(this.baseMapper.selectAttachment(pages, param));

        return pages;
    }

    @Override
    public Page<TPdfFileVO> selectQualification(Map<String, Object> param) {
        // 获取并验证页码和每页大小
        Integer page = param.containsKey("page") ? (Integer) param.get("page") : null;
        Integer size = param.containsKey("size") ? (Integer) param.get("size") : null;

        int defaultPage = 1; // 默认页码
        int defaultSize = 10; // 默认每页大小

        if (page == null || page <= 0) {
            page = defaultPage;
        }
        if (size == null || size <= 0) {
            size = defaultSize;
        }

        // 创建分页对象
        Page<TPdfFileVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);

        // 执行数据库查询
        pages.setRecords(this.baseMapper.selectQualification(pages, param));

        return pages;
    }
}




