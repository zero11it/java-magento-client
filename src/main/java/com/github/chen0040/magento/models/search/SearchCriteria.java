package com.github.chen0040.magento.models.search;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
public class SearchCriteria {
	private Map<String, List<Filter>> filter_groups = new HashMap<>();
	private Integer current_page = null;
	private Integer page_size = null;
	private List<SortOrder> sort_orders = new ArrayList<>();
	
	public SearchCriteria addFilterGroup(String field, String value, ConditionType condition_type) {
		if (!filter_groups.containsKey(field)) {
			filter_groups.put(field, new ArrayList<>());
		}
		
		List<Filter> filter_group = filter_groups.get(field);
		filter_group.add(new Filter(value, condition_type));
		
		return this;
	}
	
	public SearchCriteria add_OR_FilterGroup(String field, String value, ConditionType condition_type) {
		return addFilterGroup(field, "%25" + value + "%25", condition_type);
	}
	
	public SearchCriteria add_AND_FilterGroup(String field, String value, ConditionType condition_type) {
		return addFilterGroup(field, value, condition_type);
	}
	
	public SearchCriteria setPage(Integer current_page, Integer page_size) {
		this.current_page = current_page;
		this.page_size = page_size;
		
		return this;
	}
	
	public SearchCriteria addSortOrder(String field, SortingDirection direction) {
		sort_orders.add(new SortOrder(field, direction));
		
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder args = new StringBuilder();
		
		args.append(getFilterGroupArgs());
		
		if (current_page != null) {
			args.append("searchCriteria[currentPage]=");
			args.append(current_page);
			args.append("&");
		}
		
		if (page_size != null) {
			args.append("searchCriteria[pageSize]=");
			args.append(page_size);
			args.append("&");
		}
		
		args.append(getSortOrderArgs());
		
		String ret = args.toString();
		
		if (ret.endsWith("&")) {
			ret = ret.substring(0, ret.length()-1);
		}
		
		return args.toString();
	}

	private String getFilterGroupArgs() {
		if (filter_groups.size() == 0) {
			return "";
		}
		
		StringBuilder args = new StringBuilder();
		
		Set<String> fields = filter_groups.keySet();
		int curField = 0;
		
		for (String field : fields) {
			List<Filter> filters = filter_groups.get(field);
			
			for (int curFilter = 0; curFilter < filters.size(); curFilter++) {
				Filter filter = filters.get(curFilter);
				
				args.append(
						String.format("searchCriteria[filter_groups][%d][filters][%d][field]=", curField, curFilter)
				);
				args.append(field);
				args.append("&");
				args.append(
						String.format("searchCriteria[filter_groups][%d][filters][%d][value]=", curField, curFilter)
				);
				args.append(filter.getValue());
				args.append("&");
				args.append(
						String.format("searchCriteria[filter_groups][%d][filters][%d][condition_type]=", curField, curFilter)
				);
				args.append(filter.getCondition_type());
				args.append("&");
			}
		}
		
		return args.toString();
	}

	private String getSortOrderArgs() {
		if (sort_orders.size() == 0) {
			return "";
		}
		
		StringBuilder args = new StringBuilder();
		
		for (int curSortOrder = 0; curSortOrder < sort_orders.size(); curSortOrder++) {
			SortOrder sort_order = sort_orders.get(curSortOrder);
			
			args.append(
					String.format("searchCriteria[sortOrders][%d][field]=", curSortOrder)
			);
			args.append(sort_order.getField());
			args.append("&");
			args.append(
					String.format("searchCriteria[sortOrders][%d][direction]=", curSortOrder)
			);
			args.append(sort_order.getDirection());
			args.append("&");
		}
		
		return args.toString();
	}
}
