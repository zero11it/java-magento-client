package com.github.chen0040.magento.models.sales;

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
public class SalesDataAddress {
	private String address_type;
	private String city;
	private String company;
	private String country_id;
	private Integer customer_address_id;
	private Integer customer_id;
	private String email;
	private Integer entity_id;
	private String fax;
	private String firstname;
	private String lastname;
	private String middlename;
	private Integer parent_id;
	private String postcode;
	private String prefix;
	private String region;
	private String region_code;
	private Integer region_id;
	private List<String> street;
	private String suffix;
	private String telephone;
	private String vat_id;
	private Integer vat_is_valid;
	private String vat_request_date;
	private String vat_request_id;
	private Integer vat_request_success;
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes;
	
	public String getFullStreetName() {
		return String.join("\n", street);
	}
	
	public String getFullAddress() {
		StringBuilder address = new StringBuilder();
		
		address
			.append(firstname).append(" ").append(lastname).append("\n")
			.append(getFullStreetName()).append("\n")
			.append(region).append(", ").append(region_code).append("\n")
			.append(postcode).append(" ").append(city).append("\n")
			.append(region).append(" ").append(region_code).append("\n")
			.append(country_id);
		
		return address.toString();
	}
}
