package com.github.chen0040.magento.models.search;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
public class Filter {
	@Setter(AccessLevel.PUBLIC) private int index;
	
	private String field;
	private String value;
	private String condition_type;
	
	public Filter(String field, String value, ConditionType condition_type) {
		this.field = field;
		this.value = value;
		this.condition_type = condition_type.toString();
	}
}
