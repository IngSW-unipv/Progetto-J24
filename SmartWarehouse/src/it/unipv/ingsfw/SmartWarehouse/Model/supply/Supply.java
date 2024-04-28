package model.supply;

import java.time.LocalDateTime;
import exception.*;
import exception.supplier.SupplierDoesNotExistException;
import exception.supply.InvalidMaxQuantityExcepion;
import exception.supply.InvalidPriceException;
import exception.supply.InvalidSupplyException;
import exception.supply.SupplyAlreadyExistsException;
import exception.supply.SupplyDoesNotExistException;
import facade.InventoryDAOFacade;
import facade.SupplyDAOFacade;
import model.inventory.InventoryItem;
import model.operator.SupplyOperator;
import model.operator.WarehouseOperator;
import randomGenerator.IRandomGenerator;
import randomGenerator.RandomGenerator;

public class Supply {
	private String ID_Supply;
	private Supplier supplier;
	private InventoryItem inventoryItem;
	private double price;
	private int maxqty; 
	
	//serve?
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
	//useful to view
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
	
	//metodo chiamato su una supply costruita con il terzo costruttore
	public void add(WarehouseOperator op) throws InvalidSupplyException, AuthorizationDeniedException, ItemNotFoundException, SupplierDoesNotExistException {
		this.checkSupplierAuthorization(op);
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
	
	public void delete(WarehouseOperator op) throws InvalidSupplyException, AuthorizationDeniedException {
		this.checkSupplierAuthorization(op);
		//check the presence of the supply
		if(SupplyDAOFacade.getInstance().findSupply(ID_Supply)!=null) {
			SupplyDAOFacade.getInstance().deleteSupply(this);
		} else {
			throw new SupplyDoesNotExistException();
		}
	}

	//controllare non superi il massimo ordinabile maxqty
	//controllo che la supply sia effettivamente presente 
	public SupplyOrder buy(WarehouseOperator op, int qty) throws AuthorizationDeniedException, SupplyDoesNotExistException, IllegalArgumentException {
		this.checkSupplierAuthorization(op);
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
	 
	private void checkSupplierAuthorization(WarehouseOperator op) throws AuthorizationDeniedException {
		try {
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
