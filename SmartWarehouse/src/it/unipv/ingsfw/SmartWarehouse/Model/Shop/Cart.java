
package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Cart {
	HashMap<InventoryItem,Integer> skuqty;
	
	public Cart() {
		skuqty=new HashMap<InventoryItem, Integer>();
	}
	/*
	 * add to the Map skuqty the selected item and its quantity
	 * if the given quantity is < 1 throws an exception
	 */
	public void add(InventoryItem it, int qty)throws IllegalArgumentException{
		int i;
		if(qty < 1) {
			throw new IllegalArgumentException("quantity cannot be negative");
		}
		i=skuqty.getOrDefault(it, 0)+qty;
		skuqty.put(it, i);
	}
	/*
	 * remove the mapping in skuqty for the selected item;
	 */
	public void remove(InventoryItem it) {
		 skuqty.remove(findItemInCart(it));
	}
	/*
	 * create an instance of Order using the first constructor
	 * and make sure to update the right quantity in the DB	
	 * if the cart is empty throws an exception
	 */
	public Order PayAndOrder(Client cl) throws EmptyKartExceptio, IllegalArgumentException, ItemNotFoundException {
		if(skuqty.isEmpty()) {
			throw(new EmptyKartExceptio());
		}		
		Order o=new Order(skuqty, cl.getEmail());
		updateWarehouseQty();
		skuqty.clear();
		return o;
	}
	/*
	 * Update Correctly Warehouse quantities
	 */
	private void updateWarehouseQty() throws IllegalArgumentException, ItemNotFoundException{
		for(InventoryItem i: getSet()) {
			int nuova = i.getQty() - skuqty.get(i);
			i.updateQty(nuova);
		}
	}
	/*
	 * Calculate the total cost of the Cart
	 */
	public double getTotal() {
		double tot = 0;
		for(InventoryItem i: getSet()) {
			tot += i.getPrice()*skuqty.get(i); //item value times its quantity
		}
		return tot;
	}
	/*
	 * Getters and Setters
	 */
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
	
	private InventoryItem findItemInCart(InventoryItem i) {
		InventoryItem ret = null;
		for(InventoryItem it: getSet()) {
			if(i.getSku().equals(it.getSku())) {
				ret = it;
				break;
			}
		}
		return ret;
	}
}
