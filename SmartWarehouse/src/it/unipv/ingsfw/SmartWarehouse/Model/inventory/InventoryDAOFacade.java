package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Database.IInventoryDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.InventoryDAO;

//contains all the DAO methods about inventory
public class InventoryDAOFacade {
	private static InventoryDAOFacade instance;
	private IInventoryDAO inventoryDAO;
	 
	private InventoryDAOFacade() {
		inventoryDAO=new InventoryDAO(); 
	}
	
	public static synchronized InventoryDAOFacade getInstance() {
		if(instance==null) {
			instance=new InventoryDAOFacade();
		} 
		return instance;
	}
	  
	public List<InventoryItem> viewInventory(){ 
		return inventoryDAO.selectAllInventory();
	}
	
	public InventoryItem findInventoryItemBySku(String sku) {
		return inventoryDAO.getInventoryItemBySku(sku);
	}  
	
	public boolean insertItem(InventoryItem i){ 
		return inventoryDAO.insertItemToInventory(i);
	}
	
	public boolean deleteItem(String sku) {
		return inventoryDAO.deleteItem(sku);
	}
	
	public InventoryItem checkIfPositionAlreadyUsed(Position pos) {
		return inventoryDAO.getInventoryItemByPosition(pos);
	} 
	 
	public boolean updateInventoryItemQty(String sku, int qty) {
		return inventoryDAO.updateInventoryItemQty(sku, qty);
	}
	
	public List<InventoryItem> getInventoryItemsUnderStdLevel(){
		return inventoryDAO.viewItemsUnderStdLevel();
	} 

	public List<Object[]> getSuppliersInfo(InventoryItem i){
		return inventoryDAO.getSuppliersInfo(i);
	}
	
}
