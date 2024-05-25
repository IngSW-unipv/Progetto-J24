package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public class Kart {
	HashMap<InventoryItem,Integer> skuqty;
	
	public Kart() {
		skuqty=new HashMap<InventoryItem, Integer>();
	}
	
	public void add(InventoryItem it, int qty)throws IllegalArgumentException{
		int i;
		if(qty <= 0) {
			IllegalArgumentException e=new IllegalArgumentException();
			throw e;
		}
		i=skuqty.getOrDefault(it, 0)+qty;
		skuqty.put(it, i);
	}
	
	public void remove(InventoryItem it) {
		 skuqty.remove(it);
	}
		
	public Order PayAndOrder(Client cl) throws EmptyKartExceptio {
		if(skuqty.isEmpty()) {
			throw(new EmptyKartExceptio());
		}		
		Order o=new Order(skuqty, cl.getEmail());
		skuqty.clear();
		return o;
	}
	
	public double getTotal() {
		double tot = 0;
		for(InventoryItem i: getSet()) {
			tot += i.getPrice()*skuqty.get(i); //item value times its quantity
		}
		return tot;
	}
	
	public HashMap<InventoryItem, Integer> getSkuqty() {
		return skuqty;
	}

	public void setSkuqty(HashMap<InventoryItem, Integer> skuqty) {
		this.skuqty = skuqty;
	}
	
	public HashSet<InventoryItem> getSet(){
		HashSet<InventoryItem> sq = new HashSet<InventoryItem>();
		skuqty.forEach((t, u) -> sq.add(t)); 
		return sq;
	}

	public String toString() {
		String out="";
		for(InventoryItem i: skuqty.keySet()) {
			out = out+i.toString()+"-"+skuqty.get(i)+"\n";
		}
		return out;
	}
}
