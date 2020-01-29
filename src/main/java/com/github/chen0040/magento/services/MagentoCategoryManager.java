package com.github.chen0040.magento.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.category.Category;
import com.github.chen0040.magento.models.category.CategoryProduct;
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

	public boolean deleteCategory(long categoryId) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId;
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return json.equalsIgnoreCase("true");
	}

	public long addCategory(Category category) {
		Map<String, Object> cat = new HashMap<>();
		
		cat.put("id", category.getId());
		cat.put("parent_id", category.getParent_id());
		cat.put("name", category.getName());
		cat.put("is_active", category.is_active());
		cat.put("position", category.getPosition());
		cat.put("level", category.getLevel());
		cat.put("children", "string");
		cat.put("include_in_menu", true);
		cat.put("available_sort_by", new ArrayList<>());
		cat.put("extension_attributes", new ArrayList<>());
		cat.put("custom_attributes", new ArrayList<>());
		
		Map<String, Object> req = new HashMap<>();
		
		req.put("category", cat);
		
		String uri = baseUri() + "/" + relativePath4Categories;
		String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
		String json = postSecure(uri, body, logger);

		if (!validateJSON(json)) {
			return -1;
		}
		
		return Long.parseLong(json);
	}

	public boolean updateCategory(Category category) {
		Map<String, Object> cat = new HashMap<>();
		
		cat.put("id", category.getId());
		cat.put("parent_id", category.getParent_id());
		cat.put("name", category.getName());
		cat.put("is_active", category.is_active());
		cat.put("position", category.getPosition());
		cat.put("level", category.getLevel());
		cat.put("children", "string");
		cat.put("include_in_menu", true);
		cat.put("available_sort_by", new ArrayList<>());
		cat.put("extension_attributes", new ArrayList<>());
		cat.put("custom_attributes", new ArrayList<>());
		
		Map<String, Object> req = new HashMap<>();
		
		req.put("category", cat);
		
		String uri = baseUri() + "/" + relativePath4Categories + "/" + category.getId();
		String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
		String json = postSecure(uri, body, logger);

		if (!validateJSON(json)) {
			return false;
		}
		return json.equalsIgnoreCase("true");
	}

	public Category getCategories() {
		String uri = baseUri() + "/" + relativePath4Categories;
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Category.class);
	}

	public Category getCategoryByIdClean(long id) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + id;
		
		return getCategoryByUrl(uri);
	}

	public Category getRootCategoryById(long id) {
		String uri = baseUri() + "/" + relativePath4Categories + "?rootCategoryId=" + id;
		
		return getCategoryByUrl(uri);
	}

	private Category getCategoryByUrl(String uri) {
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Category.class);
	}

	public Category getCategoryByIdWithChildren(long id) {
		Category all = getCategories();
		
		return getCategoryById(all, id);
	}

	private Category getCategoryById(Category category, long id) {
		if (category.getId() == id) {
			return category;
		}
		
		for (Category child : category.getChildren_data()) {
			Category cat = getCategoryById(child, id);
			if (cat != null) {
				return cat;
			}
		}
		
		return null;
	}

	public List<CategoryProduct> getProductsInCategory(long id) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + id + "/products";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, CategoryProduct.class);
	}

	public boolean addProductToCategory(long categoryId, String productSku, int position) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products";
		Map<String, Object> req = new HashMap<>();
		Map<String, Object> detail = new HashMap<>();
		
		detail.put("sku", productSku);
		detail.put("position", position);
		detail.put("category_id", categoryId);
		detail.put("extension_attributes", new HashMap<>());
		req.put("productLink", detail);
		
		String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
		String json = putSecure(uri, body, logger);

		return json.equals("true");
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
	}

	public boolean removeProductFromCategory(long categoryId, String productSku) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products/" + productSku;
		String json = deleteSecure(uri, logger);
		
		return json.equals("true");
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
