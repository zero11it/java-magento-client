package com.github.chen0040.magento.models.product.media;

public enum ProductImageType {
	image,
	small_image,
	thumbnail,
	swatch_image,
	media_gallery;
	
	public static ProductImageType[] none() {
		return new ProductImageType[] {};
	}
	
	public static ProductImageType[] forConfigurableProduct() {
		return new ProductImageType[] {image, small_image, thumbnail};
	}
	
	public static ProductImageType[] forSimpleProduct() {
		return new ProductImageType[] {image, small_image, thumbnail, swatch_image};
	}
	
	public static ProductImageType[] additionalImage() {
		return new ProductImageType[] {media_gallery};
	}
}
