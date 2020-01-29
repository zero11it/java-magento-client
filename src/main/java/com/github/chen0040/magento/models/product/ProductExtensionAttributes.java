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
	List<CategoryLink> category_links = new ArrayList<ProductExtensionAttributes.CategoryLink>();
	StockItem stock_item;
	
	public ProductExtensionAttributes addCategoryLink(Integer position, String category_id) {
		this.category_links.add(new CategoryLink(position, category_id));
		
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
