package com.github.chen0040.magento.models.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.sales.AmazonOrderLink;
import com.github.chen0040.magento.models.sales.GiftCard;
import com.github.chen0040.magento.models.sales.GiftMessage;
import com.github.chen0040.magento.models.sales.SalesDataAddress;
import com.github.chen0040.magento.models.sales.SalesDataAppliedTax;
import com.github.chen0040.magento.models.sales.SalesDataPayment;
import com.github.chen0040.magento.models.sales.SalesDataShippingAssignment;
import com.github.chen0040.magento.models.sales.SalesDataItem;
import com.github.chen0040.magento.models.sales.SalesDataItemAppliedTax;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {
	private BigDecimal adjustment_negative;
	private BigDecimal adjustment_positive;
	private String applied_rule_ids;
	private BigDecimal base_adjustment_negative;
	private BigDecimal base_adjustment_positive;
	private String base_currency_code;
	private BigDecimal base_discount_amount;
	private BigDecimal base_discount_canceled;
	private BigDecimal base_discount_invoiced;
	private BigDecimal base_discount_refunded;
	private BigDecimal base_grand_total;
	private BigDecimal base_discount_tax_compensation_amount;
	private BigDecimal base_discount_tax_compensation_invoiced;
	private BigDecimal base_discount_tax_compensation_refunded;
	private BigDecimal base_shipping_amount;
	private BigDecimal base_shipping_canceled;
	private BigDecimal base_shipping_discount_amount;
	private BigDecimal base_shipping_discount_tax_compensation_amnt;
	private BigDecimal base_shipping_incl_tax;
	private BigDecimal base_shipping_invoiced;
	private BigDecimal base_shipping_refunded;
	private BigDecimal base_shipping_tax_amount;
	private BigDecimal base_shipping_tax_refunded;
	private BigDecimal base_subtotal;
	private BigDecimal base_subtotal_canceled;
	private BigDecimal base_subtotal_incl_tax;
	private BigDecimal base_subtotal_invoiced;
	private BigDecimal base_subtotal_refunded;
	private BigDecimal base_tax_amount;
	private BigDecimal base_tax_canceled;
	private BigDecimal base_tax_invoiced;
	private BigDecimal base_tax_refunded;
	private BigDecimal base_total_canceled;
	private BigDecimal base_total_due;
	private BigDecimal base_total_invoiced;
	private BigDecimal base_total_invoiced_cost;
	private BigDecimal base_total_offline_refunded;
	private BigDecimal base_total_online_refunded;
	private BigDecimal base_total_paid;
	private BigDecimal base_total_qty_ordered;
	private BigDecimal base_total_refunded;
	private BigDecimal base_to_global_rate;
	private BigDecimal base_to_order_rate;
	private Integer billing_address_id;
	private Integer can_ship_partially;
	private Integer can_ship_partially_item;
	private String coupon_code;
	private Date created_at;
	private String customer_dob;
	private String customer_email;
	private String customer_firstname;
	private Integer customer_gender;
	private Integer customer_group_id;
	private Integer customer_id;
	private Integer customer_is_guest;
	private String customer_lastname;
	private String customer_middlename;
	private String customer_note;
	private Integer customer_note_notify;
	private String customer_prefix;
	private String customer_suffix;
	private String customer_taxvat;
	private BigDecimal discount_amount;
	private BigDecimal discount_canceled;
	private String discount_description;
	private Integer discount_invoiced;
	private Integer discount_refunded;
	private BigDecimal discount_tax_compensation_amount;
	private BigDecimal discount_tax_compensation_invoiced;
	private BigDecimal discount_tax_compensation_refunded;
	private Integer edit_increment;
	private Integer email_sent;
	private Integer entity_id;
	private String ext_customer_id;
	private String ext_order_id;
	private Integer forced_shipment_with_invoice;
	private String global_currency_code;
	private BigDecimal grand_total;
	private String hold_before_state;
	private String hold_before_status;
	private String increment_id;
	private Integer is_virtual;
	private String order_currency_code;
	private String original_increment_id;
	private BigDecimal payment_authorization_amount;
	private Integer payment_auth_expiration;
	private String protect_code;
	private Integer quote_address_id;
	private Integer quote_id;
	private String relation_child_id;
	private String relation_child_real_id;
	private String relation_parent_id;
	private String relation_parent_real_id;
	private String remote_ip;
	private BigDecimal shipping_amount;
	private BigDecimal shipping_canceled;
	private String shipping_description;
	private BigDecimal shipping_discount_amount;
	private BigDecimal shipping_discount_tax_compensation_amount;
	private BigDecimal shipping_incl_tax;
	private BigDecimal shipping_invoiced;
	private BigDecimal shipping_refunded;
	private BigDecimal shipping_tax_amount;
	private BigDecimal shipping_tax_refunded;
	private String state;
	private String status;
	private String store_currency_code;
	private Integer store_id;
	private String store_name;
	private BigDecimal store_to_base_rate;
	private BigDecimal store_to_order_rate;
	private BigDecimal subtotal;
	private BigDecimal subtotal_canceled;
	private BigDecimal subtotal_incl_tax;
	private BigDecimal subtotal_invoiced;
	private BigDecimal subtotal_refunded;
	private BigDecimal tax_amount;
	private BigDecimal tax_canceled;
	private BigDecimal tax_invoiced;
	private BigDecimal tax_refunded;
	private BigDecimal total_canceled;
	private BigDecimal total_due;
	private BigDecimal total_invoiced;
	private Integer total_item_count;
	private BigDecimal total_offline_refunded;
	private BigDecimal total_online_refunded;
	private BigDecimal total_paid;
	private BigDecimal total_qty_ordered;
	private BigDecimal total_refunded;
	private Date updated_at;
	private Double weight;
	private String x_forwarded_for;
	private List<OrderItem> items;
	private SalesDataAddress billing_address;
	private SalesDataPayment payment;
	private List<StatusHistory> status_histories;
	private ExtensionAttributes extension_attributes;

	public static class STATUS {
		public static final String PROCESSING = "processing";
		public static final String SUSPECTED_FRAUD = "fraud";
		public static final String PENDING_PAYMENT = "pending_payment";
		public static final String PAYMENT_REVIEW = "payment_review";
		public static final String PENDING = "pending";
		public static final String ON_HOLD = "holded";
		public static final String OPEN = "STATE_OPEN";
		public static final String COMPLETE = "complete";
		public static final String CLOSED = "closed";
		public static final String CANCELED = "canceled";
		public static final String PAYPAL_CANCELED_REVERSAL = "processing";
		public static final String PENDING_PAYPAL = "pending_paypal";
		public static final String PAYPAL_REVERSED = "paypal_reversed";
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class StatusHistory {
		private String comment;
		private String created_at;
		private Integer entity_id;
		private String entity_name;
		private Integer is_customer_notified;
		private Integer is_visible_on_front;
		private Integer parent_id;
		private String status;
		@JSONField(deserializeUsing = AttributeValueDeserializer.class)
		private List<MagentoAttribute<?>> extension_attributes;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		private List<ShippingAssignment> shipping_assignments;
		private List<PaymentAdditionalInfo> payment_additional_info;
		private List<SalesDataAppliedTax> applied_taxes;
		private List<SalesDataItemAppliedTax> item_applied_taxes;
		private Boolean converting_from_quote;
		private CompanyOrderAttributes company_order_attributes;
		private BigDecimal base_customer_balance_amount;
		private BigDecimal customer_balance_amount;
		private BigDecimal base_customer_balance_invoiced;
		private BigDecimal customer_balance_invoiced;
		private BigDecimal base_customer_balance_refunded;
		private BigDecimal customer_balance_refunded;
		private BigDecimal base_customer_balance_total_refunded;
		private BigDecimal customer_balance_total_refunded;
		private List<GiftCard> gift_cards;
		private BigDecimal base_gift_cards_amount;
		private BigDecimal gift_cards_amount;
		private BigDecimal base_gift_cards_invoiced;
		private BigDecimal gift_cards_invoiced;
		private BigDecimal base_gift_cards_refunded;
		private BigDecimal gift_cards_refunded;
		private GiftMessage gift_message;
		private String gw_id;
		private String gw_allow_gift_receipt;
		private String gw_add_card;
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
		private String gw_base_price_incl_tax;
		private String gw_price_incl_tax;
		private String gw_items_base_price_incl_tax;
		private String gw_items_price_incl_tax;
		private String gw_card_base_price_incl_tax;
		private String gw_card_price_incl_tax;
		private String gw_base_price_invoiced;
		private String gw_price_invoiced;
		private String gw_items_base_price_invoiced;
		private String gw_items_price_invoiced;
		private String gw_card_base_price_invoiced;
		private String gw_card_price_invoiced;
		private String gw_base_tax_amount_invoiced;
		private String gw_tax_amount_invoiced;
		private String gw_items_base_tax_invoiced;
		private String gw_items_tax_invoiced;
		private String gw_card_base_tax_invoiced;
		private String gw_card_tax_invoiced;
		private String gw_base_price_refunded;
		private String gw_price_refunded;
		private String gw_items_base_price_refunded;
		private String gw_items_price_refunded;
		private String gw_card_base_price_refunded;
		private String gw_card_price_refunded;
		private String gw_base_tax_amount_refunded;
		private String gw_tax_amount_refunded;
		private String gw_items_base_tax_refunded;
		private String gw_items_tax_refunded;
		private String gw_card_base_tax_refunded;
		private String gw_card_tax_refunded;
		private BigDecimal reward_points_balance;
		private BigDecimal reward_currency_amount;
		private BigDecimal base_reward_currency_amount;
		private AmazonOrderLink amazon_order_reference_id;

		@Getter
		@Setter
		@NoArgsConstructor
		public static class ShippingAssignment {
			private SalesDataShippingAssignment shipping;
			private List<OrderItem> items;
			private Integer stock_id;
			@JSONField(deserializeUsing = AttributeValueDeserializer.class)
			private List<MagentoAttribute<?>> extension_attributes;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class PaymentAdditionalInfo {
			private String key;
			private String value;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class CompanyOrderAttributes {
			private Integer order_id;
			private Integer company_id;
			private String company_name;
			@JSONField(deserializeUsing = AttributeValueDeserializer.class)
			private List<MagentoAttribute<?>> extension_attributes;
		}
	}

	public List<SalesDataItem> getSalesDataItems() {
		if (items == null || items.size() == 0) {
			return null;
		}

		return items.stream()
				.map(_item -> new SalesDataItem(_item.getItem_id(), _item.getQty_ordered()))
				.collect(Collectors.toList());
	}
}
