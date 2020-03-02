package com.github.chen0040.magento;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import com.github.chen0040.magento.models.category.Category;
import com.github.chen0040.magento.models.invoice.Invoice;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.product.Product;
import com.github.chen0040.magento.models.product.ProductAttribute;
import com.github.chen0040.magento.models.product.ProductAttributeSet;
import com.github.chen0040.magento.models.product.media.ProductImage;
import com.github.chen0040.magento.models.search.ConditionType;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.models.shipment.Shipment;
import com.github.chen0040.magento.models.store.StoreConfig;
import com.github.chen0040.magento.models.store.StoreGroup;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.models.store.Website;
import com.github.chen0040.magento.services.MagentoProductManager.AttributeOptionPOSTMode;

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
		test.setExtension_attributes(new Product.ExtensionAttributes()
				.setStock(10)
		);
		test = client.products().saveProduct(test);
		assertNotNull(test);
		
		test = client.products().updateProductAvailability("new-test", 5);
		assertNotNull(test);
		
		client.products().deleteProduct("new-test");
		assertNull(client.products().getProduct("new-test"));
		
		assertNotNull(client.products().searchProduct(new SearchCriteria().setPage(0, null)));
		
		assertNotNull(client.products().getAttributeSets());
		
		ProductAttributeSet set = new ProductAttributeSet().setAttribute_set_name("aaa").setSort_order(1);
		set = client.products().saveAttributeSet(set);
		assertNotNull(set);
		assertTrue(client.products().deleteAttributeSet(set.getAttribute_set_id()));
		
		ProductAttribute attr = new ProductAttribute()
				.setAttribute_code("aaa")
				.setEntity_type_id("4")
				.setIs_required(true)
				.setFrontend_input("text")
				.setDefault_frontend_label("azm");
		attr = client.products().saveAttribute(attr);
		assertNotNull(attr);
		
		assertTrue(client.products().deleteAttribute(attr.getAttribute_code()));
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
		client.products().addOptionToAttribute("test", AttributeOptionPOSTMode.NO_DUPLICATE_LABELS, "brown");
		System.out.println(client.products().getAttributeOptions("test"));
		assertTrue(client.products().deleteAttribute("test"));
		
		ProductAttributeSet set = client.products().saveAttributeSet(new ProductAttributeSet()
				.setAttribute_set_name("test"));
		assertNotNull(set);
		assertNotNull(client.products().deleteAttributeSet(set.getAttribute_set_id()));
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
		
		assertNotNull(client.shipment().searchShipments(new SearchCriteria().setPage(0, 1000)));
		List<Shipment> shipments = client.shipment().searchShipments(new SearchCriteria().addFilter("order_id", "2", ConditionType.GREATER_THAN_OR_EQUAL));
		assertNotNull(shipments);
		Shipment shipment = shipments.get(0);
		assertNotNull(client.shipment().getParentOrder(shipment));
		Invoice invoice = client.shipment().getCorrespondingInvoice(shipment);
		assertNotNull(invoice);
		assertNotNull(client.invoice().getCorrespondingShipment(invoice));
	}
	
	@Test
	public void testOrder() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net");
		client.loginAsAdmin("a.trucco", "zero11zero11");

		assertNotNull(client.order().getOrder(1));
		SearchCriteria criteria = new SearchCriteria()
				.addORFilter("status", Order.STATUS.PENDING, ConditionType.LIKE)
				.addORFilter("status", Order.STATUS.COMPLETE, ConditionType.LIKE)
				.addANDFilter("status", Order.STATUS.CLOSED, ConditionType.LIKE);
		assertNotNull(client.order().searchOrders(criteria));
		assertNotNull(client.order().searchItems(new SearchCriteria().setPage(0, 1000)));
	}
	
	@Test
	public void testOAuth() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		
		client.setOAuth(
				"idml26ffz6e8rm2w6ell2k53ahcl1ot4",
				"7fsx8t8bb5av0tslkezzpgu1yw9emkkk",
				"23fd2q2co8i2wnd8l9z6v7fyqvmt47c1",
				"dto1qt879u40l0csn8hyan8rlauc0vpf"
		);
		assertNotNull(client.oAuth());
		
		client.setOAuth(
				"idml26ffz6e8rm2w6ell2k53ahcl1ot4",
				"7fsx8t8bb5av0tslkezzpgu1yw9emkkk",
				"23fd2q2co8i2wnd8l9z6v7fyqvmt47c1",
				"dto1qt879u40l0csn8hyan8rlauc0vpd" // different
		);
		assertNull(client.oAuth());
	}
	
	@Test
	public void testImage() {
		MagentoClient client = new MagentoClient("https://bsmagento2.web07.zero11.net/");
		client.loginAsAdmin("a.trucco", "zero11zero11");
		
		client.products().saveProduct(new Product().setSku("test").setName("test").setAttribute_set_id(4));
		
		Integer id = client.products().media().uploadImage("test", "/Users/arseniotrucco/byron.jpg", "byron");
		assertNotNull(id);
		client.products().media().deleteProductImage("test", id);
		
		id = client.products().media().uploadImageFromURL("test", "https://www.fashionclothes.it/wp-content/uploads/2018/11/stock_product_image_83682_319861228.jpg", "byron");
		assertNotNull(id);
		client.products().media().deleteProductImage("test", id);
		
		List<ProductImage> images = client.products().media().getProductImages("test");
		for (ProductImage image : images) {
			client.products().media().deleteProductImage("test", image.getId());
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
