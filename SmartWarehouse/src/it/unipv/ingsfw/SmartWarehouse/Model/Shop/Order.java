package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Item;

public class Order implements IReturnable{
	private HashMap<InventoryItem, Integer> skuqty;
	private int id;
	private String email;
	private String date;
	public Order(HashMap<InventoryItem, Integer> skuqty, String email) {
		this.skuqty=new HashMap<InventoryItem, Integer>();
		this.skuqty.putAll(skuqty);
		this.email=email;
	
		Locale loc = new Locale.Builder().setLanguage("it").setRegion("EU").build();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
		date=dateFormat.format(new Date());
	}
	public Order(HashMap<InventoryItem, Integer> skuqty, int id, String email, String date) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
	
	//????????
	@Override 
	public String getDescBySku(String sku) {
		return getItemBySku(sku).toString();
	}
	
	
	@Override
	public String toString() {
		String s="";
		for(InventoryItem i: getSet()) {
			s+=i+", ";
		}
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
