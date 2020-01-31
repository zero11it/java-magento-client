package com.github.chen0040.magento.models.product.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
	private Integer id;
	private String media_type;
	private String label;
	private Integer position;
	private Boolean disabled;
	List<ProductImageType> types;
	private String file;
	ProductImageContent content;
	Map<String, ProductVideoContent> extension_attributes;
	
	public ProductImage(String filePath, ProductImageType[] types, String label) {
		this.media_type = "image";
		this.label = label;
		this.position = 1;
		this.disabled = false;
		this.types = Arrays.asList(types);
		
		String imageName = getImageName(filePath);
		this.content = new ProductImageContent(encodeImage(filePath), getImageType(imageName), imageName);
		
		this.extension_attributes = null;
	}
	
	public ProductImage(String label, ProductVideoContent video) {
		Map<String, ProductVideoContent> extension_attributes = Stream.of(new Object[][] {
				{"video_content", video},
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (ProductVideoContent) data[1]));
		
		this.setMedia_type("video");
		this.setLabel(label);
		this.setDisabled(false);
		this.setPosition(1);
		this.setTypes(Arrays.asList(ProductImageType.image));
		this.setExtension_attributes(extension_attributes);
		
	}

	private String getImageName(String filePath) {
		String[] tokens = filePath.split("/");
		
		return tokens[tokens.length-1];
	}

	private ImageMIMEType getImageType(String fileName) {
		String[] tokens = fileName.split(".");
		String ext = tokens[tokens.length-1].toLowerCase();
		
		if (ext.equals("bmp")) {
			return ImageMIMEType.BITMAP;
		}
		else if (ext.equals("gif")) {
			return ImageMIMEType.GIF;
		}
		else if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("jpe")) {
			return ImageMIMEType.JPEG;
		}
		else if (ext.equals("svg")) {
			return ImageMIMEType.SVG;
		}
		else if (ext.equals("tif") || ext.equals("tiff")) {
			return ImageMIMEType.TIFF;
		}
		else if (ext.equals("png")) {
			return ImageMIMEType.PNG;
		}
		else {
			return ImageMIMEType.UNSUPPORTED;
		}
	}

	private String encodeImage(String filePath) {
		byte[] imageBytes;
		try {
			imageBytes = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
		
		return Base64.encodeBase64String(imageBytes);
	}
}
