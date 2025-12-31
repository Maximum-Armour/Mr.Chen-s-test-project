package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 手动操作文件对象
 */
@Schema(description = "手动操作文件对象")
@Data
public class ManualOperationfile {

    /**
     * 图片URL地址列表。
     */
    @Schema(description = "图片URL地址列表。", required = false, example = "[\"https://example.com/photo1.jpg\", \"https://example.com/photo2.jpg\"]")
    private List<String> photos;

    /**
     * 附件列表。
     */
    @Schema(description = "附件列表。", required = false, example = "[{\"name\": \"attachment1.pdf\", \"url\": \"https://example.com/attachment1.pdf\"}]")
    private List<Object> attachments;

    /**
     * 钉盘空间ID。
     */
    @Schema(description = "钉盘空间ID。", required = false, example = "SPACE12345")
    private String spaceId;

    /**
     * 文件大小。
     */
    @Schema(description = "文件大小。", required = false, example = "1024")
    private String fileSize;

    /**
     * 文件ID。
     */
    @Schema(description = "文件ID。", required = false, example = "FILE12345")
    private String fileId;

    /**
     * 文件名称。
     */
    @Schema(description = "文件名称。", required = false, example = "example.pdf")
    private String fileName;

    /**
     * 文件类型。
     */
    @Schema(description = "文件类型。", required = false, example = "application/pdf")
    private String fileType;
}
