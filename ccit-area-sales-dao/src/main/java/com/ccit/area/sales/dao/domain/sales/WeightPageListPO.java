package com.ccit.area.sales.dao.domain.sales;

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
  * 描述 : “分量”实体对象
  * 创建人 : cf
  * 创建时间 : 2024年9月12日 下午14::50
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.domain.weight
  * 类名 : WeightPageListPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_quantity_management")
public class WeightPageListPO extends BasePO<WeightPageListPO> {
    private static final long serialVersionUID = 1L;


    /**
     * 公司
     */
    @TableField(value = "company_no")
    private String companyNo;


    private String company;
    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 待分配订单数
     */
    @TableField(value = "pending_order_allocation")
    private String pendingOrderAllocation;

    /**
     * 已分配订单数
     */
    @TableField(value = "assigned_orders")
    private String assignedOrders;

    /**
     * 挂牌编码
     */
    @TableField(value = "listing_no")
    private String listingNo;

    /**
     * 挂牌类型
     */
    @TableField(value = "listing_type")
    private String listingType;

    /**
     * 牌号
     */
    @TableField(value = "brand_number")
    private String brandNumber;

    /**
     * 区域名称
     */
    @TableField(value = "regional_name")
    private String regionalName;

    /**
     * 配送方式
     */
    @TableField(value = "delivery_method")
    private String deliveryMethod;

    /**
     * 运输方式
     */
    @TableField(value = "type_of_shipping")
    private String typeOfShipping;

    /**
     * 支付方式
     */
    @TableField(value = "payment_method")
    private String paymentMethod;

    /**
     * 挂牌含税单价
     */
    @TableField(value = "listed_tax_price")
    private BigDecimal listedTaxPrice;

    /**
     * 单价单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 库存
     */
    @TableField(value = "inventory")
    private String inventory;

    /**
     * 挂牌数量
     */
    @TableField(value = "number_of_listings")
    private BigDecimal numberOfListings;

    /**
     * 贸易类型
     */
    @TableField(value = "trade_type")
    private String tradeType;

    /**
     * 区域编码
     */
    @TableField(value = "region_code")
    private String regionCode;

    /**
     * 有效开始时间
     */
    @TableField(value = "effective_start_time")
    private Date effectiveStartTime;

    /**
     * 有效结束时间
     */
    @TableField(value = "effective_end_time")
    private Date effectiveEndTime;

    /**
     * 制单时间
     */
    @TableField(value = "production_time")
    private Date productionTime;

    /**
     * 商城
     */
    @TableField(value = "shopping_mall")
    private String shoppingMall;

    /**
     * 组织机构编码
     */
    @TableField(value = "organizational_code")
    private String organizationalCode;

    /**
     * 组织机构名称
     */
    @TableField(value = "organization_name")
    private String organizationName;

    /**
     * 定价行编码
     */
    @TableField(value = "pricing_line_code")
    private String pricingLineCode;

    /**
     * 产品分类
     */
    @TableField(value = "product_classification")
    private String productClassification;

    /**
     * 业务分类编码
     */
    @TableField(value = "business_classification_code")
    private String businessClassificationCode;

    /**
     * 提货地分类
     */
    @TableField(value = "classification_location")
    private String classificationLocation;

    /**
     * 币种
     */
    @TableField(value = "currency")
    private String currency;

    /**
     * 含税价
     */
    @TableField(value = "price_including_tax")
    private String priceIncludingTax;

    /**
     * 描述
     */
    @TableField(value = "describs")
    private String describs;

    /**
     * 挂牌不含税价格
     */
    @TableField(value = "listing_price_excluding")
    private BigDecimal listingPriceExcluding;

    /**
     * 单价模式
     */
    @TableField(value = "unit_price_mode")
    private String unitPriceMode;

    /**
     * 价格是否可见
     */
    @TableField(value = "ifprice_visible")
    private String ifPriceVisible;

    /**
     * 价格备注
     */
    @TableField(value = "price_remarks")
    private String priceRemarks;

    /**
     * 重量单位
     */
    @TableField(value = "weight")
    private String weight;

    /**
     * 数量是否可见
     */
    @TableField(value = "ifquantity_visible")
    private String ifQuantityVisible;

    /**
     * 数量备注
     */
    @TableField(value = "quantity_remarks")
    private String quantityRemarks;

    /**
     * 查看附件
     */
    @TableField(value = "view_attachment")
    private String viewAttachment;

    /**
     * 制单人
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 最后更新人编码
     */
    @TableField(value = "last_updated_person_code")
    private String lastUpdatedPersonCode;

    /**
     * 最后更新人
     */
    @TableField(value = "last_updated_person")
    private String lastUpdatedPerson;

    /**
     * 最后更新时间
     */
    @TableField(value = "last_updated_time")
    private Date lastUpdatedTime;

    /**
     * 修改单位
     */
    @TableField(value = "modify_unit")
    private String modifyUnit;

    /**
     * 单位名称
     */
    @TableField(value = "unit_name")
    private String unitName;

    /**
     * 审核标制
     */
    @TableField(value = "shbz")
    private String shbz;

    /**
     * 工作流编码
     */
    @TableField(value = "workflow_encoding")
    private String workflowEncoding;


    /**
     *
      * 创建人 : cf
      * 创建时间 : 2024年9月9日 下午16:07:09
      * 构造方法名 : WeightPageListPO()
      * 描述 :
     */
    public WeightPageListPO() {
    }

    /**
     *
      * 创建人 : cf
      * 创建时间 : 2024年9月9日 下午16:07:09
      * 构造方法名 : WeightPageListPO(Boolean deleted, Boolean isAutoFillUser) 
     * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
     */
    public WeightPageListPO(Boolean deleted, Boolean isAutoFillUser) {
        super.setDeleted(deleted ? 1 : 0);
        super.setDeletedTime(new Date());
        if (isAutoFillUser) {
            super.setDeletedByName(null);
            super.setDeletedTime(null);
        }
    }

    /**
     *
     * 创建人 : cf
     * 创建时间 :  2024年9月9日 下午16:09:13
     * 描述 : 包装器
     * 包名 : com.ccit.area.sales.dao.domain.weight
     * 方法名 : wrapper
     *  LambdaQueryWrapper<WeightPageListPO>
     *  @throws
     */
    public static LambdaQueryWrapper<WeightPageListPO> wrapper() {
        LambdaQueryWrapper<WeightPageListPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WeightPageListPO::getDeleted, 0);
        return wrapper;
    }
}
