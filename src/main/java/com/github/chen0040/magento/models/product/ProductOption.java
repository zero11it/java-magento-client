package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductOption {
	private String product_sku;
	private Integer option_id;
	private String title;
	private String type;
	private Integer sort_order;
	private Boolean is_require;
	private BigDecimal price;
	private String price_type;
	private String sku;
	private String file_extension;
	private Integer max_characters;
	private Integer image_size_x;
	private Integer image_size_y;
	private List<ProductOptionValue> values;
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private ExtensionAttributes extension_attributes;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ProductOptionValue {
		private String title;
		private Integer sort_order;
		private BigDecimal price;
		private String price_type;
		private String sku;
		private Integer option_type_id;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		private String vertex_flex_field;
	}
}
