package facade;

import java.util.List;
import java.util.Set;
import db.IInventoryDAO;
import db.InventoryDAO;
import model.inventory.InventoryItem;
import model.inventory.Position;
import model.supply.Supplier;

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
	
	public Set<InventoryItem> getInventoryItemsUnderStdLevel(){
		return inventoryDAO.viewItemsUnderStdLevel();
	} 

	public List<Object[]> getSuppliersInfo(InventoryItem i){
		return inventoryDAO.getSuppliersInfo(i);
	}
}
