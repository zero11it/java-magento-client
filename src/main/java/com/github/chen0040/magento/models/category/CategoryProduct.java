package com.github.chen0040.magento.models.category;

import java.util.List;

import com.github.chen0040.magento.models.MagentoAttribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryProduct {
	private String sku;
	private Integer position;
	private Integer category_id;
	private List<MagentoAttribute<String>> extension_attributes;
}
