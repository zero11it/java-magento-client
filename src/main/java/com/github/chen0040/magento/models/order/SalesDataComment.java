package com.github.chen0040.magento.models.order;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDataComment {
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
	
	private String comment;
	private long is_visible_on_front;
}
