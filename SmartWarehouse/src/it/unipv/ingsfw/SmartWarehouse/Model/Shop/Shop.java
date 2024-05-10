package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;

public class Shop {
	
	private InventoryManager inv;
	private Kart kart;
	private Register reg;
	private Client cl;
	private final double prcost=50;
	
	public Shop(InventoryManager inv, Register reg, Kart kart, Client c) {	
		this.kart = kart;
		this.inv = inv;
		this.reg = reg;
		this.cl = c;		
	}
	
	public void addToKart(String sku, int qty) throws IllegalArgumentException{
		kart.add(inv.findInventoryItem(sku), qty);	
	}
	
	public void removeFromKart(String sku) {
		kart.remove(inv.findInventoryItem(sku));
	}
	
	public void makeOrder() {
		try {
			reg.addOrd(kart.PayAndOrder(cl));
		} catch (EmptyKartExceptio e) {
			System.err.println("the kart is empty, please retry");
		} 
	}
	
	/*
	**
	public void setPrime() {
		if(!Payment.getIstance().pay(cl, prcost) || cl.getPrime()) {
			cl.setPrime(false);
			System.err.println("pagamento non riuscito.");
		} else cl.setPrime(true);
	}
	**
	*/

	public InventoryManager getInv() {
		return inv;
	}

	public Kart getKart() {
		return kart;
	}

	public Register getReg() {
		return reg;
	}

	public Client getCl() {
		return cl;
	}

	public void setInv(InventoryManager inv) {
		this.inv = inv;
	}

	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public void setReg(Register reg) {
		this.reg = reg;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}
}
