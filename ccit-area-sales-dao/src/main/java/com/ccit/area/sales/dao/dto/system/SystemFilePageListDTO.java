package com.ccit.area.sales.dao.dto.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 *
  * 描述 : “附件分页”DTO
  * 创建人 : tb
  * 创建时间 : 2024年10月31日 上午9:12:13
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SystemFilePageListDTO
 */
@Data
@Schema(description = "【附件分页】接受参数实体类")
public class SystemFilePageListDTO {
    /**
     * 附件名称
     */
    @Length(max = 100, message = "附件名称不能超过100个字符")
    @Schema(description = "附件名称")
    private String fileName;

    /**
     * 业务编码
     */
    @Length(max = 50, message = "附件名称不能超过50个字符")
    @Schema(description = "业务编码")
    private String businessCode;

    /**
     * 附件后缀
     */
    @Length(max = 100, message = "附件后缀不能超过100个字符")
    @Schema(description = "附件后缀")
    private String fileSuffix;

    /**
     * 最后修改人
     */
    @Length(max = 50, message = "最后修改人不能超过50个字符")
    @Schema(description = "最后修改人")
    private String updateByName;

    /**
     * 制单时间
     */
    @Schema(description = "制单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
