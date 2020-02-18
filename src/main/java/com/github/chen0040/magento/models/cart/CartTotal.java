package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartTotal {
	private Double grand_total;
	private Double base_grand_total;
	private Double subtotal;
	private Double base_subtotal;
	private Double discount_amount;
	private Double base_discount_amount;
	private Double subtotal_with_discount;
	private Double base_subtotal_with_discount;
	private Double shipping_amount;
	private Double base_shipping_amount;
	private Double shipping_discount_amount;
	private Double base_shipping_discount_amount;
	private Double tax_amount;
	private Double base_tax_amount;
	private Double weee_tax_applied_amount;
	private Double shipping_tax_amount;
	private Double base_shipping_tax_amount;
	private Double subtotal_incl_tax;
	private Double base_subtotal_incl_tax;
	private Double shipping_incl_tax;
	private Double base_shipping_incl_tax;
	private String base_currency_code;
	private String quote_currency_code;
	private String coupon_code;
	private Integer items_qty;
	private List<CartTotalItem> items;
	private List<CartTotalSegment> total_segments;
	private CartTotalExtensionAttributes extension_attributes;
}
