package com.github.chen0040.magento.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MagentoAttribute <T> {
	private String attribute_code;
	private T value;
}
