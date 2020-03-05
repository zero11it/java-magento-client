package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.product.ConfigurableProductOption;
import com.github.chen0040.magento.models.product.PriceUpdateResult;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.product.ProductAttribute;
import com.github.chen0040.magento.models.product.ProductAttributeGroup;
import com.github.chen0040.magento.models.product.ProductAttributeOption;
import com.github.chen0040.magento.models.product.ProductAttributeSet;
import com.github.chen0040.magento.models.product.ProductAttributeType;
import com.github.chen0040.magento.models.product.ProductCost;
import com.github.chen0040.magento.models.product.ProductPrice;
import com.github.chen0040.magento.models.product.ProductType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoProductManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Products = "/rest/V1/products";
	private static final String relativePath4ConfigurableProducts = "/rest/V1/configurable-products";
	private MagentoProductMediaManager media;
	
	private Map<String, AttributeOptionCacheEntry> attributeOptionCache;
	
	@Getter
	@Setter
	private class AttributeOptionCacheEntry {
		boolean valid;
		Map<String, ProductAttributeOption> options;
		
		public AttributeOptionCacheEntry() {
			this.valid = false;
			this.options = new HashMap<>();
		}
	}

	public MagentoProductManager(MagentoClient client) {
		super(client.getHttpComponent());
		
		this.client = client;
		this.media = new MagentoProductMediaManager(client);
		this.attributeOptionCache = null;
	}
	
	public MagentoProductMediaManager media() {
		return media;
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
	}

	public List<Product> searchProduct(SearchCriteria searchCriteria) {
		String uri = baseUri() + relativePath4Products + "?" + searchCriteria;
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Product.class);
	}

	public Product getProduct(String sku) {
		String uri = baseUri() + relativePath4Products + "/" + escape(sku);
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Product.class);
	}
	
	public ProductAttribute getAttribute(Integer attributeId) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeId;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		

		return JSON.parseObject(json, ProductAttribute.class);
	}
	
	public ProductAttribute getAttribute(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		

		return JSON.parseObject(json, ProductAttribute.class);
	}

	public List<ProductAttribute> getAttributes() {
		String uri = baseUri() + relativePath4Products + "/attributes?searchCriteria[currentPage]=0";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", ProductAttribute.class);
	}
	
	public List<ProductAttribute> getAttributesInSet(Integer attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/attributes";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductAttribute.class);
	}
	
	public List<ProductAttributeOption> getAttributeOptions(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ProductAttributeOption.class);
	}
	
	public ProductAttributeOption getAttributeOption(String attributeCode, ProductAttributeOption option) {
		List<ProductAttributeOption> options = getAttributeOptions(attributeCode);
		
		if (options == null) {
			return null;
		}
		
		Optional<ProductAttributeOption> ourOption = options.stream()
				.filter(_option -> _option.getLabel().equals(option.getLabel()))
				.findAny();
		
		if (ourOption.isPresent()) {
			return ourOption.get();
		}
		
		if (option.getStore_labels() != null) {
			for (ProductAttributeOption.StoreLabel storeLabel : option.getStore_labels()) {
				ourOption = options.stream()
						.filter(_option -> _option.getLabel().equals(storeLabel.getLabel()))
						.findAny();
				
				if (ourOption.isPresent()) {
					return ourOption.get();
				}
			}
		}
		
		return null;
	}

	private void createCacheIfNonexistent() {
		if (attributeOptionCache == null) {
			attributeOptionCache = new HashMap<>();
			
			for (ProductAttribute attribute : getAttributes()) {
				attributeOptionCache.put(attribute.getAttribute_code(), new AttributeOptionCacheEntry());
			}
		}
	}

	private ProductAttributeOption getAttributeOptionFromCache(String attributeCode, String label) {
		createCacheIfNonexistent();
		
		if (!attributeOptionCache.get(attributeCode).isValid()) {
			for (ProductAttributeOption option : getAttributeOptions(attributeCode)) {
				attributeOptionCache.get(attributeCode).getOptions().put(option.getLabel(), option);
			}
			attributeOptionCache.get(attributeCode).setValid(true);
		}

		return attributeOptionCache.get(attributeCode).getOptions().get(label);
	}

	private void invalidateCacheEntry(String attributeCode, String label) {
		createCacheIfNonexistent();
		
		attributeOptionCache.get(attributeCode).setValid(false);
		attributeOptionCache.get(attributeCode).getOptions().clear();
	}
	
	public String getAttributeOptionValue(String attributeCode, String label) {
		ProductAttributeOption option = getAttributeOptionFromCache(attributeCode, label);
		
		if (option == null) {
			return null;
		}
		
		return option.getValue();
	}
	
	public enum AttributeOptionPOSTMode {
		ALLOW_DUPLICATE_LABELS,
		NO_DUPLICATE_LABELS
	}
	
	public String addOptionToAttribute(String attributeCode, AttributeOptionPOSTMode mode, ProductAttributeOption option) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options";
		String body = RESTUtils.payloadWrapper("option", option);
		
		if (mode == AttributeOptionPOSTMode.NO_DUPLICATE_LABELS) {
			ProductAttributeOption ourOption = getAttributeOption(attributeCode, option);
			if (ourOption != null) {
					String msg = ourOption.getLabel() + " is already present.";
					logger.error(msg);
					return msg;
			}
		}
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		invalidateCacheEntry(attributeCode, option.getLabel());
		
		return JSON.parseObject(json, String.class);
	}

	public String addOptionToAttribute(String attributeCode, AttributeOptionPOSTMode mode, String label) {
		return addOptionToAttribute(attributeCode, mode, new ProductAttributeOption().setLabel(label));
	}

	public List<ProductAttributeType> getAttributeTypes() {
		String uri = baseUri() + relativePath4Products + "/attributes/types";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductAttributeType.class);
	}
	
	public List<ProductAttributeType> getOptionTypes() {
		String uri = baseUri() + relativePath4Products + "/options/types";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductAttributeType.class);
	}
	
	public ProductAttributeGroup getAttributeGroup(Integer attributeGroupId) {
		List<ProductAttributeGroup> groups = getAttributeGroups();
		
		if (groups == null) {
			return null;
		}
		
		Optional<ProductAttributeGroup> group = groups.stream()
				.filter(_group -> _group.getAttribute_group_id().equals(attributeGroupId.toString()))
				.findAny();
		
		return group.isPresent() ? group.get() : null;
	}
	
	public List<ProductAttributeGroup> getAttributeGroups() {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups/list?searchCriteria[currentPage]=0";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return RESTUtils.getArrayByKey(json, "items", ProductAttributeGroup.class);
	}
	
	public ProductAttributeSet getAttributeSet(Integer attributeSetId) {
		List<ProductAttributeSet> sets = getAttributeSets();
		
		if (sets == null) {
			return null;
		}
		
		Optional<ProductAttributeSet> set = sets.stream()
				.filter(_set -> _set.getAttribute_set_id().equals(attributeSetId))
				.findAny();
		
		return set.isPresent() ? set.get() : null;
	}

	public List<ProductAttributeSet> getAttributeSets() {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/sets/list?searchCriteria[currentPage]=0";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", ProductAttributeSet.class);
	}

	public List<ProductType> getProductTypes() {
		String uri = baseUri() + relativePath4Products + "/types";
		String json = getSecure(uri, logger);

		return JSON.parseArray(json, ProductType.class);
	}
	
	public List<ProductPrice> getProductPrices(List<String> skus) {
		String uri = baseUri() + relativePath4Products + "/base-prices-information";
		String json = postSecure(uri, RESTUtils.payloadWrapper("skus", skus), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ProductPrice.class);
	}
	
	public List<ProductCost> getProductCosts(List<String> skus) {
		String uri = baseUri() + relativePath4Products + "/cost-information";
		String json = postSecure(uri, RESTUtils.payloadWrapper("skus", skus), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ProductCost.class);
	}

	public boolean hasAttributeSet(Integer attributeSetId) {
		List<ProductAttributeSet> attributeSets = getAttributeSets();
		
		return attributeSets.stream()
				.filter(set -> set.getAttribute_set_id() == attributeSetId)
				.collect(Collectors.toList())
				.size() > 0;
	}
	
	public boolean hasAttributeGroup(String attributeGroupId) {
		List<ProductAttributeGroup> attributeGroups = getAttributeGroups();
		
		return attributeGroups.stream()
				.filter(set -> set.getAttribute_group_id().equals(attributeGroupId))
				.collect(Collectors.toList())
				.size() > 0;
	}
	
	public boolean hasAttribute(ProductAttribute attribute) {
		return getAttribute(attribute.getAttribute_code()) != null;
	}
	
	public boolean hasProduct(String sku) {
		return getProduct(sku) != null;
	}
	
	public ProductAttributeSet saveAttributeSet(ProductAttributeSet attributeSet) {
		ProductAttributeSet defaultSet = getAttributeSets().stream()
				.filter(set -> set.getAttribute_set_name().toLowerCase().equals("default"))
				.collect(Collectors.toList())
				.get(0);
		
		return saveAttributeSet(attributeSet, defaultSet);
	}
	
	public ProductAttributeSet saveAttributeSet(ProductAttributeSet attributeSet, ProductAttributeSet baseAttributeSet) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets";
		String body = RESTUtils.payloadWrapper(
						new String[] {"attributeSet",  "skeletonId"},
						new Object[] {attributeSet, baseAttributeSet.getAttribute_set_id()});
		
		String json;
		if (attributeSet.getAttribute_set_id() != null && hasAttributeSet(attributeSet.getAttribute_set_id())) {
			json = putSecure(uri, StringUtils.toUTF8(body), logger);
		}
		else {
			json = postSecure(uri, StringUtils.toUTF8(body), logger);
		}
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeSet.class);
	}
	
	public ProductAttributeGroup saveAttributeGroup(ProductAttributeGroup attributeGroup) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups";
		String body = RESTUtils.payloadWrapper("group", attributeGroup);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeGroup.class);
	}
	
	public ProductAttributeGroup saveAttributeGroup(ProductAttributeGroup attributeGroup, Integer attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/groups";
		String body = RESTUtils.payloadWrapper("group", attributeGroup);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeGroup.class);
	}
	
	public Integer assignAttribute(Integer attributeSetId, Integer attributeGroupId, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/attributes";
		Map<String, String> req = new HashMap<>();
		
		req.put("attributeSetId", new Integer(attributeSetId).toString());
		req.put("attributeGroupId", new Integer(attributeGroupId).toString());
		req.put("attributeCode", attributeCode);
		req.put("sortOrder", "0");
		
		String body = JSON.toJSONString(req);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		Integer resp;
		
		try {
			resp = JSON.parseObject(json, Integer.class);
		}
		catch (NumberFormatException exception) {
			resp = null;
		}
		
		return resp;
	}
	
	public ProductAttribute saveAttribute(ProductAttribute attribute) {
		String uri = baseUri() + relativePath4Products + "/attributes";
		String body = RESTUtils.payloadWrapper("attribute", attribute);
		
		String json;
		ProductAttribute oldAttribute = getAttribute(attribute.getAttribute_code());
		if (oldAttribute != null) {
			oldAttribute.setDefault_frontend_label(attribute.getDefault_frontend_label() == null ? oldAttribute.getDefault_frontend_label() : attribute.getDefault_frontend_label());
			oldAttribute.setFrontend_labels(attribute.getFrontend_labels() == null ? oldAttribute.getFrontend_labels() : attribute.getFrontend_labels());
			return saveAttribute(attribute, attribute.getAttribute_code());
		}
		else {
			json = postSecure(uri, StringUtils.toUTF8(body), logger);
		}
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttribute.class);
	}

	public ProductAttribute saveAttribute(ProductAttribute attribute, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode;
		String body = RESTUtils.payloadWrapper("attribute", attribute);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttribute.class);
	}
	
	public Product saveProduct(Product product) {
		String sku = product.getSku();
		String uri = baseUri() + relativePath4Products;
		String body = RESTUtils.payloadWrapper("product", product);
		
		String json;
		
		if (hasProduct(sku)) {
			uri = uri + "/" + escape(sku);
			json = putSecure(uri, StringUtils.toUTF8(body), logger);
		} else {
			json = postSecure(uri, StringUtils.toUTF8(body), logger);
		}
		
		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Product.class);
	}
	
	public Product saveProduct(String sku, Product product) {
		String uri = baseUri() + relativePath4Products + "/" + sku;
		String body = RESTUtils.payloadWrapper("product", product);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Product.class);
	}
	
	public List<Product> getConfigurableProductChildren(String parentSku) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + parentSku + "/children";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, Product.class);
	}
	
	public Boolean assignConfigurableProductChild(String parentSku, String childSku) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + parentSku + "/child";
		String body = JSON.toJSONString(Collections.singletonMap("childSku", childSku), SerializerFeature.PrettyFormat);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public Boolean deleteConfigurableProductChild(String parentSku, String childSku) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + parentSku + "/children/" + childSku;
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public List<ConfigurableProductOption> getConfigurableProductOptions(String sku) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options/all";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ConfigurableProductOption.class);
	}
	
	public ConfigurableProductOption getConfigurableProductOption(String sku, Integer optionId) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options/" + optionId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ConfigurableProductOption.class);
	}
	
	public Optional<ConfigurableProductOption> getExistingConfigurableProductOption(String sku, ConfigurableProductOption option) {
		Optional<ConfigurableProductOption> existing = getConfigurableProductOptions(sku).stream()
				.filter(_option -> _option.getLabel().equals(option.getLabel()))
				.findAny();
		
		return existing;
	}
	
	public Integer addConfigurableProductOption(String sku, ConfigurableProductOption option) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options";
		String body = RESTUtils.payloadWrapper("option", option);
		
		Optional<ConfigurableProductOption> existing = getExistingConfigurableProductOption(sku, option);
		if (existing.isPresent()) {
			option.getValues().addAll(existing.get().getValues());
			return updateConfigurableProductOption(sku, existing.get().getId(), option);
		} 
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}

	public Integer updateConfigurableProductOption(String sku, Integer optionId, ConfigurableProductOption option) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options/" + optionId;
		String body = RESTUtils.payloadWrapper("option", option);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Boolean deleteConfigurableProductOption(String sku, Integer optionId) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options/" + optionId;
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public List<Product> generateProductVariationsFromOptions(Product product, List<ConfigurableProductOption> options) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/variation";
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, Product.class);
	}
	
	public List<PriceUpdateResult> updateProductPrices(List<ProductPrice> prices) {
		String uri = baseUri() + relativePath4Products + "/base-prices";
		String body = RESTUtils.payloadWrapper("prices", prices);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, PriceUpdateResult.class);
	}
	
	public List<PriceUpdateResult> updateProductCosts(List<ProductCost> costs) {
		String uri = baseUri() + relativePath4Products + "/base-prices";
		String body = RESTUtils.payloadWrapper("prices", costs);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, PriceUpdateResult.class);
	}
	
	public Product updateProductAvailability(String sku, Integer amount) {
		Product product = new Product()
				.setExtension_attributes(new Product.ExtensionAttributes()
						.setStock(amount)
				);
		
		return saveProduct(sku, product);
	}
	
	public Product updateProductPrice(String sku, BigDecimal price) {
		Product product = new Product()
				.setPrice(price);
		
		return saveProduct(sku, product);
	}
	
	public Product updateProductPriceAndAvailability(String sku, BigDecimal price, Integer amount) {
		Product product = new Product()
				.setPrice(price)
				.setExtension_attributes(new Product.ExtensionAttributes()
						.setStock(amount)
				);
		
		return saveProduct(sku, product);
	}
	
	public Boolean deleteAttributeGroup(Integer groupId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups/" + groupId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteAttributeSet(Integer attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteAttributeInSet(Integer attributeSetId, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/attributes/" + attributeCode;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteAttribute(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteAttributeOption(String attributeCode, String optionId) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options/" + optionId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductCosts(List<String> skus) {
		String uri = baseUri() + relativePath4Products + "/cost-delete";
		String body = RESTUtils.payloadWrapper("skus", skus);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		return Boolean.parseBoolean(json);
	}

	public String deleteProduct(String sku) {
		String uri = baseUri() + relativePath4Products + "/" + escape(sku);

		return deleteSecure(uri, logger);
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
