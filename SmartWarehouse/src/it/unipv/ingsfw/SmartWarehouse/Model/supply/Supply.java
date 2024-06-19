package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import java.time.LocalDateTime;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.InvalidSupplierException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidSupplyException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.*;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.SupplyOperator;

public class Supply {
	private String ID_Supply;
	private Supplier supplier;
	private IInventoryItem inventoryItem;
	private double price;
	private int maxqty; 
	private SupplyDAOFacade supplyDAOFacade;

	/**
	 * Constructor used to create an object Supply, this Supply that already exists in DB
	 */
	public Supply(String ID_Supply, Supplier supplier, InventoryItem inventoryItem, double price, int maxqty) {
		this.ID_Supply = ID_Supply;
		this.supplier = supplier;
		this.inventoryItem = inventoryItem; 
		this.price = price; 
		this.maxqty = maxqty;
		supplyDAOFacade=SupplyDAOFacade.getInstance();
	} 
	
	/**
	 * Constructor used to create an object Supply, this Supply that already exists in DB
	 */
	public Supply(String ID_Supply, String sku, String ids, double price, int maxqty) {
		supplyDAOFacade=SupplyDAOFacade.getInstance();
		this.ID_Supply = ID_Supply;
		this.supplier = supplyDAOFacade.findSupplier(ids);
		this.inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku); 
		this.price = price; 
		this.maxqty = maxqty;
	} 

	/**
	 * Constructor used to create an object Supply which represents a new Supply 
	 */
	public Supply(String sku, String ids, double price, int maxqty) throws IllegalArgumentException {
		supplyDAOFacade=SupplyDAOFacade.getInstance();
		this.ID_Supply=this.generateUnivoqueID_Supply(new RandomGenerator());
		this.supplier = supplyDAOFacade.findSupplier(ids);
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

	public IInventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(IInventoryItem inventoryItem) {
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
	
	/**
	 * Adds the Supply after checking the Authorization, the presence of the InventoryItem, 
	 * the presence of the supplier, and the absence of the supply.
	 * The method is called on an object Supply created with the third constructor. 
	 */
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
		if(supplyDAOFacade.findSupplyBySkuAndIds(inventoryItem.getSku(), supplier.getIDS())==null) {
			supplyDAOFacade.insertNewSupply(this);		
		} else {
			throw new SupplyAlreadyExistsException();
		}			 
	} 
	
	/**
	 * The method deletes the Supply after checking the authorization, the presence of the Supply.
	 * It deletes also SupplyOrders associated so as not to violate the integrity constraint.
	 */
	public void delete() throws InvalidSupplyException, AuthorizationDeniedException {
		this.checkreplenishAuthorization();
		//check the presence of the supply
		if(supplyDAOFacade.findSupply(ID_Supply)!=null) {
			//delete orders of that supply
			supplyDAOFacade.deleteSupplyOrder(this);
			supplyDAOFacade.deleteSupply(this);
		} else {
			throw new SupplyDoesNotExistException();
		}
	} 

	public SupplyOrder buy(int qty) throws AuthorizationDeniedException, InvalidSupplyException, IllegalArgumentException {
		//check the presence of the supply in the DB
		if(supplyDAOFacade.findSupply(ID_Supply)==null) {
			throw new SupplyDoesNotExistException();
		}
		//check qty validity 
		if(qty<=0) {
			throw new IllegalArgumentException("qty must be positive");
		}
		if(qty>maxqty) {
			throw new IllegalArgumentException("qty can't exceed the maximum orderable limit: "+maxqty);
		}
		return replenishSupply(qty);
	} 
	  
	/**
	 * The method create a SupplyOrder after checking the authorization
	 * @return SupplyOrder
	 */
	public SupplyOrder replenishSupply(int qty) throws AuthorizationDeniedException {
		this.checkreplenishAuthorization();
		SupplyOrder o = new SupplyOrder(supplyDAOFacade.nextNOrder(), ID_Supply, qty, price*qty, LocalDateTime.now());
		supplyDAOFacade.insertSupplyOrder(o);
		return o;
	} 
	
	private void checkreplenishAuthorization() throws AuthorizationDeniedException {
		try {
			SupplyOperator op= (SupplyOperator)SingletonUser.getInstance().getLoggedUser();
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
	private String generateUnivoqueID_Supply(IRandomGenerator ra) {
		int idSupplySize = SmartWarehouseInfoPoint.getIdSupplySize();
		String s=ra.generateRandomString(idSupplySize);
		while(supplyDAOFacade.findSupply(s)!=null) {
			s=ra.generateRandomString(idSupplySize);
		}
		return s; 
	} 
	
}
