package com.github.chen0040.magento.models.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SortOrder {
	private String field;
	private SortingDirection direction;
}
