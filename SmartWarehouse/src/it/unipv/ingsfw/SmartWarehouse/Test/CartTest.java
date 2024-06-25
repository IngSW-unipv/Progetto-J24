package it.unipv.ingsfw.SmartWarehouse.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Cart;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class CartTest {
	private Position pos1=new Position("G", "A", "A");
	private ItemDetails itemDetails1=new ItemDetails(1,1,Category.ELECTRONICS);
	private ItemDetails itemDetails2=new ItemDetails(1,1,Category.GROCERIES);
	private IInventoryItem inventoryItem1=new InventoryItem("computer",itemDetails1,"SKU001",10,92,10,pos1);
	private IInventoryItem inventoryItem2=new InventoryItem("zaino",itemDetails2,"@Kls54",10,92,10,pos1);

	private Cart ca;
	private Client cli;
	
	@Before
	public void initCart() {
		ca = new Cart();
		cli = new Client("n1", "s1", "e1", "p1", false, 0);
	}
	
	@Test
	public void testEmptyCart() {
		
		assertThrows(EmptyKartExceptio.class, () ->{
			ca.PayAndOrder(cli);
		});
	}
	
	@Test
	public void testAddZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			ca.add(inventoryItem1, 0);
		});
	}
	
	@Test
	public void testAddNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			ca.add(inventoryItem1, -1);
		});
	}
	
	@Test
	public void testOrderTooManyItems() {
		ca.add(inventoryItem1, 100);
		assertThrows(IllegalArgumentException.class, () ->{
			ca.PayAndOrder(cli);
		});
	}
	
	@Test
	public void testOrderNoteExistingItem() {
		ca.add(inventoryItem2, 1);
		assertThrows(ItemNotFoundException.class, () ->{
			ca.PayAndOrder(cli);
		});
	}
}
