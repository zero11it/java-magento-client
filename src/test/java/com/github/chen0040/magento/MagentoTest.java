package com.github.chen0040.magento;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.chen0040.magento.enums.ConditionTypes;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.shipment.ShipmentTrack;
import com.github.chen0040.magento.models.store.StoreConfig;
import com.github.chen0040.magento.models.store.StoreGroup;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.models.store.Website;

public class MagentoTest {

	@Test
	public void testProduct() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		Product test = new Product();
		test.setSku("new-test");
		test.setName("new-test");
		test.setAttribute_set_id(4);
		client.products().saveProduct(test);
		
		System.out.println("\n");
		System.out.println(client.products().getProductBySku("new-test").getName());
		
		client.products().deleteProduct("new-test");
		assertNull(client.products().getProductBySku("new-test"));
	}
	
	@Test
	public void testStore() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		List<StoreConfig> configs = client.store().getStoreConfigs();
		List<Website> websites = client.store().getWebsites();
		List<StoreGroup> groups = client.store().getStoreGroups();
		List<StoreView> views = client.store().getStoreViews();
		List<String> codes = new ArrayList<String>();

		System.out.println("\n");


		for (StoreConfig config : configs) {
			codes.add(config.getCode());
		}
		System.out.println(codes.toString());
		
		codes.clear();
		for (Website website : websites) {
			codes.add(website.getCode());
		}
		System.out.println(codes.toString());
		
		codes.clear();
		for (StoreGroup group : groups) {
			codes.add(group.getCode());
		}
		System.out.println(codes.toString());
		
		codes.clear();
		for (StoreView view : views) {
			codes.add(view.getCode());
		}
		System.out.println(codes.toString());
	}
	
	@Test
	public void testShipment() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		assertNotNull(client.shipment().search(0, 10000));
		List<Shipment> shipments = client.shipment().search("order_id", "2", ConditionTypes.GTEQ);
		assertNotNull(shipments);
		assertNotNull(client.shipment().saveShipment(shipments.get(0)));
		assertNotNull(client.shipment().saveTrack(shipments.get(0).getTracks().get(0)));
	}
}
