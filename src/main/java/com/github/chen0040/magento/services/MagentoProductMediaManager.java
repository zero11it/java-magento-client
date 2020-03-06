package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.product.media.ImageMIMEType;
import com.github.chen0040.magento.models.product.media.ProductImage;
import com.github.chen0040.magento.models.product.media.ProductImage.ImageSourceType;
import com.github.chen0040.magento.models.product.media.ProductImageContent;
import com.github.chen0040.magento.models.product.media.ProductImageType;
import com.github.chen0040.magento.models.product.media.ProductVideoContent;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xschen on 15/6/2017.
 */
// TODO
public class MagentoProductMediaManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductMediaManager.class);
	private MagentoClient client;
	private static final String relativePath4Media = "/rest/all/V1/products/";

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
		String uri = baseUri() + relativePath4Media + sku + "/media";
		String body = RESTUtils.payloadWrapper("entry", image);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}

	public Integer uploadImage(String sku, String imageFilePath, String label) {
		ProductImage image = new ProductImage(
				imageFilePath,
				ImageSourceType.FILE,
				ProductImageType.values(),
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
				.setTypes(ProductImageType.values())
				.setContent(new ProductImageContent(base64String, imageType, label + "." + imageType));
		
		return uploadImage(sku, image);
	}
	
	public Integer uploadImageFromURL(String sku, String url, String label) {
		ProductImage image = new ProductImage(
				url,
				ImageSourceType.URL,
				ProductImageType.values(),
				label
		);
		
		return uploadImage(sku, image);
	}
	
	public Integer updateImage(String sku, String entryId, ProductImage image) {
		String uri = baseUri() + relativePath4Media + sku + "/media/" + entryId;
		String body = RESTUtils.payloadWrapper("entry", image);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}

	public Integer updateImage(String sku, String entryId, String imageFilePath, String label) {
		ProductImage image = new ProductImage(
				imageFilePath,
				ImageSourceType.FILE,
				ProductImageType.values(),
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
				.setTypes(ProductImageType.values())
				.setContent(new ProductImageContent(base64String, imageType, label + "." + imageType));
		
		return updateImage(sku, entryId, image);
	}
	
	public Integer updateImageFromURL(String sku, String entryId, String url, String label) {
		ProductImage image = new ProductImage(
				url,
				ImageSourceType.URL,
				ProductImageType.values(),
				label
		);
		
		return updateImage(sku, entryId, image);
	}
	
	public Integer linkVideo(String sku, String label, ProductVideoContent video) {
		ProductImage image = new ProductImage(label, video);
		
		return uploadImage(sku, image);
	}

	public List<ProductImage> getProductImages(String sku) {
		String uri = baseUri() + relativePath4Media + escape(sku) + "/media";
		
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseArray(json, ProductImage.class);
	}

	public ProductImage getProductImage(String sku, Integer entryId) {
		String uri = baseUri() + relativePath4Media + escape(sku) + "/media/" + entryId;
		
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, ProductImage.class);
	}

	public Boolean deleteProductImage(String sku, Integer entryId) {
		String uri = baseUri() + relativePath4Media + escape(sku) + "/media/" + entryId;
		
		String json = deleteSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, Boolean.class);
	}

	public String getProductImageAbsoluteUrl(String sku, Integer entryId) {
		ProductImage media = getProductImage(sku, entryId);
		String filename = media.getFile();
		
		return baseUri() + "/pub/media/catalog/product/" + filename;
	}

	public String getProductImageRelativeUrl(String sku, Integer entryId) {
		ProductImage media = getProductImage(sku, entryId);
		String filename = media.getFile();
		
		return "/pub/media/catalog/product/" + filename;
	}

	public List<String> getProductImageAbsoluteUrls(String sku) {
		List<ProductImage> mediaList = getProductImages(sku);
		List<String> result = new ArrayList<>();
		
		for (ProductImage media : mediaList) {
			String filename = media.getFile();
			String uri = baseUri() + "/pub/media/catalog/product/" + filename;
			result.add(uri);
		}
		
		return result;
	}

	public List<String> getProductImageRelativeUrls(String sku) {
		List<ProductImage> mediaList = getProductImages(sku);
		List<String> result = new ArrayList<>();
		
		for (ProductImage media : mediaList) {
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
