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
		this.skuqty = super.getSkuqty();
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
	 *
	 * method to transform the order from a hashmap to a list
	 */
	
	public List<InventoryItem> getSkuqtyAsList() {
		List<InventoryItem> itemList = new ArrayList<>();    
		for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
			InventoryItem item = entry.getKey();
		    int quantity = entry.getValue();
	        for (int i = 0; i < quantity; i++) {
	             itemList.add(item);
	        }
		}    
		return itemList;
	}
	/*
	 *method for show the items and some details in the order
	 */
	public List<String> getItemDetails() {
	    List<String> itemDetailsList = new ArrayList<>();
	    for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
	        InventoryItem item = entry.getKey();
	        int quantity = entry.getValue();
	        String itemDetails = "SKU: " + item.getSku() + "," + item.getPos() + ", Quantità: " + quantity+"\n";
	        itemDetailsList.add(itemDetails);
	    }
	    return itemDetailsList;
	}
	/*
	 * method for select a item and a quantity
	 */
	public void selectItemqty(String sku, int qty) throws ItemNotFoundException, QuantityMismatchException {
	    boolean itemFound = false;
	    for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
	        if (entry.getKey().getSku().equals(sku)) {
	            itemFound = true;
	            int actualQuantity = entry.getValue();
	            if (actualQuantity != qty) {
	                throw new QuantityMismatchException("Quantity mismatch for item " + sku + ". Actual quantity in order: " + actualQuantity);
	            }
	        
	        }
	    }

	    if (!itemFound) {
	        throw new ItemNotFoundException("Item not found for SKU: " + sku);
	    }
	}
	 /*
     * method for stamp the spuLabel
     */
	public String getSpuLabel() {
		String result = "Email: ".concat(this.getEmail()).concat(", ")
				.concat("ID: ").concat(String.valueOf(this.getId())).concat(", ");
		    return result;
	}

	
	    
	
}
