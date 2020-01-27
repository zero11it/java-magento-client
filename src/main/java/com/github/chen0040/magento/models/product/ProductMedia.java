package com.github.chen0040.magento.models.product;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
public class ProductMedia {
	private long id;
	private String media_type;
	private String label;
	private int position;
	private boolean disabled;
	private List<String> types;
	private String file;
}
