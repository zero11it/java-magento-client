package com.github.chen0040.magento.models.category;

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
}
