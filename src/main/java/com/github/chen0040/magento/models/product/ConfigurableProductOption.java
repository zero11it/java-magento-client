package com.github.chen0040.magento.models.product;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurableProductOption {
	 private Integer id;
	 private String attribute_id;
	 private String label;
	 private Integer position;
	 private Boolean is_use_default;
	 List<Value> values;
	 //extension_attributes
	 private Integer product_id;
	 
	 @Getter
	 @Setter
	 @NoArgsConstructor
	 @AllArgsConstructor
	 private class Value {
		 Integer value_index;
	 }
	 
	 public ConfigurableProductOption addValue(Integer value_index) {
		 if (this.values == null) {
			 this.values = new ArrayList<ConfigurableProductOption.Value>();
		 }
		 
		 this.values.add(new Value(value_index));
		 
		 return this;
	 }
}
