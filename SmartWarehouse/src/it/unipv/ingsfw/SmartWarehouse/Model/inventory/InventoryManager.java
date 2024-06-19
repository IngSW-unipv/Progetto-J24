package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.Collections;
import java.util.List;

public class InventoryManager {  
	private InventoryDAOFacade invFacade;
	
	public InventoryManager() {
		this.invFacade = InventoryDAOFacade.getInstance();
	}
	 
	public List<IInventoryItem> getInventory() {
		return invFacade.viewInventory();
	}  
	
	public IInventoryItem findInventoryItem(String sku) {
		return invFacade.findInventoryItemBySku(sku);
	}  
	
	public IInventoryItem findInventoryItemByPosition(Position pos) {
		return invFacade.checkIfPositionAlreadyUsed(pos);
	}

	/**
	 * Descending sort with the difference between stdLevel and quantity
	 * @param items
	 */
	public void orderInventoryItems(List<IInventoryItem> items){
		Collections.sort(items);
	}
	
	public List<IInventoryItem> getInventoryItemsUnderStdLevel(){
		return invFacade.getInventoryItemsUnderStdLevel();
	}
	
} 




