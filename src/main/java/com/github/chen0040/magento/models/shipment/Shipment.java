package com.github.chen0040.magento.models.shipment;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Shipment {
	private Integer billing_address_id;
	private Date created_at;
	private Integer customer_id;
	private Integer email_sent;
	private Integer entity_id;
	private String increment_id;
	private Integer order_id;
	List<ShipmentPackage> packages;
	private Integer shipment_status;
	private Integer shipping_address_id;
	private String shipping_label;
	private Integer store_id;
	private Integer total_qty;
	private Double total_weight;
	private Date updated_at;
	List<ShipmentItem> items;
	List<ShipmentTrack> tracks;
	List<ShipmentComment> comments;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
}
