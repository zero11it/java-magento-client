package com.github.chen0040.magento.models.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class ProductAttribute {
	private boolean is_wysiwyg_enabled;
	private boolean is_html_allowed_on_front;
	private boolean used_for_sort_by;
	private boolean is_filterable;
	private boolean is_filterable_in_search;
	private boolean is_used_in_grid;
	private boolean is_visible_in_grid;
	private boolean is_filterable_in_grid;
	private long position;
	private List<String> apply_to;
	private String is_searchable;
	private String is_visible_in_advanced_search;
	private String is_comparable;
	private String is_used_for_promo_rules;
	private String is_visible_on_front;
	private String used_in_product_listing;
	private boolean is_visible;
	private String scope;
	private long attribute_id;
	private String attribute_code;
	private String frontend_input;
	private String entity_type_id;
	private boolean is_requiredt;
	private List<ProductAttributeOption> options;
	private boolean is_user_defined;
	private String default_frontend_label;
	private String frontend_labels;
	private String backend_type;
	private String is_unique;
	private String frontend_class;
	private List<String> validation_rules;
}
