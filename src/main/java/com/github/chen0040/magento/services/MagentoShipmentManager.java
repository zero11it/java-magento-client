package com.github.chen0040.magento.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.shipment.ShipmentComment;

public class MagentoShipmentManager extends MagentoHttpComponent {

	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Shipments = "rest/V1/shipment";

	public MagentoShipmentManager(MagentoClient client) {
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
	
	public Shipment getShipment(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id;
		String json = getSecured(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
	
	public List<ShipmentComment> getShipmentComments(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecured(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ShipmentComment.class);
	}
	
	public String getLabel(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecured(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<Shipment> search(long currentPage, long pageSize) {
		String uri = baseUri() + "/" + relativePath4Shipments + "s?"
				+ "searchCriteria[currentPage]=" + currentPage + "&"
				+ "searchCriteria[pageSize]=" + pageSize;
		String json = getSecured(uri);

		if (!validate(json)) {
			return null;
		}
		
		Map<String, String> resp = (HashMap<String, String>) JSON.parseObject(json, HashMap.class);
		
		return JSON.parseArray(resp.get("items"), Shipment.class);
	}
	
	public List<Shipment> search(String field, String value, String condition_type) {
		String uri = baseUri() + "/" + relativePath4Shipments + "?"
				+ "searchCriteria[filterGroups][0][filters][0][field]=" + field + "&"
				+ "searchCriteria[filterGroups][0][filters][0][value]=" + value + "&"
				+ "searchCriteria[filterGroups][0][filters][0][condition_type]=" + condition_type;
		String json = getSecured(uri);

		if (!validate(json)) {
			return null;
		}
		
		return JSON.parseArray(json, Shipment.class);
	}
	
	public Shipment saveShipment(Shipment shipment) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/";
		Map<String, String> req = new HashMap<String, String>();
		
		req.put("entity", JSON.toJSONString(shipment));
		
		String body = JSON.toJSONString(req);
		String json = postSecure(uri, body);
		
		if (!validate(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
}
