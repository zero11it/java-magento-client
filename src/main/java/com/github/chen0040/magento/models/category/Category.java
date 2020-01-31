package com.github.chen0040.magento.models.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private Integer product_count;
	private List<Category> children_data;
	@SuppressWarnings("rawtypes")
	private List<MagentoAttribute> custom_attributes;
}
