package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;

public class Shop {
	
	private InventoryManager inv;
	private Kart kart;
	private Register reg;
	private Client cl;
	private final double primeImport=50;
	
	public Shop(InventoryManager inv, Register reg, Client c) {	
		this.kart = new Kart();
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
	
	public void makeOrder(IPayment mode) {
		try {
			reg.addOrd(kart.PayAndOrder(cl, mode));
		} catch (EmptyKartExceptio e) {
			System.err.println("the kart is empty, please retry");
		} 
	}
	
	
	public void setPrime(IPayment mode) {
		PaymentProcess pay=new PaymentProcess(mode, cl.getEmail(), "magazzo");
		try {
			pay.startPayment(primeImport);
			cl.setPrime(true);
		} catch (PaymentException e) {
			//TODO SISTEMARE
			System.err.println("è stato impossibile effettuare l'abbonamento a prime");
		}
		
	}

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
