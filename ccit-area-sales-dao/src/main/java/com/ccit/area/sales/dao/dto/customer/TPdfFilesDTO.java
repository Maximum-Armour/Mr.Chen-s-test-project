package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;

@Data
public class TPdfFilesDTO {
    /**
     *
     */
    private Integer pdfId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 最后修改人
     */
    private String lastModifiedBy;

    /**
     * 制单时间
     */
    private String createTime;
}
