package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "t_targetinfo")
public class TargetInfoPO{
    /**
     * 标的ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 产品ID
     */
    @TableField(value = "productid")
    private String productid;

    /**
     * 默认1:以数量计算,0以热量计算
     */
    @TableField(value = "htype")
    private Integer htype;

    /**
     * 竞价名称
     */
    @TableField(value = "bidname")
    private String bidname;

    /**
     * 竞价数量
     */
    @TableField(value = "quantity")
    private String quantity;

    /**
     * 基价报价
     */
    @TableField(value = "bidform")
    private String bidform;

    /**
     * 竞价基价
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 最小加价
     */
    @TableField(value = "minmarkup")
    private String minmarkup;

    /**
     * 底价可见
     */
    @TableField(value = "pricestates")
    private String pricestates;

    /**
     * 支付方式
     */
    @TableField(value = "payment")
    private String payment;

    /**
     * 保证金
     */
    @TableField(value = "deposit")
    private String deposit;

    /**
     * 保证金截止时间
     */
    @TableField(value = "depositendtime")
    private Date depositendtime;

    /**
     * 竞价开始时间
     */
    @TableField(value = "begintime")
    private Date begintime;

    /**
     * 竞价结束时间
     */
    @TableField(value = "endtime")
    private Date endtime;

    /**
     * 交割地
     */
    @TableField(value = "delivery")
    private String delivery;

    /**
     * 提货方式
     */
    @TableField(value = "deliverydetail")
    private String deliverydetail;

    /**
     * 交货开始时间
     */
    @TableField(value = "trbegintime")
    private Date trbegintime;

    /**
     * 交货结束时间
     */
    @TableField(value = "trendtime")
    private Date trendtime;

    /**
     * 联系人
     */
    @TableField(value = "contacts")
    private String contacts;

    /**
     * 联系电话
     */
    @TableField(value = "tel")
    private String tel;

    /**
     * 是否支持拆分拍0否1是
     */
    @TableField(value = "splitstate")
    private String splitstate;

    /**
     * 拆分拍最小加价数量
     */
    @TableField(value = "minquantity")
    private String minquantity;

    /**
     * 拆分拍最小起拍数量
     */
    @TableField(value = "minbquantity")
    private String minbquantity;

    /**
     * 竞价状态0：已拍下
     * 1：已流拍
     * 2：关闭中
     * 3：进行中
     * 4：准备中
     * 5：无人报名
     * 6：报名人数不足
     * 7：竞拍人数不足
     */
    @TableField(value = "outbidstate")
    private String outbidstate;

    /**
     * 其他说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 公告
     */
    @TableField(value = "notice")
    private String notice;

    /**
     * 规则
     */
    @TableField(value = "rule")
    private String rule;

    /**
     * 所属组织
     */
    @TableField(value = "groupId")
    private String groupId;

    /**
     * 审核状态
     */
    @TableField(value = "auditstate")
    private String auditstate;

    /**
     * 删除标示
     */
    @TableField(value = "deleted")
    private String deleted;

    /**
     * 创建人
     */
    @TableField(value = "createuser")
    private String createuser;

    /**
     * 创建时间
     */
    @TableField(value = "createtime", fill = FieldFill.INSERT)
    private Date createtime;

    /**
     * 更新人
     */
    @TableField(value = "updateuser")
    private String updateuser;

    /**
     * 更新时间
     */
    @TableField(value = "updatetime", fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;

    /**
     * 审批人
     */
    @TableField(value = "audituser")
    private String audituser;

    /**
     * 审批时间
     */
    @TableField(value = "audittime")
    private Date audittime;

    /**
     * 竞价模式(0:手动，1:自动)
     */
    @TableField(value = "biddingmode")
    private String biddingmode;

    /**
     * 延迟时间（单位分钟）
     */
    @TableField(value = "lapsetime")
    private String lapsetime;

    /**
     * 当前延拍次数
     */
    @TableField(value = "lapsecount")
    private String lapsecount;

    /**
     * 允许延拍次数
     */
    @TableField(value = "lapsemaxcount")
    private String lapsemaxcount;

    /**
     * 监督电话
     */
    @TableField(value = "supervisetel")
    private String supervisetel;

    /**
     * 公告pdf保存路径
     */
    @TableField(value = "notice_path")
    private String noticePath;

    /**
     * 竞价规则pdf保存路径
     */
    @TableField(value = "rule_path")
    private String rulePath;

    /**
     * '0':不启动最低人数限制 '1':启动最低人数限制
     */
    @TableField(value = "minpeopleflg")
    private String minpeopleflg;

    /**
     * 竞拍最低人数
     */
    @TableField(value = "minpeoplecount")
    private String minpeoplecount;

    /**
     * 合同模板id
     */
    @TableField(value = "contracttemplateid")
    private String contracttemplateid;

    /**
     * 过程价格可见：1，不可见：0
     */
    @TableField(value = "processflg")
    private String processflg;

    /**
     * 报告通知单位
     */
    @TableField(value = "coalmine")
    private String coalmine;

    /**
     * 是否限价 0:否，1:是
     */
    @TableField(value = "lpriceflg")
    private String lpriceflg;

    /**
     * 竞价限价
     */
    @TableField(value = "lprice")
    private BigDecimal lprice;

    /**
     * 租户编码
     */
    @TableField(value = "tenant_code")
    private Long tenantCode;

    /**
     * 交易商品:1冶金煤，2动力煤，3煤炭副产品
     */
    @TableField(value = "coal_type")
    private Integer coalType;

    /**
     * 标签-部门id-矿标签
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 数据来源 电商2.0-电商1.0
     */
    @TableField(value = "db_from")
    private String dbFrom;

    /**
     * 数据接入时间
     */
    @TableField(value = "`db_from _date`")
    private Date dbFromDate;

    /**
     * 价格会编号
     */
    @TableField(value = "huiyi_no")
    private String huiyiNo;

    /**
     * 价格会id
     */
    @TableField(value = "huiyi_id")
    private String huiyiId;

    /**
     * 会议日期
     */
    @TableField(value = "meet_time")
    private Date meetTime;

    /**
     * 规格
     */
    @TableField(value = "specifiy")
    private String specifiy;

    /**
     * t_sales_bidding 表 主键id
     */
    @TableField(value = "t_sales_bidding_id")
    private String tSalesBiddingId;
}
