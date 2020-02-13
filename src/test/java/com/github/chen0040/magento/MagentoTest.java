package com.github.chen0040.magento;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.github.chen0040.magento.models.category.Category;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.product.ProductAttribute;
import com.github.chen0040.magento.models.product.ProductAttributeSet;
import com.github.chen0040.magento.models.product.ProductExtensionAttributes;
import com.github.chen0040.magento.models.product.media.ProductImage;
import com.github.chen0040.magento.models.search.ConditionType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.store.StoreConfig;
import com.github.chen0040.magento.models.store.StoreGroup;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.models.store.Website;

public class MagentoTest {

	@Test
	public void testLogin() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		String token = client.loginAsAdmin("a.trucco", "zero11zero11");
		
		assertNotNull(token);
		
		token = client.loginAsAdmin("a", "z");
		assertNull(token);

		client = new MagentoClient("a");
		token = client.loginAsAdmin("a.trucco", "zero11zero11");
		assertNull(token);
		
		client = new MagentoClient("http://bsmagento2.web07.zero11.net");
		token = client.loginAsAdmin("a.trucco", "zero11zero11");
		assertNotNull(token);
		
		client = new MagentoClient("bsmagento2.web07.zero11.net");
		token = client.loginAsAdmin("a.trucco", "zero11zero11");
		assertNotNull(token);
	}
	
	@Test
	public void testProduct() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		Product test = new Product();
		test.setSku("new-test");
		test.setName("new-test");
		test.setAttribute_set_id(4);
		test.setExtension_attributes(new ProductExtensionAttributes()
				.setStock(10)
		);
		test = client.products().saveProduct(test);
		assertNotNull(test);
		
		test = client.products().updateProductAvailability("new-test", 5);
		assertNotNull(test);
		
		client.products().deleteProduct("new-test");
		assertNull(client.products().getProduct("new-test"));
		
		assertNotNull(client.products().searchProduct(new SearchCriteria().setPage(0, null)));
		
		assertNotNull(client.products().getProductAttributeSets());
		
		ProductAttributeSet set = new ProductAttributeSet().setAttribute_set_name("aaa").setSort_order(1);
		set = client.products().saveProductAttributeSet(set);
		assertNotNull(set);
		assertTrue(client.products().deleteProductAttributeSet(set.getAttribute_set_id()));
		
		ProductAttribute attr = new ProductAttribute()
				.setAttribute_code("aaa")
				.setEntity_type_id("4")
				.setIs_required(true)
				.setFrontend_input("text")
				.setDefault_frontend_label("azm");
		attr = client.products().saveAttribute(attr);
		assertNotNull(attr);
		
		assertTrue(client.products().deleteProductAttribute(attr.getAttribute_code()));
	}
	@Test
	public void testAttributes() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		assertNotNull(client.products().saveAttribute(new ProductAttribute()
				.setAttribute_code("test")
				.setEntity_type_id("4")
				.setFrontend_input("multiselect")
				.setDefault_frontend_label("test")
				.setIs_required(false)
				.setIs_user_defined(true)
		));
		client.products().addOptionToAttribute("test", "brown");
		System.out.println(client.products().getProductAttributeOptions("test"));
		assertTrue(client.products().deleteProductAttribute("test"));
		
		List<String> rewixAttributeNames = Arrays.asList(
				//"availability", --> stock qty
				"brand",
				//"code", --> sku
				"country_selling_restrictions",
				"currency",
				"description", // descriptions handled by store views
				"id",    // actually "id"
				"intra",
				// "models",  --> handled by Magento configurable product
				//"pictures", --> handled by Magento media
				"tags",
				"taxable",
				//"street_price", --> price
				"suggested_price",
				// Model-specific attributes
				"barcode",
				"color", // actually "color"
				"size"   // actually "size
		);
		
		try {
			client.products().deleteProductAttributeSet(
					client.products().getProductAttributeSets().stream()
					.filter(set -> set.getAttribute_set_name().equals("Rewix (brandsdistribution)"))
					.collect(Collectors.toList())
					.get(0).getAttribute_set_id()
			);
		}
		catch (IndexOutOfBoundsException exception) {
			;
		}
		
		for (String attr : rewixAttributeNames) {
			client.products().deleteProductAttribute("rewix_" + attr);
		}
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
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		assertNotNull(client.shipment().search(new SearchCriteria().setPage(0, 1000)));
		List<Shipment> shipments = client.shipment().search(new SearchCriteria().addFilterGroup("order_id", "2", ConditionType.GREATER_THAN_OR_EQUAL));
		assertNotNull(shipments);
		assertNotNull(client.shipment().saveShipment(shipments.get(0)));
		assertNotNull(client.shipment().saveTrack(shipments.get(0).getTracks().get(0)));
	}
	
	@Test
	public void testOrder() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");

		assertNotNull(client.order().getOrder(1));
		assertNotNull(client.order().searchItems(new SearchCriteria().setPage(0, 1000)));
		assertNotNull(client.order().searchItems(new SearchCriteria().addFilterGroup("name", "ESTER", ConditionType.LIKE)));
		assertNotNull(client.order().searchOrders(new SearchCriteria().setPage(0, 1000)));
		assertNotNull(client.order().searchOrders(new SearchCriteria().addFilterGroup("name", "ESTER", ConditionType.LIKE)));
	}
	
	@Test
	public void testOAuth() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		
		client.setOAuth(
				"7dqcbluq2i4oq4ompesiyjd9a4eog6ms",
				"uc38k78qnghjupv0vietqf52ueb25wo0",
				"2yi0gc2xlm9wo6tpy8amx79hy032aj8h",
				"adff0m430etk83cvbup7iijr7viu7wa0"
		);
		assertNotNull(client.oAuth());
		
		client.setOAuth(
				"7dqcbluq2i4oq4ompesiyjd9a4eog6ms",
				"uc38k78qnghjupv0vietqf52ueb25wo1",
				"2yi0gc2xlm9wo6tpy8amx79hy032aj8h",
				"adff0m430etk83cvbup7iijr7viu7wa0"
		);
		assertNull(client.oAuth());
	}
	
	@Test
	public void testImage() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		Integer id = client.products().media().uploadImage("BYRON_BROWN", "/Users/arseniotrucco/byron.jpg", "byron");
		assertNotNull(id);
		client.products().media().deleteProductImage("BYRON_BROWN", id);
		
		id = client.products().media().uploadImageFromURL("BYRON_BROWN", "https://www.fashionclothes.it/wp-content/uploads/2018/11/stock_product_image_83682_319861228.jpg", "byron");
		assertNotNull(id);
		client.products().media().deleteProductImage("BYRON_BROWN", id);
		
		List<ProductImage> images = client.products().media().getProductImages("BYRON_BROWN");
		for (ProductImage image : images) {
			client.products().media().deleteProductImage("BYRON_BROWN", image.getId());
		}
	}
	
	@Test
	public void testCategory() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		Category category = client.categories().addCategory(
				new Category()
				.setName("test")
				.setIs_active(true)
		);
		assertNotNull(category);
		
		category = client.categories().addCategory(
				new Category()
				.setName("test")
				.setIs_active(true)
		);
		assertNotNull(category);
		
		client.switchStoreView("it");
		category = client.categories().addCategory(
				new Category()
				.setName("test-it")
				.setIs_active(true)
		);
		assertNotNull(category);
		assertTrue(client.categories().deleteCategory(category.getId()));
		
		client.switchStoreViewToDefault();
		category = client.categories().getCategory("test");
		assertTrue(client.categories().deleteCategory(category.getId()));
	}
}
