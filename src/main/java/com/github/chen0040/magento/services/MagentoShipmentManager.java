package com.github.chen0040.magento.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.shipment.ShipmentComment;
import com.github.chen0040.magento.models.shipment.ShipmentTrack;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.chen0040.magento.utils.StringUtils;
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
	
	public boolean email(Integer id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/emails";
		
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Shipment getShipment(Integer id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id;
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
	
	public List<ShipmentComment> getShipmentComments(Integer id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, ShipmentComment.class);
	}
	
	public String getLabel(Integer id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/" + id + "/comments";
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<Shipment> search(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Shipments + "s?" + criteria.toString();
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Shipment.class);
	}
	
	public Shipment saveShipment(Shipment shipment) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/";
		String body = RESTUtils.payloadWrapper("entity", shipment);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Shipment.class);
	}
	
	public ShipmentTrack saveTrack(ShipmentTrack track) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/track";
		String body = RESTUtils.payloadWrapper("entity", track);
		
		String json = postSecure(uri, StringUtils.toUTF8(body), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, ShipmentTrack.class);
	}
	
	public Boolean deleteTrack(Integer id) {
		String uri = baseUri() + "/" + relativePath4Shipments + "/track/" + id;
		
		String json = deleteSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class);
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
