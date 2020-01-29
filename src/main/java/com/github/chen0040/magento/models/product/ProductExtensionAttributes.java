package com.github.chen0040.magento.models.product;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductExtensionAttributes {
	private List<CategoryLink> category_links;
	private StockItem stock_item;
	private int categoryPosition = 0;
	
	
	public ProductExtensionAttributes addCategoryLink(String category_id) {
		if (category_links == null) {
			category_links = new ArrayList<CategoryLink>();
		}
		
		category_links.add(new CategoryLink(categoryPosition, category_id));
		categoryPosition += 1;
		
		return this;
	}
	
	public ProductExtensionAttributes setStock(Integer amount) {
		this.stock_item = new StockItem(amount, amount > 0);
		
		return this;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	private class CategoryLink {
		Integer position;
		String category_id;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	private class StockItem {
		Integer qty;
		Boolean is_in_stock;
	}
}
