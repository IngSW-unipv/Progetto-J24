package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Shop {
	
	private InventoryManager inv;
	private Kart kart;
	private RegisterFacade reg;
	private Client cl;
	private final double primeImport=50;
	
	public Shop() {	
		this.kart = new Kart();
		this.inv = SingletonManager.getInstance().getInventoryManager();
		this.reg = RegisterFacade.getIstance();
		this.cl = (Client)SingletonManager.getInstance().getLoggedUser();		
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
	
	
	public void setPrime() {
		cl.setPrime(true);		
	}
	
	public double getPrimeImport() {
		return primeImport;
	}

	public InventoryManager getInv() {
		return inv;
	}

	public Kart getKart() {
		return kart;
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

	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public void setReg(RegisterFacade reg) {
		this.reg = reg;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}
}
