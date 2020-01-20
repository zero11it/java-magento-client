package com.github.chen0040.magento.models.shipment;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.AttributeValueDeserializer;
import com.github.chen0040.magento.models.MagentoAttribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentComment {
	private boolean is_customer_notified;
	private long parent_id;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
	
	private String comment;
	private boolean is_visible_on_front;
	private String created_at;
	private long entity_id;
}
