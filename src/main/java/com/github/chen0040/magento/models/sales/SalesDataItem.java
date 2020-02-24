package com.github.chen0040.magento.models.sales;

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
public class SalesDataItem {
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes;
	
	private Integer order_item_id;
	private Integer qty;
	
	public SalesDataItem(Integer order_item_id, Integer qty) {
		this.order_item_id = order_item_id;
		this.qty = qty;
	}
}
