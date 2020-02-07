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
public class Website {
	    private Integer id;
	    private String code;
	    private String name;
	    private Integer default_group_id;
	    
	    @JSONField(deserializeUsing = AttributeValueDeserializer.class)
	    private List<MagentoAttribute<?>> extension_attributes;
}
