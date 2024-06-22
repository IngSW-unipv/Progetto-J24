


package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;


public class Order implements IReturnable{
	private HashMap<IInventoryItem, Integer> skuqty;
	private int id;
	private String email;
	private LocalDateTime date;
	
	public Order(HashMap<IInventoryItem, Integer> skuqty, String email) {
		this.skuqty=new HashMap<IInventoryItem, Integer>();
		this.skuqty.putAll(skuqty);
		this.email=email;
		date = LocalDateTime.now();
		
	}
	public Order(HashMap<IInventoryItem, Integer> skuqty, int id, String email, LocalDateTime date) {
		this.skuqty=new HashMap<IInventoryItem, Integer>();
		this.skuqty.putAll(skuqty);
		this.id=id;
		this.email=email;
		this.date=date;
	}

	public int getId() {
		return id;
	}

	public HashMap<IInventoryItem, Integer> getSkuqty() {
		return skuqty;
	}
	
	public void setSkuqty(HashMap<IInventoryItem, Integer> skuqty) {
		this.skuqty = skuqty;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public HashMap<IInventoryItem, Integer> getMap(){
		return skuqty;
	}
	
	public double getTotal() {
		double tot=0;
		for(IInventoryItem i: getSet()) {
			tot+=i.getPrice()*skuqty.get(i);
		}
		return tot;
	}
	
	public int getQtyOfItem(IInventoryItem i) {
		return skuqty.get(i);
	}
	
	public HashSet<IInventoryItem> getSet(){
		HashSet<IInventoryItem> sq = new HashSet<IInventoryItem>();
		skuqty.forEach((t, u) -> sq.add(t)); 
		return sq;
	}
	
	public IInventoryItem getItemBySku(String sku) {
		IInventoryItem ret = null;
		for(IInventoryItem i: getSet()) {
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
	public String toString() {
		String s="";
		for(IInventoryItem i: getSet()) {
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
	
}
