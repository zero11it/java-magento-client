package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.sales.AmazonOrderLink;
import com.github.chen0040.magento.models.sales.SalesDataAddress;
import com.github.chen0040.magento.models.sales.SalesDataShippingAssignment;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Cart {
	private Integer id;
	private Date created_at;
	private Date updated_at;
	private Date converted_at;
	private Boolean is_active;
	private Boolean is_virtual;
	private List<CartItem> items;
	private Integer items_count;
	private Integer items_qty;
	private Customer customer;
	private SalesDataAddress billing_address;
	private String reserved_order_id;
	private Integer orig_order_id;
	private Currency CurrencyObject;
	private Boolean customer_is_guest;
	private String customer_note;
	private Boolean customer_note_notify;
	private Integer customer_tax_class_id;
	private Integer store_id;
	private ExtensionAttributes extension_attributes;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		AmazonOrderLink amazon_order_reference_id;
		List<SalesDataShippingAssignment> shipping_assignments;
		NegotiableQuote negotiable_quote;
	}
}
