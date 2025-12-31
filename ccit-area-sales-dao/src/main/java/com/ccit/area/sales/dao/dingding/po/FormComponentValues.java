package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 创建实例请求参数
 */
@Schema(description = "创建实例请求参数")
@Data
@Builder
public class FormComponentValues {

    /**
     * 控件id，非必填。
     */
    @Schema(description = "控件id，非必填。", required = false, example = "componentId123")
    private String id;

    /**
     * 控件别名，非必填。
     */
    @Schema(description = "控件别名，非必填。", required = false, example = "aliasName")
    private String bizAlias;

    /**
     * 控件名称，必填。
     * 企业内部应用和第三方企业应用需与创建或更新审批表单模板接口中组件label字段值保持一致。
     */
    @Schema(description = "控件名称，必填。企业内部应用和第三方企业应用需与创建或更新审批表单模板接口中组件label字段值保持一致。", required = true, example = "姓名")
    private String name;

    /**
     * 控件值，必填。
     */
    @Schema(description = "控件值，必填。", required = true, example = "张三")
    private String value;

    /**
     * 控件扩展值，非必填。
     */
    @Schema(description = "控件扩展值，非必填。", required = false, example = "{\"key\":\"value\"}")
    private String extValue;

    /**
     * 控件类型，非必填。
     * 取值有 TextField（单行输入框）、TextareaField（多行输入框）、NumberField（数字输入框）、DDSelectField（单选框）、DDMultiSelectField（多选框）、DDDateField（日期控件）、DDDateRangeField（时间区间控件）、TextNote（文字说明控件）、PhoneField（电话控件）、DDPhotoField（图片控件）、MoneyField（金额控件）、TableField（明细控件）、DDAttachment（附件）、InnerContactField（联系人控件）、RelateField（关联审批单）、AddressField（省市区控件）、StarRatingField（评分控件）、DepartmentField（部门控件）。
     */
    @Schema(description = "控件类型，非必填。取值有 TextField（单行输入框）、TextareaField（多行输入框）、NumberField（数字输入框）、DDSelectField（单选框）、DDMultiSelectField（多选框）、DDDateField（日期控件）、DDDateRangeField（时间区间控件）、TextNote（文字说明控件）、PhoneField（电话控件）、DDPhotoField（图片控件）、MoneyField（金额控件）、TableField（明细控件）、DDAttachment（附件）、InnerContactField（联系人控件）、RelateField（关联审批单）、AddressField（省市区控件）、StarRatingField（评分控件）、DepartmentField（部门控件）。", required = false, example = "TextField")
    private String componentType;

    /**
     * 子控件列表，非必填，最大列表长度为150。
     */
    @Schema(description = "子控件列表，非必填，最大列表长度为150。", required = false)
    private List<Details> details;
}
