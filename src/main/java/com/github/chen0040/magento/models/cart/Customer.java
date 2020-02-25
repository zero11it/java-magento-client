package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

import com.github.chen0040.magento.models.sales.CustomerAddress;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Customer {
	private String email;
	private String firstname;
	private String lastname;
	private CustomerAddress billing_address;
	private Integer orig_order_id;
	private Currency currency;
	private Boolean customer_is_guest;
	private Boolean customer_note_notify;
	private Integer customer_tax_class_id;
	private Integer store_id;
	private Map<String, Object> extension_attributes;
}
