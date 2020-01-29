package com.github.chen0040.magento.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.shipment.ShipmentComment;
import com.github.chen0040.magento.models.shipment.ShipmentTrack;
import com.github.mgiorda.oauth.OAuthConfig;

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
	
	public boolean email(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/emails";
		
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Shipment getShipment(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id;
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
	
	public List<ShipmentComment> getShipmentComments(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ShipmentComment.class);
	}
	
	public String getLabel(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<Shipment> search(long currentPage, long pageSize) {
		String uri = baseUri() + "/" + relativePath4Shipments + "s?"
				+ "searchCriteria[currentPage]=" + currentPage + "&"
				+ "searchCriteria[pageSize]=" + pageSize;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), Shipment.class);
	}
	
	public List<Shipment> search(String currentPage, long pageSize) {
		String uri = baseUri() + "/" + relativePath4Shipments + "s?"
				+ "searchCriteria[currentPage]=" + currentPage + "&"
				+ "searchCriteria[pageSize]=" + pageSize;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), Shipment.class);
	}
	
	public List<Shipment> search(String field, String value, String condition_type) {
		String uri = baseUri() + "/" + relativePath4Shipments + "s?"
				+ "searchCriteria[filterGroups][0][filters][0][field]=" + field + "&"
				+ "searchCriteria[filterGroups][0][filters][0][value]=" + value + "&"
				+ "searchCriteria[filterGroups][0][filters][0][condition_type]=" + condition_type;
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), Shipment.class);
	}
	
	public Shipment saveShipment(Shipment shipment) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/";
		Map<String, Shipment> req = new HashMap<String, Shipment>();
		
		req.put("entity", shipment);
		
		String body = JSON.toJSONString(req);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
	
	public ShipmentTrack saveTrack(ShipmentTrack track) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/track";
		Map<String, ShipmentTrack> req = new HashMap<String, ShipmentTrack>();

		req.put("entity", track);
		
		String body = JSON.toJSONString(req);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ShipmentTrack.class);
	}
	
	public boolean deleteTrack(long id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/track/" + id;
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}

	@Override
	public boolean oauthEnabled() {
		return client.oauthEnabled();
	}

	@Override
	public OAuthConfig oAuth() {
		return client.oAuth();
	}
}
