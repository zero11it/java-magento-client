package com.github.chen0040.magento.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.creditmemo.CreditMemo;
import com.github.chen0040.magento.models.creditmemo.CreditMemo.RefundType;
import com.github.chen0040.magento.models.sales.SalesDataComment;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

public class MagentoCreditMemoManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4CreditMemos = "rest/V1/creditmemo";
	
	public MagentoCreditMemoManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}
	
	public CreditMemo getCreditMemo(Integer creditmemoId) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/" + creditmemoId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, CreditMemo.class);
	}
	
	public List<CreditMemo> getCreditMemos() {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "s";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", CreditMemo.class);
	}
	
	public List<CreditMemo> searchCreditMemos(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "s?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", CreditMemo.class);
	}
	
	public List<SalesDataComment> getComments(Integer creditmemoId) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/" + creditmemoId + "/comments";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", SalesDataComment.class);
	}
	
	public CreditMemo saveCreditMemo(CreditMemo creditmemo) {
		String uri = baseUri() + "/" + relativePath4CreditMemos;
		String body = RESTUtils.payloadWrapper("entity", creditmemo);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, CreditMemo.class);
	}
	
	public SalesDataComment saveComment(Integer creditmemoId, SalesDataComment comment) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/" + creditmemoId + "/comments";
		String body = RESTUtils.payloadWrapper("entity", comment);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, SalesDataComment.class);
	}
	
	public CreditMemo refund(CreditMemo creditmemo, RefundType offlineRequested) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/refund";
		String body = RESTUtils.payloadWrapper(
				new String[] {"creditmemo", "offlineRquested"},
				new Object[] {creditmemo, offlineRequested.getValue()});
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, CreditMemo.class);
	}
	
	public Boolean cancel(Integer creditmemoId) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/" + creditmemoId;
		
		String json = putSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public Boolean emailToUser(Integer creditmemoId) {
		String uri = baseUri() + "/" + relativePath4CreditMemos + "/" + creditmemoId + "/emails";
		
		String json = putSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
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
