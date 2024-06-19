package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;


public class OrderP extends Order {
	private int countpack=0;
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
        for (InventoryItem item : skuqty.keySet()) {
            if (item.getSku().equals(sku)) {
                itemFound = true;
                actualQuantity = skuqty.get(item); 
                break;
            }
        }
        if (!itemFound) {
            throw new ItemNotFoundException("Item not found for SKU: " + sku);
        }
        if (actualQuantity != qty) {
            if (qty > actualQuantity) {
                throw new QuantityMismatchException("Too many items of SKU " + sku + " inserted. Please remove " + (qty - actualQuantity) + " items.");
            } 
            if (qty <= 0 ) {
                throw new QuantityMismatchException("negative or null quantity " + sku + " inserted. Please insert a valide items.");
            } 
        }
    }	
	
	 /*
     * method for stamp the spuLabel
     */
	

	
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
	
	public boolean addAndComparePackageSize(String packageType, int quantity, int fr) throws WrongPackageException {
	    IPackageStrategy strategy = new PackageStrategyFactory().getPackageStrategy(this);
	    strategy.calculatePackages();
	    if (strategy.isPackageCorrect(packageType, quantity, fr)) {
	        countpack += quantity; 
	        System.out.println("Correct package size and fragility.");
	        System.out.println("Current countpack: " + countpack);
	        return true;
	    } else {
	        System.out.println("Incorrect package size or fragility.");
	        throw new WrongPackageException("Wrong package size or fragility.");
	    }
	}


    public boolean allPackPresent(int countp) {
        IPackageStrategy strategy = new PackageStrategyFactory().getPackageStrategy(this);
        System.out.println("Strategy count: " + strategy.getCountPack() + " == Current countpack: " + countpack);
        if (strategy.getCountPack() == countp) {
            System.out.println("All packs are present.");
            return true;
        }
        System.out.println("Not all packs are present.");
        return false;
    }


	public boolean isOrderFragile() {
		IPackageStrategy strategy = new PackageStrategyFactory().getPackageStrategy(this);
	    strategy.calculatePackages();
	    return strategy.isPackageFragile(this.getSkuqtyAsList());
	}

	

	public int getCountp() {
		return countpack;
	}
	public void setCountp(int n) {
		this.countpack=n;
	}
}




	
