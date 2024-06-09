
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

	public HashMap<InventoryItem, Integer> getSkuqty() {
		return skuqty;
	}
	
	public void setSkuqty(HashMap<InventoryItem, Integer> skuqty) {
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
	
}
