package com.github.chen0040.magento.models.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.cart.TierPrices;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Product {
	private Integer id;
	private String sku;
	private String name;
	private Integer attribute_set_id;
	private BigDecimal price = new BigDecimal(0);
	private Integer status;
	private Integer visibility;
	private String type_id;
	private Date created_at;
	private Date updated_at;
	private Double weight;
	private ExtensionAttributes extension_attributes;
	private List<String> product_links;
	private List<TierPrices> tier_prices;

	@JSONField(deserializeUsing = AttributeValueDeserializer.class)
	private List<MagentoAttribute<?>> custom_attributes;
	
	public static final class VISIBILITY {
		public static final Integer NOT_VISIBLE = 1;
		public static final Integer IN_CATALOG = 2;
		public static final Integer IN_SEARCH = 3;
		public static final Integer BOTH = 4;
	}
	
	public static final class STATUS {
		public static final Integer DISABLED = 2;
		public static final Integer ENABLED = 1;
	}
	
	public static final class TYPE {
		public static final String SIMPLE = "simple";
		public static final String CONFIGURABLE = "configurable";
		public static final String VIRTUAL = "virtual";
		public static final String BUNDLE = "bundle";
		public static final String DOWNLOADABLE = "downloadable";
	}
	
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ExtensionAttributes {
		private List<CategoryLink> category_links;
		private StockItem stock_item;
		private Integer categoryPosition;
		
		
		public ExtensionAttributes addCategoryLink(String category_id) {
			if (category_links == null) {
				category_links = new ArrayList<CategoryLink>();
			}
			
			category_links.add(new CategoryLink(categoryPosition, category_id));
			categoryPosition += 1;
			
			return this;
		}
		
		public ExtensionAttributes setStock(Integer amount) {
			this.stock_item = new StockItem(amount, amount > 0);
			
			return this;
		}
		
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		private static class CategoryLink {
			Integer position;
			String category_id;
		}
		
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		private static class StockItem {
			Integer qty;
			Boolean is_in_stock;
		}
	}

}
