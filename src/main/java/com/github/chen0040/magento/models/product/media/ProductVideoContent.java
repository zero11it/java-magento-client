package com.github.chen0040.magento.models.product.media;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductVideoContent {
	private String media_type;
	private String video_provider;
	private String video_url;
	private String video_title;
	private String video_description;
	private String video_metadata;
}
