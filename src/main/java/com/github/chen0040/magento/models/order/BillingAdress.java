package com.github.chen0040.magento.models.order;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingAdress {
	private String address_type;
	private String city;
	private String company;
	private String country_id;
	private long customer_address_id;
	private long customer_id;
	private String email;
	private long entity_id;
	private String fax;
	private String firstname;
	private String lastname;
	private String middlename;
	private long parent_id;
	private String postcode;
	private String prefix;
	private String region;
	private String region_code;
	private long region_id;
	List<String> street;
	private String suffix;
	private String telephone;
	private String vat_id;
	private long vat_is_valid;
	private String vat_request_date;
	private String vat_request_id;
	private long vat_request_success;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
}
