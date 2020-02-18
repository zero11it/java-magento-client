package com.github.chen0040.magento.models.shipment;

import java.math.BigDecimal;
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
public class ShipmentItem {
	private String additional_data;
	private String description;
	private Integer entity_id;
	private String name;
	private Integer parent_id;
	private BigDecimal price;
	private Integer product_id;
	private BigDecimal row_total;
	private String sku;
	private Double weight;
	private Integer order_item_id;
	private Integer qty;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	List<MagentoAttribute<?>> extension_attributes;
}
