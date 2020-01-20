package com.github.chen0040.magento.models.store;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreView {
	    private long id;
	    private String code;
	    private String name;
	    private long website_id;
	    private long store_group_id;
	    private boolean is_active;
	    
	    @JSONField(deserializeUsing = AttributeValueDeserializer.class)
	    private List<MagentoAttribute> extension_attributes;
}
