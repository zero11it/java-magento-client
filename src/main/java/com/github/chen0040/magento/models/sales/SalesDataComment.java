package com.github.chen0040.magento.models.sales;

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
public class SalesDataComment {
	 private String comment;
	 private Date created_at;
	 private Integer entity_id;
	 private String entity_name;
	 private Integer is_customer_notified;
	 private Integer is_visible_on_front;
	 private Integer parent_id;
	 private String status;
	
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes;
}
