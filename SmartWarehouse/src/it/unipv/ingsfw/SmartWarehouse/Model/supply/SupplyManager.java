package model.supply;

import java.util.List;
import java.util.Set;
import facade.InventoryDAOFacade;
import facade.SupplyDAOFacade;

public class SupplyManager {
	private SupplyDAOFacade supplyDAOFacade;
	
	public SupplyManager() { 
		supplyDAOFacade=SupplyDAOFacade.getInstance();
	} 
	
	public List<Supplier> getSuppliers(){ 
		return supplyDAOFacade.allSuppliers();
	}
	
	public Supplier findSupplier(String ids) {
		return supplyDAOFacade.findSupplier(ids); 
	}

//--------------------------------------------------------------------------
	
	public List<Supply> getSupplies(){
		return supplyDAOFacade.allSupplies();
	}
	 
	public Supply findSupply(String id_supply) {
		return supplyDAOFacade.findSupply(id_supply); 
	}
	
	public Supply findSupplyByItemAndSupplier(String sku, String ids) {
		return supplyDAOFacade.findSupplyBySkuAndIds(sku, ids);
	}
 
//------------------------------------------------------------------------------
	 
	public List<SupplyOrder> getSupplyOrders(){
		 return supplyDAOFacade.viewSupplyOrders();
	}  
}
 