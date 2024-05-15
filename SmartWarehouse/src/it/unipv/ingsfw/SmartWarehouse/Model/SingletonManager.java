package it.unipv.ingsfw.SmartWarehouse.Model;

import it.unipv.ingsfw.SmartWarehouse.Database.ClientDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.IClientDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.IInventoryDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.IRegisterDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.IReturnServiceDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ISupplierDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ISupplyDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ISupplyOrderDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.InventoryDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.RegisterDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ReturnServiceDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplierDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplyDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplyOrderDAO;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.WarehouseOperator;

public class SingletonManager {
	
	
	
	private static SingletonManager instance= null;
	private WarehouseOperator op;
	private IClientDAO clientDAO;
	private IInventoryDAO inventoryDAO;
	private IRegisterDAO registerDAO;
	private IReturnServiceDAO returnServiceDAO;
	private ISupplierDAO supplierDAO;
	private ISupplyDAO supplyDAO;
	private ISupplyOrderDAO supplyOrderDAO;
	private User loggedUser;
	
	private SingletonManager() {
		op=new InventoryOperator();
		clientDAO=new ClientDAO();
		inventoryDAO=new InventoryDAO();
		registerDAO=new RegisterDAO();
		returnServiceDAO=new ReturnServiceDAO();
		supplierDAO=new SupplierDAO();
		supplyDAO=new SupplyDAO();
		supplyOrderDAO=new SupplyOrderDAO();
		
	}   
	
	public static synchronized SingletonManager getInstance() {
		if(instance==null) {
			instance = new SingletonManager();
		}
		return instance;
	}
	
	public WarehouseOperator getOp() {
		return op;
	}
	public IClientDAO getClientDAO() {
		return clientDAO;
	}
	
	public IInventoryDAO getInventoryDAO() {
		return inventoryDAO;
	}

	public void setInventoryDAO(IInventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}

	public IRegisterDAO getRegisterDAO() {
		return registerDAO;
	}

	public void setRegisterDAO(IRegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}

	public IReturnServiceDAO getReturnServiceDAO() {
		return returnServiceDAO;
	}

	public void setReturnServiceDAO(IReturnServiceDAO returnServiceDAO) {
		this.returnServiceDAO = returnServiceDAO;
	}

	public ISupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(ISupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}

	public ISupplyDAO getSupplyDAO() {
		return supplyDAO;
	}

	public void setSupplyDAO(ISupplyDAO supplyDAO) {
		this.supplyDAO = supplyDAO;
	}

	public ISupplyOrderDAO getSupplyOrderDAO() {
		return supplyOrderDAO;
	}

	public void setSupplyOrderDAO(ISupplyOrderDAO supplyOrderDAO) {
		this.supplyOrderDAO = supplyOrderDAO;
	}

	public static void setInstance(SingletonManager instance) {
		SingletonManager.instance = instance;
	}

	public void setOp(WarehouseOperator op) {
		this.op = op;
	}

	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	
	

}
