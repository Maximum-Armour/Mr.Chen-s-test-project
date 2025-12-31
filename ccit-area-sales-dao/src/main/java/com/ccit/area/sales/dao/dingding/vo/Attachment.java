package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 附件信息。
 */
@Schema(description = "附件信息")
@Data
public class Attachment {

    /**
     * 附件名称。
     */
    @Schema(description = "附件名称。", required = true, example = "example.pdf")
    private String fileName;

    /**
     * 附件大小。
     */
    @Schema(description = "附件大小。", required = true, example = "123456")
    private String fileSize;

    /**
     * 附件 ID。
     */
    @Schema(description = "附件 ID。", required = true, example = "fileId12345")
    private String fileId;

    /**
     * 附件类型。
     */
    @Schema(description = "附件类型。", required = true, example = "application/pdf")
    private String fileType;

    /**
     * 附件的钉盘空间 ID。
     */
    @Schema(description = "附件的钉盘空间 ID。", required = true, example = "spaceId12345")
    private String spaceId;
}
