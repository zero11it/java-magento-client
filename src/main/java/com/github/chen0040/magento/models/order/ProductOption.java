package com.github.chen0040.magento.models.order;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

public class ProductOption {
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
}
