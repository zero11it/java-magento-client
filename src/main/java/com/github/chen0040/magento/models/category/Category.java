package com.github.chen0040.magento.models.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.MagentoAttribute;

/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	private Integer id;
	private Integer parent_id;
	private String name;
	private Boolean is_active;
	private Integer position;
	private Integer level;
	private String children;
	private Date created_at;
	private Date updated_at;
	private String path;
	List<String> available_sort_by;
	private Boolean include_in_menu;
	List<MagentoAttribute<String>> custom_attributes;
	
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
}
