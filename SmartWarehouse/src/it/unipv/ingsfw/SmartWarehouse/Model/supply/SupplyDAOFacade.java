package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Database.ISupplierDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ISupplyDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ISupplyOrderDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplierDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplyDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.SupplyOrderDAO;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public class SupplyDAOFacade {
	private static SupplyDAOFacade instance;
	private ISupplierDAO supplierDAO;  
	private ISupplyDAO supplyDAO;
	private ISupplyOrderDAO supplyOrderDAO;
	 
	private SupplyDAOFacade() {
		supplierDAO=new SupplierDAO();
		supplyDAO=new SupplyDAO(); 
		supplyOrderDAO=new SupplyOrderDAO();
	}   
	
	public static synchronized SupplyDAOFacade getInstance() {
		if(instance==null) {
			instance = new SupplyDAOFacade();
		}
		return instance;
	}
	 
	public List<Supplier> allSuppliers(){ 
		return supplierDAO.selectAllSupplier();
	}
	
	public Supplier findSupplier(String ids) {
		return supplierDAO.getSupplierByIds(ids);
	} 
	
	public boolean insertNewSupplier(Supplier s) {
		return supplierDAO.insertSupplier(s);
	}
	
	public boolean deleteSupplier(Supplier s) { 
		return supplierDAO.deleteSupplier(s);
	}
	
//----------------------------------------------------------
	
	public List<Supply> allSupplies(){ 
		return supplyDAO.selectAllSupplies();
	}
	
	public Supply findSupply(String IDSupply) {
		return supplyDAO.getSupplyByIDSupply(IDSupply);
	} 
	
	public Supply findSupplyBySkuAndIds(String sku, String ids) {
		return supplyDAO.getSupplyBySkuAndIds(sku, ids);
	}
	
	public List<Supply> findSupplyBySku(String sku) {
		return supplyDAO.getSupplyBySku(sku);
	}
	
	public List<Supply> findSupplyBySupplier(String ids){
		return supplyDAO.getSupplyByIds(ids); 
	}
	
	public boolean insertNewSupply(Supply s) {
		return supplyDAO.insertSupply(s);
	}
	
	public boolean deleteSupply(Supply s) {
		return supplyDAO.deleteSupply(s); 
	} 
	
	public Supply getCheaperSupplyByInventoryItem(InventoryItem i) {
		return supplyDAO.getCheaperSupplyByInventoryItem(i);
	}
 
//----------------------------------------------------------
	
	public boolean insertSupplyOrder(SupplyOrder o) {
		return supplyOrderDAO.insertSupplyOrder(o);
	}
	
	public List<SupplyOrder> getSupplyOrdersBySupply(Supply s){
		return supplyOrderDAO.getSupplyOrdersBySupply(s);
	}
	
	public int nextNOrder() {
		return supplyOrderDAO.nextNOrder();
	}
	
	public List<SupplyOrder> viewSupplyOrders(){
		return supplyOrderDAO.selectAllSupplyOrders();
	}
	
	public boolean deleteSupplyOrder(Supply s) {
		return supplyOrderDAO.deleteSupplyOrder(s);
	}
 }
