package com.github.chen0040.magento.models.product;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeOption {
	private String label;
	private String value;
	private long sort_order;
	private boolean is_default;
	List<ProductAttributeOptionStoreLabel> store_labels;
}
