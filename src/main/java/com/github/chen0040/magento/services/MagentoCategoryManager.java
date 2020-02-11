package com.github.chen0040.magento.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.category.Category;
import com.github.chen0040.magento.models.category.CategoryProduct;
import com.github.chen0040.magento.models.category.ProductLink;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoCategoryManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoCategoryManager.class);
	private MagentoClient client;
	private static final String relativePath4Categories = "rest/V1/categories";

	public MagentoCategoryManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}

	public Category addCategory(Category category) {
		String uri = baseUri() + "/" + relativePath4Categories;
		String body = RESTUtils.payloadWrapper("category", category);
		
		String json;
		if (hasCategory(category)) {
			uri += "/" + getCategories().stream()
					.filter(cat -> cat.getName().equals(category.getName()))
					.collect(Collectors.toList())
					.get(0).getId();
			json = putSecure(uri, body, logger);
		}
		else {
			json = postSecure(uri, body, logger);
		}

		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Category.class);
	}

	public boolean hasCategory(Category category) {
		List<Category> categories = getCategories();
		
		return categories.stream().anyMatch(_category -> _category.getName().equals(category.getName()))
				|| categories.stream().anyMatch(_category -> _category.getId().equals(category.getId()));
	}

	public boolean categoryHasProduct(Integer categoryId, String productSku) {
		return getProductsInCategory(categoryId).stream().anyMatch(product -> product.getCategory_id() == categoryId);
	}

	public List<Category> searchCategories(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Categories + "/list?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return RESTUtils.getArrayByKey(json, "items", Category.class);
	}
	
	public List<Category> getCategories() {
		String uri = baseUri() + "/" + relativePath4Categories + "/list?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return RESTUtils.getArrayByKey(json, "items", Category.class);
	}

	public Category getCategory(Integer categoryId) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Category.class);
	}
	
	public Category getCategory(String categoryName) {
		List<Category> categories = getCategories();
		Optional<Category> category = categories.stream()
				.filter(_category -> _category.getName().equals(categoryName))
				.findAny();
		
		if (category.isPresent()) {
			return category.get();
		}
		
		return null;
	}

	public Category getCategoryWithChildren(Integer categoryId) {
		String uri = baseUri() + "/" + relativePath4Categories + "?rootCategoryId=" + categoryId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Category.class);
	}

	public List<CategoryProduct> getProductsInCategory(Integer categoryId) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products";
		
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, CategoryProduct.class);
	}

	public Boolean addProductToCategory(Integer categoryId, String productSku) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products";
		String body = RESTUtils.payloadWrapper("productLink", new ProductLink()
				.setCategory_id(categoryId.toString())
				.setSku(productSku)
		);
		
		String json;
		if (categoryHasProduct(categoryId, productSku)) {
			json = putSecure(uri, body, logger);
		}
		else {
			json = postSecure(uri, body, logger);
		}
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}

	public Boolean removeProductFromCategory(Integer categoryId, String productSku) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products/" + productSku;
		
		String json = deleteSecure(uri, logger);

		return JSON.parseObject(json, Boolean.class);
	}

	public Boolean deleteCategory(Integer categoryId) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId;
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public Boolean moveCategory(Integer categoryId, Integer fromId, Integer toId) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/move";
		String body = JSON.toJSONString(Stream.of(new Object[][] {
			{"parentId", fromId},
			{"afterId", toId}
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1])));
		
		String json = putSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
	}

	@Override
	public boolean oauthEnabled() {
		return client.oauthEnabled();
	}

	@Override
	public OAuthConfig oAuth() {
		return client.oAuth();
	}
}
