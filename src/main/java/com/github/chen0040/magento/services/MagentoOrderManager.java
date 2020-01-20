package com.github.chen0040.magento.services;

import com.github.chen0040.magento.MagentoClient;

public class MagentoOrderManager extends MagentoHttpComponent {

	private MagentoClient client;
	
	public MagentoOrderManager(MagentoClient client) {
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

	
}
