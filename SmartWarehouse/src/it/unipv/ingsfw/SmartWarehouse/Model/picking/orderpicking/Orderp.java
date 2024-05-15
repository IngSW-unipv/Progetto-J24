package it.unipv.ingsfw.SmartWarehouse.orderpicking;

import java.util.*;
import it.unipv.ingsfw.SmartWarehouse.database.*;
import it.unipv.ingsfw.SmartWarehouse.exception.*;
import it.unipv.ingsfw.SmartWarehouse.inventory.*;
import java.util.Map.Entry;
import it.unipv.ingsfw.SmartWarehouse.exception.*;
import java.time.*;
import it.unipv.ingsfw.SmartWarehouse.packagastrategy.*;

public class Orderp implements IPackageable{		
	private int id;
	private HashMap<InventoryItem,Integer> skuqty;
	private int date;
	private String email;
	public Order(HashMap<InventoryItem,Integer> skuqty) {
		this.skuqty=new HashMap<InventoryItem,Integer>();
		this.skuqty.putAll(skuqty);
	}
	public Orderp(HashMap<InventoryItem,Integer> skuqty,int id,String email,int date) {
		this.skuqty=skuqty;
		this.id=id;
		this.email=email;
		this.date=date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HashMap<InventoryItem, Integer> getSkuqty() {
		return skuqty;
	}
	public void setSkuqty(HashMap<InventoryItem, Integer> skuqty) {
		this.skuqty = skuqty;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void takeInfoItem() {
		for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
			 InventoryItem item = entry.getKey();
			 int quantity = entry.getValue();        
			 // Stampo le coordinate dell'item
			 item.toString();       
			}
	}
	public int calculateTotalSize() {
		int totalSize = 0;
		for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
			InventoryItem item = entry.getKey();
		    int quantity = entry.getValue();
		    int itemSize = item.getDim(); // Assuming getSize() returns the size of the item
		        totalSize += itemSize * quantity;
		    }
		return totalSize;
	}
	public int totalItem() {
		int totalitems=0;
		for (int quantity : skuqty.values()) {
			totalitems += quantity;
		}
		return totalitems;
	}
	public boolean tfFragility() {
		boolean fr;
		for (InventoryItem item : skuqty.keySet()) {
			if (item.getFr()==true) { // Assuming isFragile() returns true if the item is fragile
		    return true;
		    }
		}
			return false;
	}
	public void setSkuqtyIntegerValue(InventoryItem item, int value) {
		skuqty.put(item, value);
	}
		
	public Orderline selectOrder(Orderline o) {
		return o; //scelgo ordine in base alla linea del database
	}
	
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
	public void changeDate(Orderp o) {
		PackageStrategy s=new PackageStrategy();
		if (s.calculatePackageSizes(o)==true) {
			LocalDate now = LocalDate.now();
	        int intDate = (int) now.toEpochDay(); // Converto quando ho finito il calcolcolo della strategia 
	        o.setDate(intDate);
	   }
	} 
	public void selectItemqty(InventoryItem item, int desiredQuantity) throws ItemNotFoundException, QuantityMismatchException {		   
		if (skuqty.containsKey(item)) {
		    int actualQuantity = skuqty.get(item); // Ottieni la quantità effettiva dell'elemento nell'ordine
		    if (actualQuantity != desiredQuantity) {
		        System.out.println("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		        throw new QuantityMismatchException("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		    }
		    // Trova la posizione dell'elemento nell'ordine
		    	for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
		    		if (entry.getKey().equals(item)) {
		    			System.out.println("Item found at position: ");
		    			item.getPos();
		    			System.out.println("SKU: " + entry.getKey().getSku()); // Assuming there's a method getSku() in the Item class
		    		}
		    	}
		    } 	else {
		        	System.out.println("Item not in the order, your items are:");
		        	takeInfoItem();
		        	throw new ItemNotFoundException("Item not found in the order");
		    }
		}

	}

		





