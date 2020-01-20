package com.github.chen0040.magento.models.order;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusHistory {
	private String comment;
	private String created_at;
	private long entity_id;
	private String entity_name;
	private long is_customer_notified;
	private long is_visible_on_front;
	private long parent_id;
	private String status;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
}
