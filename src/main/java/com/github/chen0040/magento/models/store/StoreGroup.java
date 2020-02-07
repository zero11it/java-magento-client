package com.github.chen0040.magento.models.store;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreGroup {
	    private Integer id;
	    private Integer website_id;
	    private Integer root_category_id;
	    private Integer default_store_id;
	    private String name;
	    private String code;
	    
	    @JSONField(deserializeUsing = AttributeValueDeserializer.class)
	    private List<MagentoAttribute<?>> extension_attributes;
}
