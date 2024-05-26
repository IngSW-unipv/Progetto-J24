package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import java.util.Collections;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.IReplenishmentStrategy;


public class SupplyManager {
	private SupplyDAOFacade supplyDAOFacade;
	private IReplenishmentStrategy replenishmentStrategy;
	
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
	
	public Supply getCheaperSupplyByInventoryItem(InventoryItem i) {
		return supplyDAOFacade.getCheaperSupplyByInventoryItem(i);
	} 
	
	public void setReplenishmentStrategy(IReplenishmentStrategy strategy) {
		this.replenishmentStrategy=strategy;
    }
	
	public void replenishAll(List<InventoryItem> items) throws AuthorizationDeniedException {
		if (replenishmentStrategy != null) {
            replenishmentStrategy.replenish(items);
        }
	}
//------------------------------------------------------------------------------
	 
	public List<SupplyOrder> getSupplyOrders(){
		 return supplyDAOFacade.viewSupplyOrders();
	}  
	
	public void getOrderedSupplyOrders(List<SupplyOrder> orders){
		 Collections.sort(orders);
	} 
}
 