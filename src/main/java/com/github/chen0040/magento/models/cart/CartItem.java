package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by xschen on 10/7/2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class CartItem {
	private Integer item_id;
	private String sku;
	private Integer qty;
	private String name;
	private Double price;
	private String product_type;
	private String quote_id;
	private CartItemProductOption product_option;
	private Map<String, Object> extension_attributes;
}
