package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.product.ProductMedia;
import com.github.chen0040.magento.models.product.media.ImageMIMEType;
import com.github.chen0040.magento.models.product.media.ProductImage;
import com.github.chen0040.magento.models.product.media.ProductImageContent;
import com.github.chen0040.magento.models.product.media.ProductImageType;
import com.github.chen0040.magento.models.product.media.ProductVideoContent;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by xschen on 15/6/2017.
 */
// TODO
public class MagentoProductMediaManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductMediaManager.class);
	private MagentoClient client;

	public MagentoProductMediaManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
	}
	
	public Integer uploadImage(String sku, ProductImage image) {
		String uri = baseUri() + "/rest/V1/products/" + sku + "/media";
		String body = RESTUtils.payloadWrapper("entry", image);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}

	public Integer uploadImage(String sku, String imageFilePath, String label) {
		ProductImage image = new ProductImage(
				imageFilePath,
				ProductImageType.all(),
				label
		);
		
		return uploadImage(sku, image);
	}

	public Integer uploadImage(String sku, String base64String, String label, ImageMIMEType imageType) {
		ProductImage image = new ProductImage()
				.setMedia_type("image")
				.setLabel(label)
				.setPosition(1)
				.setDisabled(false)
				.setTypes(Arrays.asList(new ProductImageType[] {ProductImageType.image, ProductImageType.small_image, ProductImageType.thumbnail}))
				.setContent(new ProductImageContent(base64String, imageType, label + "." + imageType));
		
		return uploadImage(sku, image);
	}
	
	public Integer updateImage(String sku, String entryId, ProductImage image) {
		String uri = baseUri() + "/rest/V1/products/" + sku + "/media/" + entryId;
		String body = RESTUtils.payloadWrapper("entry", image);
		
		String json = putSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}

	public Integer updateImage(String sku, String entryId, String imageFilePath, String label) {
		ProductImage image = new ProductImage(
				imageFilePath,
				new ProductImageType[] {ProductImageType.image, ProductImageType.small_image, ProductImageType.thumbnail},
				label
		);
		
		return updateImage(sku, entryId, image);
	}

	public Integer updateImage(String sku, String entryId, String base64String, String label, ImageMIMEType imageType) {
		ProductImage image = new ProductImage()
				.setMedia_type("image")
				.setLabel(label)
				.setPosition(1)
				.setDisabled(false)
				.setTypes(Arrays.asList(ProductImageType.all()))
				.setContent(new ProductImageContent(base64String, imageType, label + "." + imageType));
		
		return updateImage(sku, entryId, image);
	}
	
	public Integer linkVideo(String sku, String label, ProductVideoContent video) {
		ProductImage image = new ProductImage(label, video);
		
		return uploadImage(sku, image);
	}

	public List<ProductMedia> getProductMediaList(String sku) {
		String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media";
		
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductMedia.class);
	}

	public ProductMedia getProductMedia(String sku, Integer entryId) {
		String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media/" + entryId;
		
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, ProductMedia.class);
	}

	public Boolean deleteProductMedia(String sku, Integer entryId) {
		String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media/" + entryId;
		
		String json = deleteSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Boolean.class);
	}

	public String getProductMediaAbsoluteUrl(String sku, Integer entryId) {
		ProductMedia media = getProductMedia(sku, entryId);
		String filename = media.getFile();
		
		return baseUri() + "/pub/media/catalog/product/" + filename;
	}

	public String getProductMediaRelativeUrl(String sku, Integer entryId) {
		ProductMedia media = getProductMedia(sku, entryId);
		String filename = media.getFile();
		
		return "/pub/media/catalog/product/" + filename;
	}

	public List<String> getProductMediaAbsoluteUrls(String sku) {
		List<ProductMedia> mediaList = getProductMediaList(sku);
		List<String> result = new ArrayList<>();
		
		for (ProductMedia media : mediaList) {
			String filename = media.getFile();
			String uri = baseUri() + "/pub/media/catalog/product/" + filename;
			result.add(uri);
		}
		
		return result;
	}

	public List<String> getProductMediaRelativeUrls(String sku) {
		List<ProductMedia> mediaList = getProductMediaList(sku);
		List<String> result = new ArrayList<>();
		
		for (ProductMedia media : mediaList) {
			String filename = media.getFile();
			String uri = "/pub/media/catalog/product/" + filename;
			result.add(uri);
		}
		
		return result;
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
