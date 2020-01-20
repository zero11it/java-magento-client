package com.github.chen0040.magento.models.store;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreGroup {
	    private long id;
	    private long website_id;
	    private long root_category_id;
	    private long default_store_id;
	    private String name;
	    private String code;
	    
	    @JSONField(deserializeUsing = AttributeValueDeserializer.class)
	    private List<MagentoAttribute> extension_attributes;
}
