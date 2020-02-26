package com.github.chen0040.magento.models.invoice;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.sales.SalesDataAddress;
import com.github.chen0040.magento.models.sales.SalesDataComment;
import com.github.chen0040.magento.models.sales.SalesDataItem;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Invoice {
	 private String base_currency_code;
	 private BigDecimal base_discount_amount;
	 private BigDecimal base_grand_total;
	 private BigDecimal base_discount_tax_compensation_amount;
	 private BigDecimal base_shipping_amount;
	 private BigDecimal base_shipping_discount_tax_compensation_amnt;
	 private BigDecimal base_shipping_incl_tax;
	 private BigDecimal base_shipping_tax_amount;
	 private BigDecimal base_subtotal;
	 private BigDecimal base_subtotal_incl_tax;
	 private BigDecimal base_tax_amount;
	 private BigDecimal base_total_refunded;
	 private BigDecimal base_to_global_rate;
	 private BigDecimal base_to_order_rate;
	 private BigDecimal billing_address_id;
	 private Integer can_void_flag;
	 private Date created_at;
	 private BigDecimal discount_amount;
	 private String discount_description;
	 private Integer email_sent;
	 private Integer entity_id;
	 private String global_currency_code;
	 private BigDecimal grand_total;
	 private BigDecimal discount_tax_compensation_amount;
	 private String increment_id;
	 private Integer is_used_for_refund;
	 private String order_currency_code;
	 private Integer order_id;
	 private Integer shipping_address_id;
	 private BigDecimal shipping_amount;
	 private BigDecimal shipping_discount_tax_compensation_amount;
	 private BigDecimal shipping_incl_tax;
	 private BigDecimal shipping_tax_amount;
	 private Integer state;
	 private String store_currency_code;
	 private Integer store_id;
	 private BigDecimal store_to_base_rate;
	 private BigDecimal store_to_order_rate;
	 private BigDecimal subtotal;
	 private BigDecimal subtotal_incl_tax;
	 private BigDecimal tax_amount;
	 private Integer total_qty;
	 private String transaction_id;
	 private Date updated_at;
	 private List<SalesDataItem> items;
	 private List<SalesDataComment> comments;
	 private InvoiceExtensionAttributes extension_attributes;
	 
	 @Getter
	 @Setter
	 @NoArgsConstructor
	 public static class InvoiceExtensionAttributes {
		 private BigDecimal base_customer_balance_amount;
		 private BigDecimal customer_balance_amount;
		 private BigDecimal base_gift_cards_amount;
		 private BigDecimal gift_cards_amount;
		 private String gw_base_price;
		 private String gw_price;
		 private String gw_items_base_price;
		 private String gw_items_price;
		 private String gw_card_base_price;
		 private String gw_card_price;
		 private String gw_base_tax_amount;
		 private String gw_tax_amount;
		 private String gw_items_base_tax_amount;
		 private String gw_items_tax_amount;
		 private String gw_card_base_tax_amount;
		 private String gw_card_tax_amount;
		 private SalesDataAddress vertex_tax_calculation_shipping_address;
		 private SalesDataAddress vertex_tax_calculation_billing_address;
		 private Order vertex_tax_calculation_order;
	 }
}
