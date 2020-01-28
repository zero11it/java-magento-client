package com.github.chen0040.magento.models.product.media;

public enum ImageMIMEType {
	BITMAP("image/bmp"),
	GIF("image/gif"),
	JPEG("image/jpeg"),
	SVG("image/svg"),
	TIFF("image/tiff"),
	PNG("image/png"),
	UNSUPPORTED("")
	;
	
	private String value;
	
	private ImageMIMEType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
