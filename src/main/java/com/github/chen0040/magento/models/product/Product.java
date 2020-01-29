package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	public static final Integer VISIBILITY_NOT_VISIBLE = 1;
	public static final Integer VISIBILITY_IN_CATALOG = 2;
	public static final Integer VISIBILITY_IN_SEARCH = 3;
	public static final Integer VISIBILITY_BOTH = 4;

	public static final Integer STATUS_DISABLED = 2;
	public static final Integer STATUS_ENABLED = 1;
	
	public static final String TYPE_SIMPLE = "simple";
	public static final String TYPE_CONFIGURABLE = "configurable";

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

	@SuppressWarnings("rawtypes")
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute> extension_attributes;
	private List<String> product_links;
	private List<TierPrices> tier_prices;

	@SuppressWarnings("rawtypes")
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute> custom_attributes;

}
