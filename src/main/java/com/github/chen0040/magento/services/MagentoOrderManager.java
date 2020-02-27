package com.github.chen0040.magento.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.invoice.Invoice;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.order.OrderItem;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.sales.SalesDataAddress;
import com.github.chen0040.magento.models.sales.SalesDataComment;
import com.github.chen0040.magento.models.sales.SalesDataInvoice;
import com.github.chen0040.magento.models.sales.SalesDataItem;
import com.github.chen0040.magento.models.sales.SalesDataRefund;
import com.github.chen0040.magento.models.sales.SalesDataShipment;
import com.github.chen0040.magento.models.search.ConditionType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.shipment.ShipmentItem;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

public class MagentoOrderManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Orders = "rest/V1/order";
	
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
	
	public Order getOrder(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId;
		
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
	
	public List<OrderItem> getItems(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, OrderItem.class);
	}
	
	public String getStatus(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/statuses/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<SalesDataComment> getComments(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/comments/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, SalesDataComment.class);
	}
	
	public List<OrderItem> searchItems(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", OrderItem.class);
	}
	
	public List<Invoice> getInvoices(Order order) {
		SearchCriteria criteria = new SearchCriteria()
				.addFilter("order_id", order.getEntity_id().toString(), ConditionType.EQUAL);
		
		return client.invoice().searchInvoices(criteria);
	}
	
	public int countInvoicedItems(Order order) {
		List<Invoice> invoices = getInvoices(order);
		
		if (invoices == null) {
			return 0;
		}
		
		return invoices.stream()
				.map(_invoice -> {
					List<SalesDataItem> items = _invoice.getItems();
					
					return items.stream()
							.filter(_item -> {
								Product product = client.products().getProduct(_item.getSku());
								return product.getId() == _item.getProduct_id();
							})
							.collect(Collectors.toList())
							.size();
				})
				.reduce((x,y) -> x + y)
				.orElse(0);
	}
	
	public List<Shipment> getShipments(Order order) {
		SearchCriteria criteria = new SearchCriteria()
				.addFilter("order_id", order.getEntity_id().toString(), ConditionType.EQUAL);
		
		return client.shipment().searchShipments(criteria);
	}
	
	public int countShippedItems(Order order) {
		List<Shipment> shipment = getShipments(order);
		
		if (shipment == null) {
			return 0;
		}
		
		return shipment.stream()
				.map(_shipment -> {
					List<ShipmentItem> items = _shipment.getItems();
					
					return items.stream()
							.filter(_item -> {
								Product product = client.products().getProduct(_item.getSku());
								return product.getId() == _item.getProduct_id();
							})
							.collect(Collectors.toList())
							.size();
				})
				.reduce((x,y) -> x + y)
				.orElse(0);
	}
	
	public Boolean cancel(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/cancel";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean addComment(Integer orderId, SalesDataComment comment) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/comments";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("statusHistory", comment), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean emailToCustomer(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/emails";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean hold(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/hold";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean unhold(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/unhold";
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
	
	public SalesDataAddress assignAddress(String parent_id, SalesDataAddress address) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + parent_id;
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", address), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, SalesDataAddress.class);
	}
	
	public Integer createInvoice(Integer orderId, SalesDataInvoice invoice) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/invoice";
		
		String json = postSecure(uri, JSON.toJSONString(invoice), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer refund(Integer orderId, SalesDataRefund refundInfo) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/refund";
		
		String json = postSecure(uri, JSON.toJSONString(refundInfo), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer ship(Integer orderId, SalesDataShipment shipmentInfo) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/ship";
		
		String json = postSecure(uri, JSON.toJSONString(shipmentInfo), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
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
