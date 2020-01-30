package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.TierPrices;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {
	public static final class VISIBILITY {
		public static final Integer NOT_VISIBLE = 1;
		public static final Integer IN_CATALOG = 2;
		public static final Integer IN_SEARCH = 3;
		public static final Integer BOTH = 4;
	}
	
	public static final class STATUS {
		public static final Integer DISABLED = 2;
		public static final Integer ENABLED = 1;
	}
	
	public static final class TYPE {
		public static final String SIMPLE = "simple";
		public static final String CONFIGURABLE = "configurable";
		public static final String VIRTUAL = "virtual";
		public static final String BUNDLE = "bundle";
		public static final String DOWNLOADABLE = "downloadable";
	}

	private Integer id;
	@NonNull private String sku;
	private String name;
	private Integer attribute_set_id;
	private BigDecimal price = new BigDecimal(0);
	private Integer status;
	private Integer visibility;
	private String type_id;
	private String created_at;
	private String updated_at;
	private Double weight;
	private ProductExtensionAttributes extension_attributes;
	private List<String> product_links;
	private List<TierPrices> tier_prices;

	@SuppressWarnings("rawtypes")
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute> custom_attributes;

}
