package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import java.time.LocalDateTime;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidMaxQuantityExcepion;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidPriceException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidSupplyException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.SupplyOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.WarehouseOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.*;

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
	
	//useful to dao
	public Supply(String ID_Supply, String sku, String ids, double price, int maxqty) {
		this.ID_Supply = ID_Supply;
		this.supplier = SupplyDAOFacade.getInstance().findSupplier(ids);
		this.inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku); 
		this.price = price; 
		this.maxqty = maxqty;
	} 

	public Supply(String sku, String ids, double price, int maxqty) {
		this.ID_Supply=this.generateUnivoqueID_Supply();
		this.supplier = SupplyDAOFacade.getInstance().findSupplier(ids);
		this.inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku); 
		this.price = price; 
		this.maxqty = maxqty;
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

	public void setPrice(double price) {
		this.price = price;
	}

	public int getMaxqty() {
		return maxqty;
	}

	public void setMaxqty(int maxqty) {
		this.maxqty = maxqty;
	}
	
	@Override
	public String toString() {
		return ID_Supply+" "+ supplier.getIDS()+" "+inventoryItem.getSku()+" "+price+" "+maxqty;
	}
	
	//method called on a supply built whit the third constructor
	public void add() throws InvalidSupplyException, AuthorizationDeniedException, ItemNotFoundException, SupplierDoesNotExistException {
		this.checkSupplierAuthorization();
		if(price<=0) {
			throw new InvalidPriceException();
		} 
		if(maxqty<=0) {
			throw new InvalidMaxQuantityExcepion();
		}
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
		this.checkSupplierAuthorization();
		//check the presence of the supply
		if(SupplyDAOFacade.getInstance().findSupply(ID_Supply)!=null) {
			SupplyDAOFacade.getInstance().deleteSupply(this);
		} else {
			throw new SupplyDoesNotExistException();
		}
	}

	//check that qty is less than maxQty
	public SupplyOrder buy(int qty) throws AuthorizationDeniedException, SupplyDoesNotExistException, IllegalArgumentException {
		this.checkSupplierAuthorization();
		//check the presence of the supply
		if(SupplyDAOFacade.getInstance().findSupply(ID_Supply)==null) {
			throw new SupplyDoesNotExistException();
		}
		if(qty<=0) {
			throw new IllegalArgumentException("qty must be positive");
		}
		if(qty>maxqty) {
			throw new IllegalArgumentException("qty can't exceed the maximum orderable limit: "+maxqty);
		}
		SupplyOrder o=new SupplyOrder(SupplyDAOFacade.getInstance().nextNOrder(), ID_Supply, qty, price*qty, LocalDateTime.now());
		SupplyDAOFacade.getInstance().insertSupplyOrder(o);
		return o;
	} 
	  
	private void checkSupplierAuthorization() throws AuthorizationDeniedException {
		try {
			WarehouseOperator op=SingletonUser.getInstance().getOp();
			SupplyOperator su = (SupplyOperator) op;
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
