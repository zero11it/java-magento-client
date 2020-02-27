package com.github.chen0040.magento.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.invoice.Invoice;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.sales.SalesDataComment;
import com.github.chen0040.magento.models.sales.SalesDataRefund;
import com.github.chen0040.magento.models.search.ConditionType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

public class MagentoInvoiceManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Invoices = "rest/V1/invoice";
	
	public MagentoInvoiceManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}
	
	public List<Invoice> getInvoices() {
		String uri = baseUri() + "/" + relativePath4Invoices + "s?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Invoice.class);
	}
	
	public List<Invoice> searchInvoices(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Invoice.class);
	}
	
	public Invoice getInvoice(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Invoice.class);
	}
	
	public Order getParentOrder(Invoice invoice) {
		SearchCriteria criteria = new SearchCriteria()
				.addFilter("entity_id", invoice.getOrder_id().toString(), ConditionType.EQUAL);
		
		Optional<Order> order = client.order().searchOrders(criteria).stream().findFirst();
		
		if (order.isPresent()) {
			return order.get();
		}
		
		return null;
	}
	
	public Shipment getCorrespondingShipment(Invoice invoice) {
		SearchCriteria criteria = new SearchCriteria()
				.addFilter("order_id", invoice.getOrder_id().toString(), ConditionType.EQUAL);
		
		Set<Integer> invoiceItems = invoice.getItems().stream()
				.map(_item -> _item.getOrder_item_id())
				.collect(Collectors.toSet());
		
		Optional<Shipment> shipment = client.shipment().searchShipments(criteria)
				.stream()
				.filter(_shipment -> {
					Set<Integer> shipmentItems = _shipment.getItems().stream()
							.map(_item -> _item.getOrder_item_id())
							.collect(Collectors.toSet());

					return invoiceItems.containsAll(shipmentItems);
				})
				.findFirst();
		
		if (shipment.isPresent()) {
			return shipment.get();
		}
		
		return null;
	}
	
	public Invoice saveInvoice(Invoice invoice) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s";
		String body = RESTUtils.payloadWrapper("entity", invoice);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Invoice.class);
	}
	
	public List<SalesDataComment> getComments(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId + "/comments";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, SalesDataComment.class);
	}
	
	public SalesDataComment saveComment(SalesDataComment comment) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/comments";
		String body = RESTUtils.payloadWrapper("entity", comment);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, SalesDataComment.class);
	}
	
	public Integer refund(Integer invoiceId, SalesDataRefund refundInfo) {
		String uri = baseUri() + "/" + relativePath4Invoices + "/" + invoiceId + "/refund";
		String body = RESTUtils.payloadWrapper("entity", refundInfo);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public String setCapture(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId + "/capture";

		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, String.class);
	}
	
	public Boolean emailToCustomer(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId + "/emails";

		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	@Deprecated
	public Boolean makeVoid(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId + "/void";

		String json = postSecure(uri, "", logger);
		
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
