package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TPdfFilePO;
import com.ccit.area.sales.dao.vo.customer.TPdfFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_pdf_file(PDF文件表)】的数据库操作Mapper
* @createDate 2024-10-21 15:06:20
* @Entity com.ccit.area.sales.dao.bean.po.TPdfFilePO
*/
public interface PdfFileMapper extends BaseMapper<TPdfFilePO> {

    List<TPdfFileVO> selectAttachment(Page<TPdfFileVO> pages,
                                              @Param(value = "param") Map<String, Object> param);

    List<TPdfFileVO> selectQualification(Page<TPdfFileVO> pages,
                                      @Param(value = "param") Map<String, Object> param);
}




