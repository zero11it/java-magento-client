package com.github.chen0040.magento.models.product.media;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.annotation.JSONField;

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
	private List<String> types;
	private String file;
	private ProductImageContent content;
	private Map<String, ProductVideoContent> extension_attributes;
	
	@JSONField(serialize = false, deserialize = false)
	private static final int[] THUMBNAIL_SIZE = {50, 50};
	@JSONField(serialize = false, deserialize = false)
	private static final int[] SMALL_IMAGE_SIZE = {470, 470};
	
	public enum ImageSourceType {
		URL,
		FILE
	}
	
	public ProductImage(String path, ImageSourceType source, ProductImageType[] types, String label) {
		this.media_type = "image";
		this.label = label;
		this.position = 1;
		this.disabled = false;
		this.types = Arrays.asList(types).stream()
				.map(type -> type.toString())
				.collect(Collectors.toList());
		
		String imageName = getImageName(path);
		
		if (source == ImageSourceType.FILE) {
			this.content = new ProductImageContent(encodeImageFile(path), getImageType(imageName), imageName);
		}
		else {
			this.content = new ProductImageContent(encodeImageFromUrl(path), getImageType(imageName), imageName);
		}
		
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
		this.setTypes(ProductImageType.image);
		this.setExtension_attributes(extension_attributes);
		
	}

	private String getImageName(String filePath) {
		String[] tokens = filePath.split("/");
		
		return tokens[tokens.length-1];
	}

	private ImageMIMEType getImageType(String fileName) {
		String[] tokens = fileName.split("\\.");
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

	private String encodeImageFile(String filePath) {
		byte[] imageBytes;
		
		try {
			imageBytes = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
		}
		catch (IOException e) {
			return "";
		}
		
		return Base64.encodeBase64String(imageBytes);
	}
	
	private String encodeImageFromUrl(String url) {
		InputStream input = null;
		byte[] imageBytes;
		
		try {
			input = new URL(url).openStream();
			imageBytes = IOUtils.toByteArray(input);
			input.close();
		}
		catch (IOException e) {
			return "";
		}

		return Base64.encodeBase64String(imageBytes);
	}
	
	public ProductImage setTypes(ProductImageType type) {
		this.types = Arrays.asList(new ProductImageType[] {type}).stream()
		.map(_type -> _type.toString())
		.collect(Collectors.toList());
		
		return this;
	}
	
	public ProductImage setTypes(ProductImageType... types) {
		this.types = Arrays.asList(types).stream()
		.map(_type -> _type.toString())
		.collect(Collectors.toList());
		
		return this;
	}
	
	public ProductImage setTypes(List<String> types) {
		this.types = types;
		return this;
	}
	
	@JSONField(serialize = false, deserialize = false)
	public int[] getDimensions() {
		String data = content.getBase64_encoded_data();
		byte[] bytes = Base64.decodeBase64(data);
		
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			BufferedImage img = ImageIO.read(in);
			return new int[] {img.getWidth(), img.getHeight()};
		}
		catch (IOException e) {
			return new int[] {0, 0};
		}
	}
	
	@JSONField(serialize = false, deserialize = false)
	public int getPixelCount() {
		int[] dims = getDimensions();
		
		return dims[0] * dims[1];
	}
	
	@JSONField(serialize = false, deserialize = false)
	public int getSize() {
		try {
			return Base64.decodeBase64(content.getBase64_encoded_data()).length;
		}
		catch (NullPointerException e) {
			return 0;
		}
	}
	
	public ProductImage resizeToThumbnail() {
		String data = resize(content.getBase64_encoded_data(), THUMBNAIL_SIZE);
		content.setBase64_encoded_data(data);
		return this;
	}
	
	public ProductImage resizeToSmallImage() {
		String data = resize(content.getBase64_encoded_data(), SMALL_IMAGE_SIZE);
		content.setBase64_encoded_data(data);
		return this;
	}
	
	public ProductImage resizeToCustomSize(int width, int height) {
		String data = resize(content.getBase64_encoded_data(), new int[] {width, height});
		content.setBase64_encoded_data(data);
		return this;
	}

	private String resize(String data, int[] size) {
		byte[] bytes = Base64.decodeBase64(data);
		
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
			Image scaledImg = img.getScaledInstance(size[0], size[1], Image.SCALE_SMOOTH);
			BufferedImage scaledImgBuffer = new BufferedImage(size[0], size[1], BufferedImage.TYPE_INT_RGB);
			scaledImgBuffer.getGraphics().drawImage(scaledImg, 0, 0, new Color(0,0,0), null);
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	        ImageIO.write(scaledImgBuffer, "jpg", buffer);
	        
	        return Base64.encodeBase64String(buffer.toByteArray());
		}
		catch (IOException e) {
			return "";
		}
	}
	
	public List<ProductImage> generateDistinctTypes() {
		List<ProductImage> images = new ArrayList<>();
		
		for (String type : types) {
			if (type.equals(ProductImageType.thumbnail.toString())) {
				ProductImage image = new ProductImage()
						.setLabel("thumbnail-" + label)
						.setDisabled(true)
						.setTypes(ProductImageType.thumbnail)
						.setMedia_type(media_type)
						.setContent(new ProductImageContent(
								resize(content.getBase64_encoded_data(), THUMBNAIL_SIZE),
								content.getType(),
								content.getName()
						));
				images.add(image);
			}
			else if (type.equals(ProductImageType.small_image.toString())) {
				ProductImage image = new ProductImage()
						.setLabel("small-" + label)
						.setDisabled(true)
						.setTypes(ProductImageType.small_image)
						.setMedia_type(media_type)
						.setContent(new ProductImageContent(
								resize(content.getBase64_encoded_data(), SMALL_IMAGE_SIZE),
								content.getType(),
								content.getName()
						));
				images.add(image);
			}
			else if (type.equals(ProductImageType.media_gallery.toString())) {
				ProductImage image = new ProductImage()
						.setLabel("gallery-" + label)
						.setDisabled(true)
						.setTypes(ProductImageType.media_gallery)
						.setMedia_type(media_type)
						.setContent(new ProductImageContent(
								content.getBase64_encoded_data(),
								content.getType(),
								content.getName()
						));
				images.add(image);
			}
			else if (type.equals(ProductImageType.image.toString())) {
				List<String> imageTypes = types.stream()
						.filter(_type -> {
							return !_type.equals(ProductImageType.thumbnail.toString()) && !_type.equals(ProductImageType.small_image.toString());
						})
						.collect(Collectors.toList());
				ProductImage image = new ProductImage()
						.setLabel("image-" + label)
						.setDisabled(false)
						.setTypes(imageTypes)
						.setMedia_type(media_type)
						.setContent(new ProductImageContent(
								content.getBase64_encoded_data(),
								content.getType(),
								content.getName()
						));
				images.add(image);
			}
		}
		
		return images;
	}
}
