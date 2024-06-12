package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import java.time.LocalDateTime;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.InvalidSupplierException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidSupplyException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.*;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.SupplyOperator;

public class Supply {
	private String ID_Supply;
	private Supplier supplier;
	private InventoryItem inventoryItem;
	private double price;
	private int maxqty; 

	public Supply(String ID_Supply, Supplier supplier, InventoryItem inventoryItem, double price, int maxqty) {
		this.ID_Supply = ID_Supply;
		this.supplier = supplier;
		this.inventoryItem = inventoryItem; 
		this.price = price; 
		this.maxqty = maxqty;
	} 
	
	//useful to DAO
	public Supply(String ID_Supply, String sku, String ids, double price, int maxqty) {
		this.ID_Supply = ID_Supply;
		this.supplier = SupplyDAOFacade.getInstance().findSupplier(ids);
		this.inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku); 
		this.price = price; 
		this.maxqty = maxqty;
	} 

	//used for the creation of a new supply
	public Supply(String sku, String ids, double price, int maxqty) throws IllegalArgumentException {
		this.ID_Supply=this.generateUnivoqueID_Supply();
		this.supplier = SupplyDAOFacade.getInstance().findSupplier(ids);
		this.inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku); 
		this.setPrice(price); 
		this.setMaxqty(maxqty);
	} 

	public String getID_Supply() {
		return ID_Supply;
	}

	public void setID_Supply(String iDS) {
		ID_Supply = iDS;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws IllegalArgumentException {
		if(price<=0) {
			throw new IllegalArgumentException("Price must be positive");
		}
		this.price=price;
	}
	
	public int getMaxqty() {
		return maxqty;
	}

	public void setMaxqty(int maxqty) throws IllegalArgumentException  {
		if(maxqty<=0) {
			throw new IllegalArgumentException("Maximum quantity that can be ordered must be positive");
		}
		this.maxqty=maxqty;
	}
	
	//method called on a supply built with the third constructor
	public void add() throws AuthorizationDeniedException, ItemNotFoundException, InvalidSupplierException, InvalidSupplyException {
		this.checkreplenishAuthorization();
		//check the presence of the inventoryItem
		if(inventoryItem==null) { 
			throw new ItemNotFoundException(); 
		}   
		//check the presence of the supplier
		if(supplier==null) { 
			throw new SupplierDoesNotExistException(); 
		} 	
		//check the presence of the supply
		if(SupplyDAOFacade.getInstance().findSupplyBySkuAndIds(inventoryItem.getSku(), supplier.getIDS())==null) {
			SupplyDAOFacade.getInstance().insertNewSupply(this);		
		} else {
			throw new SupplyAlreadyExistsException();
		}			 
	} 
	
	public void delete() throws InvalidSupplyException, AuthorizationDeniedException {
		this.checkreplenishAuthorization();
		//check the presence of the supply
		if(SupplyDAOFacade.getInstance().findSupply(ID_Supply)!=null) {
			//delete orders of that supply
			SupplyDAOFacade.getInstance().deleteSupplyOrder(this);
			SupplyDAOFacade.getInstance().deleteSupply(this);
		} else {
			throw new SupplyDoesNotExistException();
		}
	} 

	//check that qty is less than maxQty
	public SupplyOrder buy(int qty) throws AuthorizationDeniedException, InvalidSupplyException, IllegalArgumentException {
		//check the presence of the supply in the DB
		if(SupplyDAOFacade.getInstance().findSupply(ID_Supply)==null) {
			throw new SupplyDoesNotExistException();
		}
		if(qty<=0) {
			throw new IllegalArgumentException("qty must be positive");
		}
		if(qty>maxqty) {
			throw new IllegalArgumentException("qty can't exceed the maximum orderable limit: "+maxqty);
		}
		return replenishSupply(qty);
	} 
	  
	public SupplyOrder replenishSupply(int qty) throws AuthorizationDeniedException {
		this.checkreplenishAuthorization();
		SupplyOrder o=new SupplyOrder(SupplyDAOFacade.getInstance().nextNOrder(), ID_Supply, qty, price*qty, LocalDateTime.now());
		SupplyDAOFacade.getInstance().insertSupplyOrder(o);
		return o;
	} 
	
	private void checkreplenishAuthorization() throws AuthorizationDeniedException {
		try {
			SupplyOperator op= (SupplyOperator)SingletonUser.getInstance().getLoggedUser();
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
	private String generateUnivoqueID_Supply() {
		IRandomGenerator ra=new RandomGenerator();
		String s=ra.generateRandomString(6);
		while(SupplyDAOFacade.getInstance().findSupply(s)!=null) {
			s=ra.generateRandomString(6);
		}
		return s; 
	} 
	
}
