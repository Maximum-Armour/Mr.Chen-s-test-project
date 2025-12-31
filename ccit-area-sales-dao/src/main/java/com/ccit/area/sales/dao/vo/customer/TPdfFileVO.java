package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.util.Date;

@Data
public class TPdfFileVO {

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
    private Date createTime;

}
