package com.github.chen0040.magento.models.shipment;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentTrack {
	private Integer order_id;
	private Date created_at;
	private Integer entity_id;
	private Integer parent_id;
	private Date updated_at;
	private Double weight;
	private Integer qty;
	private String description;
	private String track_number;
	private String title;
	private String carrier_code;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
}
