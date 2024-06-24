
package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.HashMap;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Cart {
	HashMap<IInventoryItem,Integer> skuqty;
	
	public Cart() {
		skuqty=new HashMap<IInventoryItem, Integer>();
	}
	/*
	 * add to the Map skuqty the selected item and its quantity
	 * if the given quantity is < 1 throws an exception
	 * if the given item was already in the map the quantity value is updated
	 */
	public void add(IInventoryItem it, int qty)throws IllegalArgumentException{
		int i;
		if(qty < 1) {
			throw new IllegalArgumentException("quantity cannot be negative");
		}
		it = findItemInCart(it);
		i=skuqty.getOrDefault(it, 0)+qty;
		skuqty.put(it, i);
	}
	/*
	 * remove the mapping in skuqty for the selected item;
	 */
	public void remove(IInventoryItem it) {
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
		for(IInventoryItem i: getSet()) {
			int nuova = i.getQty() - skuqty.get(i);
			i.updateQty(nuova);
		}
	}
	/*
	 * Calculate the total cost of the Cart
	 */
	public double getTotal() {
		double tot = 0;
		for(IInventoryItem i: getSet()) {
			tot += i.getPrice()*skuqty.get(i); //item value times its quantity
		}
		return tot;
	}
	/*
	 * Getters and Setters
	 */
	public HashMap<IInventoryItem, Integer> getSkuqty() {
		return skuqty;
	}

	public void setSkuqty(HashMap<IInventoryItem, Integer> skuqty) {
		this.skuqty = skuqty;
	}
	
	public HashSet<IInventoryItem> getSet(){
		HashSet<IInventoryItem> sq = new HashSet<IInventoryItem>();
		skuqty.forEach((t, u) -> sq.add(t)); 
		return sq;
	}

	public String toString() {
		String out="";
		for(IInventoryItem i: skuqty.keySet()) {
			out = out+i.toString()+"-"+skuqty.get(i)+"\n";
		}
		return out;
	}
	
	private IInventoryItem findItemInCart(IInventoryItem i) {
		IInventoryItem ret = i;
		for(IInventoryItem it: getSet()) {
			if(i.getSku().equals(it.getSku())) {
				ret = it;
				break;
			}
		}
		return ret;
	}
}
