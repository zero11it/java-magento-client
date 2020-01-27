package com.github.chen0040.magento.models.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@AllArgsConstructor
public class Filter {
	private String value;
	private ConditionType condition_type;
}
