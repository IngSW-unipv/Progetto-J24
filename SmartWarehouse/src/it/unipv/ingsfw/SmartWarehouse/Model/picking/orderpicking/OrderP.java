package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchItemException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;

public class OrderP extends Order {
	private int countItemInserted=0;

	public static final int N = 3;
	private Map<String, Integer> qtyInsertedMap;
	private HashMap<IInventoryItem, Integer> skuqty;


	public OrderP(HashMap<IInventoryItem, Integer> skuqty, int id, String email, LocalDateTime date) {
		super(skuqty, id, email, date);
		this.skuqty = super.getSkuqty();
		this.qtyInsertedMap = new HashMap<>();
	}
	/*
	 * method used for calculate the total size of the pack, I use a for loop the inventors of the 
	 * article and from there using the get method I find the quantity
	 */
	public int calculateTotalSize() {
	    int totalSize = 0;
	    for (IInventoryItem item : skuqty.keySet()) {
	        int countTotalItem = skuqty.get(item);
	        if (item.getDetails() == null) {
	            throw new IllegalArgumentException("Item details are not set for SKU: " + item.getSku());
	        }
	        int itemSize = item.getDetails().getDimension();
	        totalSize += itemSize * countTotalItem;
	    }
	    return totalSize;
	}
	/**
	 * this method is for trasforming the orderp hasmap into a linkedlist of InventoryItems.
	 * using a for loop.
	 */
	public LinkedList<IInventoryItem> getSkuqtyAsList() {
		LinkedList<IInventoryItem> itemList = new LinkedList<>();
		for (IInventoryItem item: skuqty.keySet()) {
			int quantity = skuqty.get(item);
			for (int i = 0; i < quantity; i++) {
				itemList.add(item);
			}
		}
		return itemList;
	}
	/*
	 * with this method you can take the details of items in the order sorted by
	 * position.
	 */
	public List<String> getItemDetails() {
	    List<IInventoryItem> sortedItems = new ArrayList<>(skuqty.keySet());
	    sortedItems.sort(new Comparator<IInventoryItem>() {
	        @Override
	        public int compare(IInventoryItem item1, IInventoryItem item2) {
	            Position pos1 = item1.getPos();
	            Position pos2 = item2.getPos();
	            
	            int lineComparison = pos1.getLine().compareTo(pos2.getLine());
	            if (lineComparison != 0) 
	                return lineComparison;
	            
	            int podComparison = pos1.getPod().compareTo(pos2.getPod());
	            if (podComparison != 0) 
	                return podComparison;
	            
	            return pos1.getBin().compareTo(pos2.getBin());
	        }
	    });
	    List<String> itemDetailsList = new ArrayList<>();
	    for (IInventoryItem item : sortedItems) {
	        int quantity = skuqty.get(item);
	        if (item == null || item.getSku() == null) {
	            throw new IllegalArgumentException("Item or SKU is null in the inventory.");
	        }

	        String itemDetails = "SKU: " + item.getSku() + ", Pos: " + item.getPos().toString() + ", Quantity: "
	                + quantity + "\n";
	        itemDetailsList.add(itemDetails);
	    }
	    return itemDetailsList;
	}
	
	public boolean checkFragility(List<IInventoryItem> pack) {
	        for (IInventoryItem item : pack) {
	            if (item.getDetails().getFragility() > N) {
	                return true;
	            }
	        }
	        return false;
	    }
	/**
	 * method for see if i select the correct item and the correct qty
	 */
	public int selectItemqty(String sku, int qty) throws ItemNotFoundException, QuantityMismatchItemException {
		boolean itemFound = false;
		int actualQuantity = 0;
		for (IInventoryItem item : skuqty.keySet()) {
			if (item.getSku().equals(sku)) {
				itemFound = true;
				actualQuantity = skuqty.get(item);
				break;
			}
		}
		if (!itemFound) 
			throw new ItemNotFoundException("Item not found for SKU: " + sku);
		if(qtyInsertedMap.containsKey(sku))
			actualQuantity = qtyInsertedMap.get(sku);
		if (qty > actualQuantity)
			throw new QuantityMismatchItemException(
					"Too many items of SKU " + sku + " inserted. Please remove " + (qty - actualQuantity) + " items.");
		if (qty <= 0)
			throw new QuantityMismatchItemException(
					"Negative or zero quantity " + sku + " inserted. Please insert a valid quantity.");
		countItemInserted=countItemInserted+qty;
		int targetQuantity = actualQuantity - qty;
		while (actualQuantity > targetQuantity)
			actualQuantity--;
		qtyInsertedMap.put(sku, actualQuantity);
		return qty;
	}
	/*  method for checking if all the items are in the respective package
  	 * check if count item is the same of the total of the package
  	 */
  	public boolean allItemPresent() throws QuantityMismatchItemException {
  		if (countItemInserted==getTotalItem()) 
  			return true;
  		else 
  			throw new QuantityMismatchItemException("not all the items are in the package");
  	}
	
  	public int getTotalItem() {
	    int countTotalItem=0;
	    for (IInventoryItem item : skuqty.keySet()) {
	        countTotalItem+= skuqty.get(item);   
	    }
 	    return countTotalItem;
  }
}
