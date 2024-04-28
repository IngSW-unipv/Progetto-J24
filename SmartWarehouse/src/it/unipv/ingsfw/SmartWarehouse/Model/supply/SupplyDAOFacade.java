package facade;

import java.util.List;
import java.util.Set;

import db.ISupplierDAO;
import db.ISupplyDAO;
import db.ISupplyOrderDAO;
import db.SupplierDAO;
import db.SupplyDAO;
import db.SupplyOrderDAO;
import model.supply.Supplier;
import model.supply.Supply;
import model.supply.SupplyOrder;

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
	
	public boolean insertNewSupply(Supply s) {
		return supplyDAO.insertSupply(s);
	}
	
	public boolean deleteSupply(Supply s) {
		return supplyDAO.deleteSupply(s); 
	} 

//----------------------------------------------------------
	
	public boolean insertSupplyOrder(SupplyOrder o) {
		return supplyOrderDAO.insertSupplyOrder(o);
	}
	
	public int nextNOrder() {
		return supplyOrderDAO.nextNOrder();
	}
	
	public List<SupplyOrder> viewSupplyOrders(){
		return supplyOrderDAO.selectAllSupplyOrders();
	}
 }
