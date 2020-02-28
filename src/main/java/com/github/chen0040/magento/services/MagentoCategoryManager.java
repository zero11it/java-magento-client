package com.github.chen0040.magento.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.category.Category;
import com.github.chen0040.magento.models.category.CategoryProduct;
import com.github.chen0040.magento.models.category.ProductLink;
import com.github.chen0040.magento.models.search.ConditionType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;

/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoCategoryManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoCategoryManager.class);
	private MagentoClient client;
	private static final String relativePath4Categories = "rest/V1/categories";
	private Integer DEFAULT_ROOT_CATEGORY_ID;
	private Category DEFAULT_ROOT_CATEGORY;

	public MagentoCategoryManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}

	public Category getDefaultRootCategory() {
		if (DEFAULT_ROOT_CATEGORY_ID == null) {
			Category root = getCategory(1);
			String[] rootChildrenIds = root.getChildren().split(",");
			DEFAULT_ROOT_CATEGORY_ID = Integer.valueOf(rootChildrenIds[0]);
			DEFAULT_ROOT_CATEGORY = getCategory(DEFAULT_ROOT_CATEGORY_ID);
		}
		
		return DEFAULT_ROOT_CATEGORY;
	}

	public Category addCategory(Category category) {
		String uri = baseUri() + "/" + relativePath4Categories;
		
		if (category.getParent_id() == null) {
			category.setParent_id(getDefaultRootCategory().getId());
		}
		if (category.getCustomAttribute("url_key") == null || category.getCustomAttribute("url_path") == null) {
			generateUrlKeyAndPath(category);
		}
		String body = RESTUtils.payloadWrapper("category", category);
		
		String json;
		Optional<Category> existing = getExisting(category);
		if (existing.isPresent()) {
			if (category.getId() == null) {
				category.setId(existing.get().getId());
			}
			return updateCategory(category);
		}
		else {
			json = postSecure(uri, StringUtils.toUTF8(body), logger);
		}

		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Category.class);
	}

	public Category generateUrlKeyAndPath(Category category) {
		Category currentCategory;
		String url_key = category.getName().toLowerCase().replaceAll("\\s+", "_");
		String url_path = url_key;
		Integer parentId = category.getParent_id();
		Integer rootId = getDefaultRootCategory().getId();
		
		while (parentId != rootId) {
			currentCategory = getCategory(parentId);
			parentId = currentCategory.getParent_id();
			url_path = currentCategory.getName().toLowerCase().replaceAll("\\s+", "_") + "/" + url_path;
		}
		
		category.addCustomAttribute("url_key", url_key);
		category.addCustomAttribute("url_path", url_path);
		return category;
	}

	public Category updateCategory(Category category) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + category.getId();
		String body = RESTUtils.payloadWrapper("category", category);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Category.class);
	}

	public Optional<Category> getExisting(Category category) {
		SearchCriteria similar = new SearchCriteria().addFilter("name", category.getName(), ConditionType.LIKE);
		List<Category> similarCategories = searchCategories(similar);
		Optional<Category> existingCategory = similarCategories.stream()
				.filter(_category -> _category.getParent_id().equals(category.getParent_id()))
				.findAny();
		
		return existingCategory;
	}
	
	public boolean exists(Category category) {
		return getExisting(category).isPresent();
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
	
	@Deprecated
	public Category getCategory(String categoryName) {
		Optional<Category> category = searchCategories(new SearchCriteria().addFilter("name", categoryName, ConditionType.LIKE)).stream().findFirst();
		
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
	
	public Category getCategoryByUrlKey(String urlKey) {
		Optional<Category> category = getCategories().stream()
				.filter(_category -> getCategoryCustomAttribute(_category, "url_key") != null)
				.filter(_category -> getCategoryCustomAttribute(_category, "url_key").getValue().equals(urlKey))
				.findFirst();
		
		if (category.isPresent()) {
			return category.get();
		}
		
		return null;
	}

	public MagentoAttribute<String> getCategoryCustomAttribute(Category category, String attribute_code) {
		Optional<MagentoAttribute<String>> attribute = category.getCustom_attributes().stream()
		.filter(_attribute -> _attribute.getAttribute_code().equals(attribute_code))
		.findFirst();
		
		if (attribute.isPresent()) {
			return attribute.get();
		}
		
		return null;
	}

	public Boolean addProductToCategory(Integer categoryId, String productSku) {
		String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products";
		String body = RESTUtils.payloadWrapper("productLink", new ProductLink()
				.setCategory_id(categoryId.toString())
				.setSku(productSku)
		);
		
		String json;
		if (categoryHasProduct(categoryId, productSku)) {
			json = putSecure(uri, StringUtils.toUTF8(body), logger);
		}
		else {
			json = postSecure(uri, StringUtils.toUTF8(body), logger);
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
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
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
