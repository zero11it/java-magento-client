package com.github.chen0040.magento.models.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class Category {
	private Integer id;
	private Integer parent_id;
	private String name;
	private Boolean is_active;
	private Integer position;
	private Integer level;
	private String children;
	private List<Category> children_data;
	private Date created_at;
	private Date updated_at;
	private String path;
	private List<String> available_sort_by;
	private Boolean include_in_menu;
	private List<MagentoAttribute<String>> custom_attributes;
	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> extension_attributes;
	
	public Category addChild(String categoryId) {
		if (children == null) {
			children = categoryId;
		}
		else {
			children += ", " + categoryId;
		}
		
		return this;
	}
	
	public Category addChild(Category category) {
		return addChild(category.getId().toString());
	}
	
	public MagentoAttribute<String> getCustomAttribute(String attribute_code) {
		if (custom_attributes == null || custom_attributes.size() == 0) {
			return null;
		}
		
		Optional<MagentoAttribute<String>> attribute = custom_attributes.stream()
				.filter(_attribute -> _attribute.getAttribute_code().equals(attribute_code))
				.findAny();
		
		if (attribute.isPresent()) {
			return attribute.get();
		}
		
		return null;
	}

	public Category addCustomAttribute(String attribute_code, String value) {
		MagentoAttribute<String> currentAttribute = getCustomAttribute(attribute_code);
		
		if (currentAttribute == null) {
			currentAttribute = new MagentoAttribute<String>(attribute_code, value);
		}
		else {
			currentAttribute.setValue(value);
		}
		
		if (custom_attributes == null) {
			custom_attributes = new ArrayList<MagentoAttribute<String>>();
		}
		
		custom_attributes.add(currentAttribute);
		
		return this;
	}
}
