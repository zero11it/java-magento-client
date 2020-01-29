package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
public class MagentoAttribute {
	private String attribute_code;
	private Object value;

	public MagentoAttribute() {

	}

	public MagentoAttribute(String attribute_code, Object value) {
		this.attribute_code = attribute_code;
		this.value = value;
	}
	
	public static List<MagentoAttribute> buildList(MagentoAttribute... attributes) {
		List<MagentoAttribute> list = new ArrayList<MagentoAttribute>();
		
		for (MagentoAttribute attribute : attributes) {
			list.add(attribute);
		}
		
		return list;
	}
}
