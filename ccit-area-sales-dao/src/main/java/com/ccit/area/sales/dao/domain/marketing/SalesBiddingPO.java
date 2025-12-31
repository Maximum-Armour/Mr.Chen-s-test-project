package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “产品竞价”实体类
  * 创建人 : tb
  * 创建时间 : 2024年9月5日 下午4:13:15
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.domain.bidding
  * 类名 : SalesBiddingPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_sales_bidding")
public class SalesBiddingPO extends BasePO<SalesBiddingPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 竞价编码
     */
    @TableField(value = "bidding_no")
    private String biddingNo;

    /**
     * 产品分类编码
     */
    @TableField(value = "product_class_no")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @TableField(value = "product_class_name")
    private String productClassName;

    /**
     * 牌号编码
     */
    @TableField(value = "sku_no")
    private String skuNo;

    /**
     * 牌号名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 产品编码
     */
    @TableField(value = "materiel_no")
    private String materielNo;

    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
    private String materielName;

    /**
     * 规格
     */
    @TableField(value = "specifications")
    private String specifications;

    /**
     * 数量
     */
    @TableField(value = "quantity")
    private String quantity;

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 区域编码
     */
    @TableField(value = "area_no")
    private String areaNo;
    /**
     * 区域名称
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * '提货地编码'
     */
    @TableField(value = "delivery_place_no")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @TableField(value = "delivery_place_name")
    private String deliveryPlaceName;
    /**
     * 提货地分类编码
     */
    @TableField(value = "delivery_place_class_no")
    private String deliveryPlaceClassNo;
    /**
     * 提货地分类名称
     */
    @TableField(value = "delivery_place_class_name")
    private String deliveryPlaceClassName;

    /**
     * 仓库编码
     */
    @TableField(value = "depot_no")
    private String depotNo;
    /**
     * 提货仓库名称
     */
    @TableField(value = "depot_name")
    private String depotName;

    /**
     * 配送方式
     */
    @TableField(value = "delivery_method")
    private String deliveryMethod;

    /**
     * 运输方式
     */
    @TableField(value = "shipping_type")
    private String shippingType;

    /**
     * 支付方式
     */
    @TableField(value = "payment_method")
    private String paymentMethod;

    /**
     * 单价模式
     */
    @TableField(value = "unit_price_mode")
    private String unitPriceMode;

    /**
     * 其他事项说明
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 竞价模式
     */
    @TableField(value = "bidding_mode")
    private String biddingMode;

    /**
     * 招标编号
     */
    @TableField(value = "tender_number")
    private String tenderNumber;

    /**
     * 币种
     */
    private String currency;

    /**
     * 是否需要保证金
     */
    @TableField(value = "earnest_money")
    private String earnestMoney;

    /**
     * 是否需要确认
     */
    @TableField(value = "confirm")
    private String confirm;

    /**
     * 是否允许延时
     */
    @TableField(value = "allowed_delay")
    private String allowedDelay;

    /**
     * 最少投标人
     */
    @TableField(value = "min_bidders")
    private String minBidders;

    /**
     * 是否需要报名
     */
    @TableField(value = "if_need_apply")
    private String ifNeedApply;

    /**
     * 竞价底价建议（元）
     */
    @TableField(value = "low_price_suggestion")
    private BigDecimal lowPriceSuggestion;

    /**
     * 竞价底价（元）
     */
    @TableField(value = "low_price")
    private BigDecimal lowPrice;

    /**
     * 最低加价幅度（元）
     */
    @TableField(value = "min_markup")
    private BigDecimal minMarkup;

    /**
     * 保证金
     */
    @TableField(value = "bond")
    private BigDecimal bond;

    /**
     * 报名截止时间
     */
    @TableField(value = "sign_up_end_time")
    private Date signUpEndTime;

    /**
     * 保证金截止时间
     */
    @TableField(value = "bond_end_time")
    private Date bondEndTime;

    /**
     * 起拍时间
     */
    @TableField(value = "starting_time")
    private Date startingTime;

    /**
     * 竞价时长
     */
    @TableField(value = "bidding_duration")
    private BigDecimal biddingDuration;

    /**
     * 起拍量（吨）
     */
    @TableField(value = "starting_quantity")
    private BigDecimal startingQuantity;

    /**
     * 加量幅度（吨）
     */
    @TableField(value = "increase_amplitude")
    private BigDecimal increaseAmplitude;

    /**
     * 延时时长
     */
    @TableField(value = "delay_duration")
    private Long delayDuration;

    /**
     * 距离结束时长
     */
    @TableField(value = "distance_end_duration")
    private Long distanceEndDuration;

    /**
     * 审核标志
     */
    @TableField(value = "shbz")
    private String shbz;

    /**
     * 组织编码
     */
    @TableField(value = "org_no")
    private String orgNo;

    /**
     * 组织名称
     */
    @TableField(value = "org_name")
    private String orgName;

    /**
     * 公司编码
     */
    @TableField(value = "company_no")
    private String companyNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 区域
     */
    @TableField(value = "regional_bidding")
    private String regionalBidding;

    /**
     * 报名状态
     */
    @TableField(value = "registration_status")
    private String registrationStatus;

    /**
     * 最高成交单价
     */
    @TableField(value = "transaction_price_max")
    private BigDecimal transactionPriceMax;

    /**
     * 最低成交单价
     */
    @TableField(value = "transaction_price_min")
    private BigDecimal transactionPriceMin;

    /**
     * 成交总量
     */
    @TableField(value = "transaction_quantity_total")
    private BigDecimal transactionQuantityTotal;

    /**
     * 成交客户数
     */
    @TableField(value = "transaction_customer")
    private String transactionCustomer;

    /**
     * 参与客户数
     */
    @TableField(value = "total_customer")
    private String totalCustomer;

    /**
     * 竞价延长次数
     */
    @TableField(value = "delay_frequency")
    private String delayFrequency;

    /**
     * 产品说明
     */
    @TableField(value = "product_description")
    private String productDescription;

    /**
     * 竞价说明
     */
    @TableField(value = "bidding_description")
    private String biddingDescription;

    /**
     * 启用状态
     */
    @TableField(value = "enable_status")
    private String enableStatus;

    /**
     * 结果确认
     */
    @TableField(value = "order_bidding_no")
    private String orderBiddingNo;

    /**
     * 工作流编码
     */
    @TableField(value = "workflowid")
    private String workflowid;

    /**
     *
      * 创建人 : tb
      * 创建时间 : 2024年9月5日 下午5:11:05
      * 构造方法名 : SalesBiddingPO()
      * 描述 :
     */
    public SalesBiddingPO() {
    }

    /**
     *
      * 创建人 : tb
      * 创建时间 : 2024年9月5日 下午5:11:15
      * 构造方法名 : SalesBiddingPO(Boolean deleted, Boolean isAutoFillUser)
     * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
     */
    public SalesBiddingPO(Boolean deleted, Boolean isAutoFillUser) {
        super.setDeleted(deleted ? 1 : 0);
        super.setDeletedTime(new Date());
        if (isAutoFillUser) {
            super.setDeletedByName(null);
            super.setDeletedTime(null);
        }
    }

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年9月5日 下午5:12:08
     * 描述 : 包装器
     * 包名 : com.ccit.area.sales.dao.domain.bidding
     * 方法名 : wrapper
     *  LambdaQueryWrapper<SalesBiddingPO>
     *  @throws
     */
    public static LambdaQueryWrapper<SalesBiddingPO> wrapper() {
        LambdaQueryWrapper<SalesBiddingPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SalesBiddingPO::getDeleted, 0);
        return wrapper;
    }
}
