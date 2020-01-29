package com.github.chen0040.magento.models.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMedia {
	private Integer id;
	private String media_type;
	private String label;
	private Integer position;
	private Boolean disabled;
	private List<String> types;
	private String file;
}
