package com.github.chen0040.magento.models.creditmemo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.sales.SalesDataComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditMemo {
	private BigDecimal adjustment;
	private BigDecimal adjustment_negative;
	private BigDecimal adjustment_positive;
	private BigDecimal base_adjustment;
	private BigDecimal base_adjustment_negative;
	private BigDecimal base_adjustment_positive;
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
	private BigDecimal base_to_global_rate;
	private BigDecimal base_to_order_rate;
	private Integer billing_address_id;
	private Date created_at;
	private Integer creditmemo_status;
	private BigDecimal discount_amount;
	private String discount_description;
	private Integer email_sent;
	private Integer entity_id;
	private String global_currency_code;
	private BigDecimal grand_total;
	private BigDecimal discount_tax_compensation_amount;
	private String increment_id;
	private Integer invoice_id;
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
	private String transaction_id;
	private Date updated_at;
	private List<CreditMemoItem> items;
	private List<SalesDataComment> comments;
	private ExtensionAttributes extension_attributes;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
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
	}
	
	public static enum RefundType {
		OFFLINE(true),
		ONLINE(false);
		
		private Boolean value;
		
		private RefundType(Boolean value) {
			this.value = value;
		}
		
		public Boolean getValue() {
			return value;
		}
	}
}
