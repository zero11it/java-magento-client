package com.github.chen0040.magento.models.shipment;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentPackage {
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute> extension_attributes;
}
