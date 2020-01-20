package com.github.chen0040.magento.models.shipment;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentComment {
	private long is_customer_notified;
	private long parent_id;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
	
	private String comment;
	private long is_visible_on_front;
	private String created_at;
	private long entity_id;
	
	public boolean is_customer_notified() {
		return is_customer_notified > 0;
	}
	public void set_customer_notified(boolean val) {
		is_customer_notified = (val) ? 1 : 0;
	}
	
	public boolean is_visible_on_front() {
		return is_visible_on_front > 0;
	}
	public void set_visible_on_front(boolean val) {
		is_visible_on_front = (val) ? 1 : 0;
	}
}
