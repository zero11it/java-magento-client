package com.github.chen0040.magento.models.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.sales.GiftMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
	private Date created_at;
	private String description;
	private BigDecimal discount_amount;
	private BigDecimal discount_invoiced;
	private BigDecimal discount_percent;
	private BigDecimal discount_refunded;
	private Integer event_id;
	private String ext_order_item_id;
	private Integer free_shipping;
	private BigDecimal gw_base_price;
	private BigDecimal gw_base_price_invoiced;
	private BigDecimal gw_base_price_refunded;
	private BigDecimal gw_base_tax_amount;
	private BigDecimal gw_base_tax_amount_invoiced;
	private BigDecimal gw_base_tax_amount_refunded;
	private Integer gw_id;
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
	private Integer is_qty_decimal;
	private Integer is_virtual;
	private Integer item_id;
	private Integer locked_do_invoice;
	private Integer locked_do_ship;
	private String name;
	private Integer no_discount;
	private Integer order_id;
	private BigDecimal original_price;
	private Integer parent_item_id;
	private BigDecimal price;
	private BigDecimal price_incl_tax;
	private Integer product_id;
	private String product_type;
	private Integer qty_backordered;
	private Integer qty_canceled;
	private Integer qty_invoiced;
	private Integer qty_ordered;
	private Integer qty_refunded;
	private Integer qty_returned;
	private Integer qty_shipped;
	private Integer quote_item_id;
	private BigDecimal row_invoiced;
	private BigDecimal row_total;
	private BigDecimal row_total_incl_tax;
	private BigDecimal row_weight;
	private String sku;
	private Integer store_id;
	private BigDecimal tax_amount;
	private BigDecimal tax_before_discount;
	private BigDecimal tax_canceled;
	private BigDecimal tax_invoiced;
	private BigDecimal tax_percent;
	private BigDecimal tax_refunded;
	private Date updated_at;
	private String weee_tax_applied;
	private BigDecimal weee_tax_applied_amount;
	private BigDecimal weee_tax_applied_row_amount;
	private BigDecimal weee_tax_disposition;
	private BigDecimal weee_tax_row_disposition;
	private Double weight;
	private OrderItem parent_item;
	private ProductOption product_option;
	private ExtensionAttributes extension_attributes;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		 private GiftMessage gift_message;
		 private String gw_id;
		 private String gw_base_price;
		 private String gw_price;
		 private String gw_base_tax_amount;
		 private String gw_tax_amount;
		 private String gw_base_price_invoiced;
		 private String gw_price_invoiced;
		 private String gw_base_tax_amount_invoiced;
		 private String gw_tax_amount_invoiced;
		 private String gw_base_price_refunded;
		 private String gw_price_refunded;
		 private String gw_base_tax_amount_refunded;
		 private String gw_tax_amount_refunded;
		 private List<String> vertex_tax_codes;
		 private List<String> invoice_text_codes;
		 private List<String> tax_codes;
	}
}
