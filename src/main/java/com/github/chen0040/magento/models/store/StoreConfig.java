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
public class StoreConfig {
	private Integer id;
	private String code;
	private Integer website_id;
	private String locale;
	private String base_currency_code;
	private String default_display_currency_code;
	private String timezone;
	private String weight_unit;
	private String base_url;
	private String base_link_url;
	private String base_static_url;
	private String base_media_url;
	private String secure_base_url;
	private String secure_base_link_url;
	private String secure_base_static_url;
	private String secure_base_media_url;
    
    @JSONField(deserializeUsing = AttributeValueDeserializer.class)
    private List<MagentoAttribute<?>> extension_attributes;
}
