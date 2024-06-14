package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.MultiplePackStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.SinglePackStrategy;

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
	    // Sort items by their position details: line, pod, bin
	    List<Map.Entry<InventoryItem, Integer>> sortedEntries = new ArrayList<>(skuqty.entrySet());
	    
	    sortedEntries.sort(new Comparator<Map.Entry<InventoryItem, Integer>>() {
	        @Override
	        public int compare(Map.Entry<InventoryItem, Integer> entry1, Map.Entry<InventoryItem, Integer> entry2) {
	            Position pos1 = entry1.getKey().getPos();
	            Position pos2 = entry2.getKey().getPos();
	            int lineComparison = pos1.getLine().compareTo(pos2.getLine());
	            if (lineComparison != 0) {
	                return lineComparison;
	            }
	            int podComparison = pos1.getPod().compareTo(pos2.getPod());
	            if (podComparison != 0) {
	                return podComparison;
	            }
	            return pos1.getBin().compareTo(pos2.getBin());
	        }
	    });

	    List<String> itemDetailsList = new ArrayList<>();
	    for (Map.Entry<InventoryItem, Integer> entry : sortedEntries) {
	        InventoryItem item = entry.getKey();
	        int quantity = entry.getValue();
	        String itemDetails = "SKU: " + item.getSku() + ", Pos: " + item.getPos().toString() + ", Quantity: " + quantity + "\n";
	        itemDetailsList.add(itemDetails);
	    }
	    return itemDetailsList;
	}
	/*
	 * method for select a item and a quantity
	 */
	public void selectItemqty(String sku, int qty) throws ItemNotFoundException, QuantityMismatchException {
        boolean itemFound = false;
        int actualQuantity = 0;

        // Itera sugli elementi della mappa invece che sugli entry
        for (InventoryItem item : skuqty.keySet()) {
            if (item.getSku().equals(sku)) {
                itemFound = true;
                actualQuantity = skuqty.get(item); // Ottieni la quantità associata all'item trovato
                break;
            }
        }
        if (!itemFound) {
            throw new ItemNotFoundException("Item not found for SKU: " + sku);
        }
        if (actualQuantity != qty) {
            if (qty > actualQuantity) {
                throw new QuantityMismatchException("Too many items of SKU " + sku + " inserted. Please remove " + (qty - actualQuantity) + " items.");
            } else {
                throw new QuantityMismatchException("Not enough items of SKU " + sku + " inserted. Please add " + (actualQuantity - qty) + " more items.");
            }
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
	
	public IPackageStrategy calculatePack() {
		return new PackageStrategyFactory().getPackageStrategy(this);
	}

	public HashMap<String, Integer> getItemMap() {
	    HashMap<String, Integer> itemMap = new HashMap<>();
	    for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
	        itemMap.put(entry.getKey().getSku(), entry.getValue());
	    }
	    return itemMap;
	}
	
	/*public String addAndComparePackageSize(String packageType, int quantity) {
        IPackageStrategy strategy = calculatePack();
        if (strategy instanceof SinglePackStrategy) {
            SinglePackStrategy singlePackStrategy = (SinglePackStrategy) strategy;
            singlePackStrategy.calculatePackages(); 
            if (singlePackStrategy.isPackageCorrect(packageType, quantity)) {
                return "Correct number of " + packageType + " packages calculated.";
            } else {
                return "Incorrect number of " + packageType + " packages calculated.";
            }
        } else if (strategy instanceof MultiplePackStrategy) {
            MultiplePackStrategy multiplePackStrategy = (MultiplePackStrategy) strategy;
            multiplePackStrategy.calculatePackages(); 
            if (multiplePackStrategy.isPackageCorrect(packageType, quantity)) {
                return "Correct number of " + packageType + " packages calculated.";
            } else {
                return "Incorrect number of " + packageType + " packages calculated.";
            }
        } else {
            return "Unsupported package strategy: " + strategy.getClass().getSimpleName();
        }
    }*/
    }




	
