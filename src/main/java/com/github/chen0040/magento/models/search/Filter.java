package com.github.chen0040.magento.models.search;

import lombok.Getter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
public class Filter {
	private String value;
	private String condition_type;
	
	public Filter(String value, ConditionType condition_type) {
		this.value = value;
		this.condition_type = condition_type.toString();
	}
}
