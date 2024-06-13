package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Shop {
	
	private InventoryManager inv;
	private Cart cart;
	private RegisterFacade reg;
	private Client cl;
	private final double primeImport=50;
	private final int Soglia=5;
	
	public Shop() {	
		this.cart = new Cart();
		this.inv = new InventoryManager();
		this.reg = RegisterFacade.getIstance();
		this.cl = (Client)SingletonUser.getInstance().getLoggedUser();		
	}
	/*
	 * puts the selected quantity into the cart 
	 * using the inventoryManager class to get the right item 
	 * based on the sku parameter
	 */
	public void addToCart(String sku, int qty) throws IllegalArgumentException{
		if(inv.findInventoryItem(sku).getQty() > qty + Soglia) {
			cart.add(inv.findInventoryItem(sku), qty);
		}
		else throw new IllegalArgumentException("not eanugh items");
	}
	/*
	 * removes from the cart a selected item 
	 * with a similar way as add
	 */
	public void removeFromCart(String sku) {
		cart.remove(inv.findInventoryItem(sku));
	}
	/*
	 * invoke a method from the Cart class that crate
	 * the an instance of Order then used by the Register Class 
	 * to add it in the DB
	 */
	public void makeOrder() throws IllegalArgumentException, EmptyKartExceptio, ItemNotFoundException, PaymentException {
		reg.addOrd(cart.PayAndOrder(cl));
	}
	/*
	 * Getters and Setters
	 */
	public void setPrime() {
		cl.setPrime(true);		
	}
	
	public double getPrimeImport() {
		return primeImport;
	}

	public InventoryManager getInv() {
		return inv;
	}

	public Cart getCart() {
		return cart;
	}

	public RegisterFacade getReg() {
		return reg;
	}

	public Client getCl() {
		return cl;
	}

	public void setInv(InventoryManager inv) {
		this.inv = inv;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setReg(RegisterFacade reg) {
		this.reg = reg;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}
}
