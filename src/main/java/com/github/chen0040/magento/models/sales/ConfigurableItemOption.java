package com.github.chen0040.magento.models.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class ConfigurableItemOption {
	private String option_id;
	private Double option_value;
	private Map<String, Object> extension_attributes;
}
