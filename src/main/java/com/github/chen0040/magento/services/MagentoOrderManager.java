package com.github.chen0040.magento.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.order.Address;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.order.OrderInvoice;
import com.github.chen0040.magento.models.order.OrderItem;
import com.github.chen0040.magento.models.order.OrderRefund;
import com.github.chen0040.magento.models.order.SalesDataComment;
import com.github.chen0040.magento.models.order.SalesDataShipment;
import com.github.chen0040.magento.models.order.StatusHistory;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

public class MagentoOrderManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Orders = "rest/V1/order";
	private static final String relativePath4Invoices = "rest/V1/invoice";
	
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

	public List<Order> getOrders() {
		String uri = baseUri() + "/" + relativePath4Orders + "s?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Order.class);
	}
	
	public Order getOrder(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public List<Order> searchOrders(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Orders + "s?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Order.class);
	}
	
	public List<OrderItem> getItems(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items/" + id;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, OrderItem.class);
	}
	
	public String getStatuses(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/statuses/" + id;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<StatusHistory> getComments(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/comments/" + id;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, StatusHistory.class);
	}
	
	public List<OrderItem> searchItems(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", OrderItem.class);
	}
	
	public Boolean cancelOrder(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/cancel";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean addComment(Integer id, StatusHistory comment) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/comments";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("statusHistory", comment), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean emailOrderUser(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/emails";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean holdOrder(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/hold";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean unholdOrder(Integer id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/unhold";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Order saveOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/";
		
		String json = postSecure(uri, RESTUtils.payloadWrapper("entity", order), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Order createOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/create";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", order), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Address createOrderAddress(String parent_id, Address address) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + parent_id;
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", address), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Address.class);
	}
	
	public Integer createOrderInvoice(Integer orderId, OrderInvoice invoice) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/invoice";
		
		String json = postSecure(uri, JSON.toJSONString(invoice), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer createOrderRefund(Integer orderId, OrderRefund refund) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/refund";
		
		String json = postSecure(uri, JSON.toJSONString(refund), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer shipOrder(Integer orderId, SalesDataShipment shipment) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/ship";
		
		String json = postSecure(uri, JSON.toJSONString(shipment), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public List<OrderInvoice> getInvoices() {
		String uri = baseUri() + "/" + relativePath4Invoices + "s?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", OrderInvoice.class);
	}
	
	public List<OrderInvoice> searchInvoices(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", OrderInvoice.class);
	}
	
	public OrderInvoice getInvoice(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, OrderInvoice.class);
	}
	
	public OrderInvoice saveInvoice(OrderInvoice invoice) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s";
		String body = RESTUtils.payloadWrapper("entity", invoice);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, OrderInvoice.class);
	}
	
	public List<SalesDataComment> getInvoiceComments(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/" + invoiceId + "/comments";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, SalesDataComment.class);
	}
	
	public SalesDataComment saveInvoiceComment(SalesDataComment comment) {
		String uri = baseUri() + "/" + relativePath4Invoices + "s/comments";
		String body = RESTUtils.payloadWrapper("entity", comment);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, SalesDataComment.class);
	}
	
	public Integer refundInvoice(Integer invoiceId, OrderRefund refund) {
		String uri = baseUri() + "/" + relativePath4Invoices + "/" + invoiceId + "/refund";
		String body = RESTUtils.payloadWrapper("entity", refund);
		
		String json = postSecure(uri, body, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public String setInvoiceCapture(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "/" + invoiceId + "/capture";

		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, String.class);
	}
	
	public Boolean emailInvoiceUser(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "/" + invoiceId + "/emails";

		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class);
	}
	
	public Boolean voidInvoice(Integer invoiceId) {
		String uri = baseUri() + "/" + relativePath4Invoices + "/" + invoiceId + "/void";

		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
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
