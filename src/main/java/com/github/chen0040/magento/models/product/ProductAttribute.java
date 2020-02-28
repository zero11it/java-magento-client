package com.github.chen0040.magento.models.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductAttribute {
	private Boolean is_wysiwyg_enabled;
	private Boolean is_html_allowed_on_front;
	private Boolean used_for_sort_by;
	private Boolean is_filterable;
	private Boolean is_filterable_in_search;
	private Boolean is_used_in_grid;
	private Boolean is_visible_in_grid;
	private Boolean is_filterable_in_grid;
	private Integer position;
	private List<String> apply_to;
	private String is_searchable;
	private String is_visible_in_advanced_search;
	private String is_comparable;
	private String is_used_for_promo_rules;
	private String is_visible_on_front;
	private String used_in_product_listing;
	private Boolean is_visible;
	private String scope;
	private Integer attribute_id;
	private String attribute_code;
	private String frontend_input;
	private String entity_type_id;
	private Boolean is_required;
	private List<ProductAttributeOption> options;
	private Boolean is_user_defined;
	private String default_frontend_label;
	private List<FrontendLabel> frontend_labels;
	private String backend_type;
	private String is_unique;
	private String frontend_class;
	private List<String> validation_rules;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FrontendLabel {
		String label;
		Integer store_id;
	}
	
	public boolean hasOptions() {
		return (options != null && options.size() > 0)
				|| (frontend_input != null && frontend_input.equals("select"))
				|| (is_visible_in_grid != null && is_visible_in_grid == true);
	}
}
