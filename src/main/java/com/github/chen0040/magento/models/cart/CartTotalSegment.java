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
public class CartTotalSegment {

	private String code;
	private String title;
	private double value;
	private String area;
	private CartTotalSegmentExtensionAttributes extension_attributes;

}
