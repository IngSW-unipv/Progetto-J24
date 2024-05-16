package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.HashMap;

import it.unipv.ingsfw.SmartWarehouse.Controller.ReturnController;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Item;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnView;

public class MainClassForTest {

	public static void main(String[] args) {
		Client client1= new Client("John", "Doe", "john.doe@example.com", "123 Main St, Anytown, USA", "password123");

		Position pos1=new Position("linea1","pod1","bin1");
		ItemDetails itemDetails1=new ItemDetails(1, 1);
		Item item1=new Item("Smartphone",itemDetails1);
		InventoryItem inventoryItem1=new InventoryItem(item1,"SKU001",599.99,2,1000,pos1);

		HashMap<InventoryItem, Integer> skuqty=new HashMap<InventoryItem, Integer>();
		skuqty.put(inventoryItem1, 5);
		Order order1_client1=new Order(skuqty,client1.getEmail());
		ReturnView rv= new ReturnView(client1);
		ReturnFACADE rf=new ReturnFACADE(order1_client1);
		ReturnController rc=new ReturnController(rf,rv);
	}
}
