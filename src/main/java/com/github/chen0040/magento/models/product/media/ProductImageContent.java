package com.github.chen0040.magento.models.product.media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductImageContent {
	private String base64_encoded_data;
	private String type;
	private String name;
	
	public ProductImageContent(String base64_encoded_data, ImageMIMEType type, String name) {
		this.base64_encoded_data = base64_encoded_data;
		this.type = type.toString();
		this.name = name;
	}
	
	public ProductImageContent setType(ImageMIMEType type) {
		this.type = type.toString();
		
		return this;
	}
}
