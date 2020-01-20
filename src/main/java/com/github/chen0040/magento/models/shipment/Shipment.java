package com.github.chen0040.magento.models.shipment;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.AttributeValueDeserializer;
import com.github.chen0040.magento.models.MagentoAttribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shipment {
	private long billing_address_id;
	private String created_at;
	private long customer_id;
	private boolean email_sent;
	private long entity_id;
	private String increment_id;
	private long order_id;
	List<ShipmentPackage> packages;
	private long shipment_status;
	private long shipping_address_id;
	private String shipping_label;
	private long store_id;
	private long total_qty;
	private double total_weight;
	private String updated_at;
	List<ShipmentItem> items;
	List<ShipmentTrack> tracks;
	List<ShipmentComment> comments;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute> extension_attributes;
}