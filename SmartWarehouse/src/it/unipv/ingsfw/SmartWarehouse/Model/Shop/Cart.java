package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Cart {
	HashMap<InventoryItem,Integer> skuqty;
	
	public Cart() {
		skuqty=new HashMap<InventoryItem, Integer>();
	}
	
	public void add(InventoryItem it, int qty)throws IllegalArgumentException{
		int i;
		if(qty <= 0) {
			IllegalArgumentException e=new IllegalArgumentException("qty cannot be negative or 0");
			throw e;
		}
		i=skuqty.getOrDefault(it, 0)+qty;
		skuqty.put(it, i);
	}
	
	public void remove(InventoryItem it) {
		 skuqty.remove(findItemInCart(it));
	}
		
	public Order PayAndOrder(Client cl) throws EmptyKartExceptio, IllegalArgumentException, ItemNotFoundException {
		if(skuqty.isEmpty()) {
			throw(new EmptyKartExceptio());
		}		
		Order o=new Order(skuqty, cl.getEmail());
		updateWarehouseQty();
		skuqty.clear();
		return o;
	}
	
	private void updateWarehouseQty() throws IllegalArgumentException, ItemNotFoundException{
		for(InventoryItem i: getSet()) {
			int nuova = i.getQty() - skuqty.get(i);
			i.updateQty(nuova);
		}
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
			out = out+i.getDescription()+"-"+skuqty.get(i)+"\n";
		}
		return out;
	}
	
	
	
	private InventoryItem findItemInCart(InventoryItem it) {
		InventoryItem in = null;
		for(InventoryItem i: getSet()) {
			if(it.getSku().equals(i.getSku())) {
				in = i;
				break;
			}
		}
		return in;
	}
}
