package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PDF文件表
 * @TableName t_pdf_file
 */
@TableName(value ="t_pdf_file")
@Data
public class TPdfFilePO implements Serializable {
    /**
     * 
     */
    @TableField("pdf_id")
    private Integer pdfId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 最后修改人
     */
    @TableField("pdf_id")
    private String last_modified_by;

    /**
     * 制单时间
     */
    @TableField("create_time")
    private Date createTime;

}