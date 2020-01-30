package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.product.ConfigurableProductOption;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.product.ProductAttribute;
import com.github.chen0040.magento.models.product.ProductAttributeGroup;
import com.github.chen0040.magento.models.product.ProductAttributeOption;
import com.github.chen0040.magento.models.product.ProductAttributeSet;
import com.github.chen0040.magento.models.product.ProductAttributeType;
import com.github.chen0040.magento.models.product.ProductType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public MagentoProductManager(MagentoClient client) {
		super(client.getHttpComponent());
		
		this.client = client;
		this.media = new MagentoProductMediaManager(client);
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

	public Product getProductBySku(String sku) {
		String uri = baseUri() + relativePath4Products + "/" + escape(sku);
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Product.class);
	}

	public List<ProductAttribute> getProductAttributes() {
		String uri = baseUri() + relativePath4Products + "/attributes?searchCriteria[currentPage]=0";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		

		return RESTUtils.getArrayByKey(json, "items", ProductAttribute.class);
	}
	
	public ProductAttribute getProductAttributeByCode(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, ProductAttribute.class);
	}
	
	public List<ProductAttribute> getProductAttributesInSet(long attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/attributes";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return RESTUtils.getArrayByKey(json, "items", ProductAttribute.class);
	}
	
	public List<ProductAttributeOption> getProductAttributeOptions(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ProductAttributeOption.class);
	}
	
	public String getProductAttributeOptionValue(String attributeCode, String label) {
		List<ProductAttributeOption> options = getProductAttributeOptions(attributeCode);
		
		if (options == null) {
			return null;
		}
		
		List<ProductAttributeOption> labelOptions = options.stream()
			.filter(option -> option.getLabel().equals(label))
			.collect(Collectors.toList());
		
		if (labelOptions.size() == 0) {
			return null;
		}
		
		return labelOptions.get(0).getValue();
	}

	public List<ProductAttributeType> getProductAttributeTypes() {
		String uri = baseUri() + relativePath4Products + "/attributes/types";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductAttributeType.class);
	}
	
	public List<ProductAttributeGroup> getProductAttributeGroups() {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups/list";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return RESTUtils.getArrayByKey(json, "items", ProductAttributeGroup.class);
	}

	public List<ProductAttributeSet> getProductAttributeSets() {
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

	public boolean hasProductAttributeSet(long attributeSetId) {
		List<ProductAttributeSet> attributeSets = getProductAttributeSets();
		
		return attributeSets.stream()
				.filter(set -> set.getAttribute_set_id() == attributeSetId)
				.collect(Collectors.toList())
				.size() > 0;
	}
	
	public boolean hasProductAttributeGroup(String attributeGroupId) {
		List<ProductAttributeGroup> attributeGroups = getProductAttributeGroups();
		
		return attributeGroups.stream()
				.filter(set -> set.getAttribute_group_id().equals(attributeGroupId))
				.collect(Collectors.toList())
				.size() > 0;
	}
	
	public boolean hasAttribute(ProductAttribute attribute) {
		List<ProductAttribute> attributes = getProductAttributes().stream()
				.filter(attr -> attr.getAttribute_code().equals(attribute.getAttribute_code()))
				.collect(Collectors.toList());
		
		return attributes.size() > 0;
	}
	
	public boolean hasProduct(String sku) {
		return getProductBySku(sku) != null;
	}
	
	public ProductAttributeSet saveProductAttributeSet(ProductAttributeSet attributeSet) {
		ProductAttributeSet defaultSet = getProductAttributeSets().stream()
				.filter(set -> set.getAttribute_set_name().toLowerCase().equals("default"))
				.collect(Collectors.toList())
				.get(0);
		
		return saveProductAttributeSet(attributeSet, defaultSet);
	}
	
	public ProductAttributeSet saveProductAttributeSet(ProductAttributeSet attributeSet, ProductAttributeSet baseAttributeSet) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets";
		String body = "{"
				+ "\"attributeSet\" : " + JSON.toJSONString(attributeSet) + ","
				+ "\"skeletonId\" : " + baseAttributeSet.getAttribute_set_id()
				+ "}";
		
		String json;
		
		if (attributeSet.getAttribute_set_id() != null && hasProductAttributeSet(attributeSet.getAttribute_set_id())) {
			json = putSecure(uri, body, logger);
		}
		else {
			json = postSecure(uri, body, logger);
		}
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeSet.class);
	}
	
	public ProductAttributeGroup saveProductAttributeGroup(ProductAttributeGroup attributeGroup) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups";
		String body = "{"
				+ "\"group\" : " + JSON.toJSONString(attributeGroup)
				+ "}";
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeGroup.class);
	}
	
	public ProductAttributeGroup saveProductAttributeGroup(ProductAttributeGroup attributeGroup, long attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/groups";
		String body = "{"
				+ "\"group\" : " + JSON.toJSONString(attributeGroup)
				+ "}";
		
		String json = putSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttributeGroup.class);
	}
	
	public Long assignAttribute(long attributeSetId, long attributeGroupId, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/attributes";
		Map<String, String> req = new HashMap<>();
		
		req.put("attributeSetId", new Long(attributeSetId).toString());
		req.put("attributeGroupId", new Long(attributeGroupId).toString());
		req.put("attributeCode", attributeCode);
		req.put("sortOrder", "0");
		
		String body = JSON.toJSONString(req);
		
		String json = postSecure(uri, body, logger);
		Long resp;
		
		try {
			resp = JSON.parseObject(json, Long.class);
		}
		catch (NumberFormatException exception) {
			resp = null;
		}
		
		return resp;
	}
	
	public ProductAttribute saveAttribute(ProductAttribute attribute) {
		String uri = baseUri() + relativePath4Products + "/attributes";
		String body = RESTUtils.payloadWrapper("attribute", attribute);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttribute.class);
	}

	public ProductAttribute saveAttribute(ProductAttribute attribute, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/attributes/" + attributeCode;
		String body = RESTUtils.payloadWrapper("attribute", attribute);
		
		String json = putSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ProductAttribute.class);
	}
	
	public String saveAttributeOption(ProductAttributeOption option, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options";
		String body = RESTUtils.payloadWrapper("option", option);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, String.class);
	}
	
	public String addOptionToAttribute(String attributeCode, String label) {
		List<ProductAttributeOption> options = getProductAttributeOptions(attributeCode);
		Optional<ProductAttributeOption> ourOption = options.stream()
				.filter(option -> option.getLabel().equals(label))
				.findAny();
		
		if (ourOption.isPresent()) {
			logger.info(label + " already present.");
			return label + " already present.";
		}
		
		return saveAttributeOption(new ProductAttributeOption().setLabel(label), attributeCode);
	}
	
	public Product saveProduct(Product product) {
		String sku = product.getSku();
		String uri = baseUri() + relativePath4Products;
		String body = "{\n\"product\" : " + JSON.toJSONString(product, SerializerFeature.PrettyFormat) + "\n}";
		
		String json;
		
		if (hasProduct(sku)) {
			uri = uri + "/" + escape(sku);
			json = putSecure(uri, body, logger);
		} else {
			json = postSecure(uri, body, logger);
		}
		
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
		String body = String.format("{ \"childSku\" : \"%s\" }", childSku);
		
		String json = postSecure(uri, body, logger);
		
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
	
	public Integer addConfigurableProductOption(ConfigurableProductOption option, String sku) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options";
		String body = RESTUtils.payloadWrapper("option", option);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer updateConfigurableProductOption(ConfigurableProductOption option, String sku, Integer optionId) {
		String uri = baseUri() + relativePath4ConfigurableProducts + "/" + sku + "/options/" + optionId;
		String body = RESTUtils.payloadWrapper("option", option);
		
		String json = putSecure(uri, body, logger);
		
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
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, PriceUpdateResult.class);
	}
	
	public List<PriceUpdateResult> updateProductCosts(List<ProductCost> costs) {
		String uri = baseUri() + relativePath4Products + "/base-prices";
		String body = RESTUtils.payloadWrapper("prices", costs);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, PriceUpdateResult.class);
	}
	
	public Boolean deleteProductAttributeGroup(long groupId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/groups/" + groupId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductAttributeSet(long attributeSetId) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductAttributeInSet(long attributeSetId, String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attribute-sets/" + attributeSetId + "/attributes/" + attributeCode;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductAttribute(String attributeCode) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductAttributeOption(String attributeCode, String optionId) {
		String uri = baseUri() + relativePath4Products + "/attributes/" + attributeCode + "/options/" + optionId;
		
		String json = deleteSecure(uri, logger);
		
		return Boolean.parseBoolean(json);
	}
	
	public Boolean deleteProductCosts(List<String> skus) {
		String uri = baseUri() + relativePath4Products + "/cost-delete";
		String body = RESTUtils.payloadWrapper("skus", skus);
		
		String json = postSecure(uri, body, logger);
		
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
