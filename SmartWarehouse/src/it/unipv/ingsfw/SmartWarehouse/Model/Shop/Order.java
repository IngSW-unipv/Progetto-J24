package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;


public class Order implements IReturnable{
	private HashMap<InventoryItem, Integer> skuqty;
	private int id;
	private String email;
	private LocalDateTime date;
	
	public Order(HashMap<InventoryItem, Integer> skuqty, String email) {
		this.skuqty=new HashMap<InventoryItem, Integer>();
		this.skuqty.putAll(skuqty);
		this.email=email;
		date = LocalDateTime.now();
		
	}
	public Order(HashMap<InventoryItem, Integer> skuqty, int id, String email, LocalDateTime date) {
		this.skuqty=new HashMap<InventoryItem, Integer>();
		this.skuqty.putAll(skuqty);
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

	public LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getEmail() {
		return email;
	}
	
	public HashMap<InventoryItem, Integer> getMap(){
		return skuqty;
	}
	
	public double getTotal() {
		double tot=0;
		for(InventoryItem i: getSet()) {
			tot+=i.getPrice()*skuqty.get(i);
		}
		return tot;
	}
	
	public int getQtyOfItem(InventoryItem i) {
		return skuqty.get(i);
	}
	
	public HashSet<InventoryItem> getSet(){
		HashSet<InventoryItem> sq = new HashSet<InventoryItem>();
		skuqty.forEach((t, u) -> sq.add(t)); 
		return sq;
	}
	
	@Override
	public InventoryItem getItemBySku(String sku) {
		InventoryItem ret = null;
		for(InventoryItem i: getSet()) {
			if(i.getSku().equals(sku)) {
				ret=i;
				break;
			}
		}
		return ret;
	}
	@Override
	public int getQtyBySku(String sku) {
		return getQtyOfItem(getItemBySku(sku));
	}
	
	@Override 
	public String getDescBySku(String sku) {
		return getItemBySku(sku).getDescription();
	}
	
	
	@Override
	public String toString() {
		String s="";
		for(InventoryItem i: getSet()) {
			s+=getQtyBySku(i.getSku())+" "+i.getDescription()+", ";
		}
		s+=getDate();
		return s;
	}
	
	public int getQtyTotal() {
		int totalQty = 0;
		for (int qty : skuqty.values()) {
			totalQty += qty;
		}
		return totalQty;
	}
	//union of picking part
	public List<InventoryItem> takeInfoItem() {
        List<InventoryItem> items = new ArrayList<>();
        for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
            InventoryItem item = entry.getKey();
            items.add(item);
        }
        return items;
    }
	
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
	public int totaldimItem() {
		int totalitems=0;
		for (int quantity : skuqty.values()) {
			totalitems += quantity;
		}
		return totalitems;
	}
	public boolean tfFragility() {
		for (InventoryItem item : skuqty.keySet()) {
			if (item.getDetails().getFragility() > N) { 
		    return true;
		    }
		}
			return false;
	}
	
	public void setSkuqtyIntegerValue(InventoryItem item, int value) {
		skuqty.put(item, value);
	}
		
	
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
	//change data after packaged the order
	public void changeDate(Order o) {
		PackageStrategy s=new PackageStrategy();
		if (s.calculatePackageSizes(o)==true) {
			LocalDate now = LocalDate.now();
	        int intDate = (int) now.toEpochDay(); 
	        o.setDate(intDate);
	   }
	} 
	public void selectItemqty(InventoryItem item, int desiredQuantity) throws ItemNotFoundException, QuantityMismatchException {		   
		if (skuqty.containsKey(item)) {
		    int actualQuantity = skuqty.get(item); 
		    if (actualQuantity != desiredQuantity) {
		        System.out.println("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		        throw new QuantityMismatchException("Quantity mismatch for item " + item.getSku() + ". Actual quantity in order: " + actualQuantity);
		    }
		    // with this you can find th position of the element
		    	for (Map.Entry<InventoryItem, Integer> entry : skuqty.entrySet()) {
		    		if (entry.getKey().equals(item)) {
		    			System.out.println("Item found at position: ");
		    			item.getPos();
		    			System.out.println("SKU: " + entry.getKey().getSku()); 
		    		}
		    	}
		    } 	else {
		        	System.out.println("Item not in the order, your items are:");
		        	takeInfoItem();
		        	throw new ItemNotFoundException();
		    }
		}
	

}

}
