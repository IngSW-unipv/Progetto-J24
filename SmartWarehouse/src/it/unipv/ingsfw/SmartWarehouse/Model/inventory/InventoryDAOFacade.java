package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Database.IInventoryDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.InventoryDAO;

/**
 * Contains all the DAO methods about inventory
 */
public class InventoryDAOFacade {
	private static InventoryDAOFacade instance;
	private IInventoryDAO inventoryDAO;
	 
	private InventoryDAOFacade() {
		inventoryDAO = new InventoryDAO(); 
	}
	
	public static synchronized InventoryDAOFacade getInstance() {
		if(instance==null) {
			instance=new InventoryDAOFacade();
		} 
		return instance;
	}
	  
	public List<IInventoryItem> viewInventory(){ 
		return inventoryDAO.selectAllInventory();
	}
	
	public IInventoryItem findInventoryItemBySku(String sku) {
		return inventoryDAO.getInventoryItemBySku(sku);
	}  
	
	public boolean insertItem(IInventoryItem i){ 
		return inventoryDAO.insertItemToInventory(i);
	}
	
	public boolean deleteItem(String sku) {
		return inventoryDAO.deleteItem(sku);
	}
	
	public IInventoryItem checkIfPositionAlreadyUsed(Position pos) {
		return inventoryDAO.getInventoryItemByPosition(pos);
	} 
	 
	public boolean updateInventoryItemQty(String sku, int qty) {
		return inventoryDAO.updateInventoryItemQty(sku, qty);
	}
	
	public List<IInventoryItem> getInventoryItemsUnderStdLevel(){
		return inventoryDAO.viewItemsUnderStdLevel();
	} 

	public List<Object[]> getSuppliersInfo(IInventoryItem i){
		return inventoryDAO.getSuppliersInfo(i);
	}
	
	public int getInventoryItemQty(IInventoryItem i) {
		return inventoryDAO.getItemQty(i);
	}
}
