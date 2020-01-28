package com.github.chen0040.magento.models.product.media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductImageContent {
	private String base64_encoded_data;
	private ImageMIMEType type;
	private String name;
}
