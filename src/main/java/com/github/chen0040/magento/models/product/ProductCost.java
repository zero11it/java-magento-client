package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ProductCost {
	private BigDecimal cost;
	private Integer store_id;
	private String sku;
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes = new ArrayList<>();
}
