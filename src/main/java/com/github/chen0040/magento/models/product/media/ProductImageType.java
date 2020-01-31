package com.github.chen0040.magento.models.product.media;

public enum ProductImageType {
	image,
	small_image,
	thumbnail;
	
	public static ProductImageType[] all() {
		return new ProductImageType[] {ProductImageType.image, ProductImageType.small_image, ProductImageType.thumbnail};
	}
}
