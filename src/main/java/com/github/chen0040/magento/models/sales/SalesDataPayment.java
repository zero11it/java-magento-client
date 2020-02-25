package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataPayment {
	private String account_status;
	private String additional_data;
	private List<String> additional_information;;
	private String address_status;
	private BigDecimal amount_authorized;
	private BigDecimal amount_canceled;
	private BigDecimal amount_ordered;
	private BigDecimal amount_paid;
	private BigDecimal amount_refunded;
	private String anet_trans_method;
	private BigDecimal base_amount_authorized;
	private BigDecimal base_amount_canceled;
	private BigDecimal base_amount_ordered;
	private BigDecimal base_amount_paid;
	private BigDecimal base_amount_paid_online;
	private BigDecimal base_amount_refunded;
	private BigDecimal base_amount_refunded_online;
	private BigDecimal base_shipping_amount;
	private BigDecimal base_shipping_captured;
	private BigDecimal base_shipping_refunded;
	private String cc_approval;
	private String cc_avs_status;
	private String cc_cid_status;
	private String cc_debug_request_body;
	private String cc_debug_response_body;
	private String cc_debug_response_serialized;
	private String cc_exp_month;
	private String cc_exp_year;
	private String cc_last4;
	private String cc_number_enc;
	private String cc_owner;
	private String cc_secure_verify;
	private String cc_ss_issue;
	private String cc_ss_start_month;
	private String cc_ss_start_year;
	private String cc_status;
	private String cc_status_description;
	private String cc_trans_id;
	private String cc_type;
	private String echeck_account_name;
	private String echeck_account_type;
	private String echeck_bank_name;
	private String echeck_routing_number;
	private String echeck_type;
	private Integer entity_id;
	private String last_trans_id;
	private String method;
	private Integer parent_id;
	private String po_number;
	private String protection_eligibility;
	private Integer quote_payment_id;
	private BigDecimal shipping_amount;
	private BigDecimal shipping_captured;
	private BigDecimal shipping_refunded;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes;
}
