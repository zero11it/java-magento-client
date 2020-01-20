package com.github.chen0040.magento.models.shipment;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.AttributeValueDeserializer;
import com.github.chen0040.magento.models.MagentoAttribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentTrack {
	private long order_id;
	private String created_at;
	private long entity_id;
	private long parent_id;
	private String updated_at;
	private double weight;
	private long qty;
	private String description;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
	
	private String track_number;
	private String title;
	private String carrier_code;
}
