package it.unipv.ingsfw.SmartWarehouse;

import java.util.HashMap;

import it.unipv.ingsfw.SmartWarehouse.Controller.InventoryController;
import it.unipv.ingsfw.SmartWarehouse.Controller.ReturnController;
import it.unipv.ingsfw.SmartWarehouse.Controller.ReturnableOrdersController;
import it.unipv.ingsfw.SmartWarehouse.Controller.ShopController;
import it.unipv.ingsfw.SmartWarehouse.Controller.SupplyController;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnFACADE;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Shop;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyManager;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnableOrdersView;
import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.InventoryView;

public class MainClassForTest {
	
	public MainClassForTest() {
		System.out.println("QUESTO è il main");
	}
	
	public static void main(String[] args) {
		/*
		new MainClassForTest();
		Client client1= new Client("John", "Doe", "john.doe@example.com", "123 Main St, Anytown, USA", "password1");
		Client client2= new Client("Jane", "Smith", "jane.smith@example.com", "123 Main St, Anytown, USA", "letmein");
		
		Position pos1=new Position("linea1","pod1","bin1");
		ItemDetails itemDetails1=new ItemDetails(1,1,Category.ELECTRONICS);
		//Item item1=new Item("Smartphone",itemDetails1);
		InventoryItem inventoryItem1=new InventoryItem("Smartphone",itemDetails1,"SKU001",500,2,1000,pos1);
		
		Position pos2=new Position("linea2","pod2","bin2");
		ItemDetails itemDetails2=new ItemDetails(0, 0,Category.ELECTRONICS);
		//Item item2=new Item("Laptop",itemDetails2);
		InventoryItem inventoryItem2=new InventoryItem("Laptop",itemDetails2,"SKU002",1000,2,1000,pos2);

		HashMap<InventoryItem, Integer> skuqty=new HashMap<InventoryItem, Integer>();
		skuqty.put(inventoryItem1, 5);
		skuqty.put(inventoryItem2, 2);
		
		ShopController shc=new ShopController(new Shop(client2), new ShopFrame());
		*/
		/*
		 * ReturnableOrdersView rov= new ReturnableOrdersView(client1);
		 * ReturnableOrdersController roc=new ReturnableOrdersController(rov);
		 */
		
		InventoryView iv=new InventoryView();
		InventoryManager w=new InventoryManager();
		InventoryController ic=new InventoryController(w, iv);
		SupplyManager sm=new SupplyManager();
		SupplyController sc=new SupplyController(sm, iv.getSupplyPanel());
    }

}
