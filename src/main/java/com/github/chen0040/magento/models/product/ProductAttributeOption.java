package com.github.chen0040.magento.models.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeOption {
	private String label;
	private String value;
	private Integer sort_order;
	private Boolean is_default;
	private List<StoreLabel> store_labels;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StoreLabel {
		 private Integer store_id;
		 private String label;
	}
}
