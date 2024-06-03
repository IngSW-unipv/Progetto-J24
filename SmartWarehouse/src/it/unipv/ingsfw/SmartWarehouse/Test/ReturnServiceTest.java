package it.unipv.ingsfw.SmartWarehouse.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;

/*
 * ATTENTION! when you run the test, you try to return an order. When you run the test is always today
 */

public class ReturnServiceTest {
	
	private Position pos1=new Position("linea1","pod1","bin1");
	private ItemDetails itemDetails1=new ItemDetails(1,1,Category.ELECTRONICS);
	private InventoryItem inventoryItem1=new InventoryItem("Smartphone",itemDetails1,"SKU001",500,2,1000,pos1);
	private HashMap<InventoryItem, Integer> skuqty=new HashMap<InventoryItem, Integer>();

	
	/* it doesn't work
	@BeforeEach
	public void initTest() {
		pos1=new Position("linea1","pod1","bin1");
		itemDetails1=new ItemDetails(1,1,Category.ELECTRONICS);
		inventoryItem1=new InventoryItem("Smartphone",itemDetails1,"SKU001",500,2,1000,pos1);
		skuqty=new HashMap<InventoryItem, Integer>();
		skuqty.put(inventoryItem1, 5);
		System.out.println("CIAOOO");
	}
	*/
	
	
	
	/*
	 * Order date: today-->ok
	 */
	@Test
	public void testReturnServiceOk1() throws UnableToReturnException {
		skuqty.put(inventoryItem1, 5);
		IReturnable returnableOrder=new Order(skuqty,"Email"); 
		assertDoesNotThrow(() ->new ReturnService(returnableOrder));
	}
	
	/*
	 * Order date: yesterday-->ok
	 */
	@Test
	public void testReturnServiceOk2() throws UnableToReturnException {
		skuqty.put(inventoryItem1, 5);
		IReturnable returnableOrder=new Order(skuqty,"Email");
		returnableOrder.setDate(LocalDateTime.now().minusDays(1));
		assertDoesNotThrow(() ->new ReturnService(returnableOrder));
	}
	
	/*
	 * Order date: 30 days ago: limit but ok
	 */
	@Test
	public void testReturnServiceOk3() throws UnableToReturnException {
		skuqty.put(inventoryItem1, 5);
		IReturnable returnableOrder=new Order(skuqty,"Email");
		returnableOrder.setDate(LocalDateTime.now().minusDays(30));
		assertDoesNotThrow(() ->new ReturnService(returnableOrder));
	}
	
	/*
	 * Order date: 31 days ago: limit-->not ok
	 */
	@Test
	public void testReturnServiceNotOk1() throws UnableToReturnException {
		skuqty.put(inventoryItem1, 5);
		IReturnable returnableOrder=new Order(skuqty,"Email");
		returnableOrder.setDate(LocalDateTime.now().minusDays(31));	
		assertThrows(UnableToReturnException.class, () -> {
			new ReturnService(returnableOrder);
		});
	}
	
	/*
	 * Order date: 60 days ago: not ok
	 */
	@Test
	public void testReturnServiceNotOk2() throws UnableToReturnException {
		skuqty.put(inventoryItem1, 5);
		IReturnable returnableOrder=new Order(skuqty,"Email");
		returnableOrder.setDate(LocalDateTime.now().minusDays(60));	
		assertThrows(UnableToReturnException.class, () -> {
			new ReturnService(returnableOrder);
		});
	}

	
	
}