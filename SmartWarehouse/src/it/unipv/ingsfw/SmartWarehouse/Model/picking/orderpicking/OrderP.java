package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public class OrderP extends Order {
	private HashMap<InventoryItem, Integer> skuqty;
	public OrderP(HashMap<InventoryItem, Integer> skuqty, int id, String email, LocalDateTime date) {
		super(skuqty, id, email, date);
		skuqty = super.getSkuqty();
	}
	/*
	 * method for calculate the total dimension of the order
	 */
	public int calculateTotalSize() { 
		int totalSize = 0;
		for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
			InventoryItem item = entry.getKey();
		    int quantity = entry.getValue();
		    int itemSize = item.getDetails().getDimension(); 
		        totalSize += itemSize * quantity;
		    }
		return totalSize;
	}
	/*
	 * method to transform the order from a hashmap to a list
	 */
	
	public List<InventoryItem> getSkuqtyAsList() {
		List<InventoryItem> itemList = new ArrayList<>();    
		for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
			InventoryItem item = entry.getKey();
		    int quantity = entry.getValue();
		    // Aggiungi l'elemento ripetutamente per il numero di volte specificato nella mappa
	        for (int i = 0; i < quantity; i++) {
	             itemList.add(item);
	        }
		}    
		return itemList;
	}
	
	
	public void selectItemqty(InventoryItem item, int desiredQuantity) throws ItemNotFoundException, QuantityMismatchException {		   
		if (skuqty.containsKey(item)) {
		    int actualQuantity = skuqty.get(item); // Ottieni la quantità effettiva dell'elemento nell'ordine
		    if (actualQuantity != desiredQuantity) {
		        System.out.println("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		        throw new QuantityMismatchException("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		    }
		    	for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
		    		if (entry.getKey().equals(item)) {
		    			System.out.println("Item found at position: ");
		    			item.getPos();
		    			System.out.println("SKU: " + entry.getKey().getSku()); // Assuming there's a method getSku() in the Item class
		    		}
		    	}
		    } 	else {
		        	System.out.println("Item not in the order, your items are:");
		        	toString();
		        	throw new ItemNotFoundException();
		    }
		}
}
