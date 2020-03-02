package com.github.chen0040.magento.models.cart;

import java.math.BigDecimal;
import java.util.List;

import com.github.chen0040.magento.models.MagentoAttribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NegotiableQuote {
	private Integer quote_id;
	private Boolean is_regular_quote;
	private String status;
	private BigDecimal negotiated_price_type;
	private BigDecimal negotiated_price_value;
	private BigDecimal shipping_price;
	private String quote_name;
	private String expiration_period;
	private Integer email_notification_status;
	private Boolean has_unconfirmed_changes;
	private Boolean is_shipping_tax_changed;
	private Boolean is_customer_price_changed;
	private Integer notifications;
	private String applied_rule_ids;
	private Boolean is_address_draft;
	private String deleted_sku;
	private Integer creator_id;
	private Integer creator_type;
	private BigDecimal original_total_price;
	private BigDecimal base_original_total_price;
	private BigDecimal negotiated_total_price;
	private BigDecimal base_negotiated_total_price;
	private List<MagentoAttribute<?>> extension_attributes;
}
