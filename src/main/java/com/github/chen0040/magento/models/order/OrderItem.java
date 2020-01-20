package com.github.chen0040.magento.models.order;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
	private String additional_data;
	private BigDecimal amount_refunded;
	private String applied_rule_ids;
	private BigDecimal base_amount_refunded;
	private BigDecimal base_cost;
	private BigDecimal base_discount_amount;
	private BigDecimal base_discount_invoiced;
	private BigDecimal base_discount_refunded;
	private BigDecimal base_discount_tax_compensation_amount;
	private BigDecimal base_discount_tax_compensation_invoiced;
	private BigDecimal base_discount_tax_compensation_refunded;
	private BigDecimal base_original_price;
	private BigDecimal base_price;
	private BigDecimal base_price_incl_tax;
	private BigDecimal base_row_invoiced;
	private BigDecimal base_row_total;
	private BigDecimal base_row_total_incl_tax;
	private BigDecimal base_tax_amount;
	private BigDecimal base_tax_before_discount;
	private BigDecimal base_tax_invoiced;
	private BigDecimal base_tax_refunded;
	private BigDecimal base_weee_tax_applied_amount;
	private BigDecimal base_weee_tax_applied_row_amnt;
	private BigDecimal base_weee_tax_disposition;
	private BigDecimal base_weee_tax_row_disposition;
	private String created_at;
	private String description;
	private BigDecimal discount_amount;
	private BigDecimal discount_invoiced;
	private BigDecimal discount_percent;
	private BigDecimal discount_refunded;
	private long event_id;
	private String ext_order_item_id;
	private long free_shipping;
	private BigDecimal gw_base_price;
	private BigDecimal gw_base_price_invoiced;
	private BigDecimal gw_base_price_refunded;
	private BigDecimal gw_base_tax_amount;
	private BigDecimal gw_base_tax_amount_invoiced;
	private BigDecimal gw_base_tax_amount_refunded;
	private long gw_id;
	private BigDecimal gw_price;
	private BigDecimal gw_price_invoiced;
	private BigDecimal gw_price_refunded;
	private BigDecimal gw_tax_amount;
	private BigDecimal gw_tax_amount_invoiced;
	private BigDecimal gw_tax_amount_refunded;
	private BigDecimal discount_tax_compensation_amount;
	private BigDecimal discount_tax_compensation_canceled;
	private BigDecimal discount_tax_compensation_invoiced;
	private BigDecimal discount_tax_compensation_refunded;
	private long is_qty_decimal;
	private long is_virtual;
	private long item_id;
	private long locked_do_invoice;
	private long locked_do_ship;
	private String name;
	private long no_discount;
	private long order_id;
	private BigDecimal original_price;
	private long parent_item_id;
	private BigDecimal price;
	private BigDecimal price_incl_tax;
	private long product_id;
	private String product_type;
	private long qty_backordered;
	private long qty_canceled;
	private long qty_invoiced;
	private long qty_ordered;
	private long qty_refunded;
	private long qty_returned;
	private long qty_shipped;
	private long quote_item_id;
	private BigDecimal row_invoiced;
	private BigDecimal row_total;
	private BigDecimal row_total_incl_tax;
	private BigDecimal row_weight;
	private String sku;
	private long store_id;
	private BigDecimal tax_amount;
	private BigDecimal tax_before_discount;
	private BigDecimal tax_canceled;
	private BigDecimal tax_invoiced;
	private BigDecimal tax_percent;
	private BigDecimal tax_refunded;
	private String updated_at;
	private String weee_tax_applied;
	private BigDecimal weee_tax_applied_amount;
	private BigDecimal weee_tax_applied_row_amount;
	private BigDecimal weee_tax_disposition;
	private BigDecimal weee_tax_row_disposition;
	private double weight;
	OrderItem parent_item;
	ProductOption product_option;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
}
