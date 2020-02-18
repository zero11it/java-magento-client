package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 10/7/2017.
 */
@Setter
@Getter
public class MagentoError {
	private String message;
	private List<String> parameters;
}
