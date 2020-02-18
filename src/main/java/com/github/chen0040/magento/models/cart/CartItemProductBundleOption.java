package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by xschen on 11/7/2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class CartItemProductBundleOption {
	private Integer option_id = 0;
	private Integer option_qty = 0;
	private List<Integer> option_selections;
	private Map<String, Object> extension_attributes;
}
