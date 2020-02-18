package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartItemProductCustomOption {
	private String option_id;
	private String option_value;
	private CartItemProductCustomOptionExtensionAttributes extension_attributes;

}
