package com.github.chen0040.magento.models.product;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeSet {
	@JSONField(serialize = false)
	 private long attribute_set_id;
	 private String attribute_set_name;
	 private long sort_order;
	@JSONField(serialize = false)
	 private long entity_type_id;
	 
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute> extension_attributes;
}
