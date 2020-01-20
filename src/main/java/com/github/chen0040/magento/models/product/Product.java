package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.TierPrices;
import com.github.chen0040.magento.models.serialization.ProductAttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class Product {

	public static final int VISIBILITY_NOT_VISIBLE = 1;
	public static final int VISIBILITY_IN_CATALOG = 2;
	public static final int VISIBILITY_IN_SEARCH = 3;
	public static final int VISIBILITY_BOTH = 4;

	public static final int STATUS_DISABLED = 2;
	public static final int STATUS_ENABLED = 1;

	private long id = 1;
	private String sku;
	private String name;
	private long attribute_set_id;
	private BigDecimal price = new BigDecimal(0);
	private int status;
	private int visibility;
	private String type_id;
	private String created_at;
	private String updated_at;
	private double weight;

	@JSONField(deserializeUsing = ProductAttributeValueDeserializer.class)
	private List<MagentoAttribute> extension_attributes = new ArrayList<>();
	private List<String> product_links = new ArrayList<>();
	private List<TierPrices> tier_prices = new ArrayList<>();

	@JSONField(deserializeUsing = ProductAttributeValueDeserializer.class)
	private List<MagentoAttribute> custom_attributes = new ArrayList<>();

}
