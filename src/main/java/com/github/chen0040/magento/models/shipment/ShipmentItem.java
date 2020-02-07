package com.github.chen0040.magento.models.shipment;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentItem {
	private String additional_data;
	private String description;
	private long entity_id;
	private String name;
	private long parent_id;
	private BigDecimal price;
	private long product_id;
	private BigDecimal row_total;
	private String sku;
	private double weight;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
	
	private long order_item_id;
	private long qty;
}
