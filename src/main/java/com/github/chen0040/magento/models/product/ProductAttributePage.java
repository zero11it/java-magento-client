package com.github.chen0040.magento.models.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.github.chen0040.magento.models.search.SearchCriteria;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributePage {
	private List<ProductAttribute> items = new ArrayList<>();
	private Integer total_count = 1000;
	private SearchCriteria search_criteria = new SearchCriteria();
}
