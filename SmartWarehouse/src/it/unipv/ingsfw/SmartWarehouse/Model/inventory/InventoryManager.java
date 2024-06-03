package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.Collections;
import java.util.List;

public class InventoryManager {  
	private InventoryDAOFacade invFacade;
	
	public InventoryManager() {
		this.invFacade = InventoryDAOFacade.getInstance();
	}
	 
	public List<InventoryItem> getInventory() {
		return invFacade.viewInventory();
	}  
	
	public InventoryItem findInventoryItem(String sku) {
		return invFacade.findInventoryItemBySku(sku);
	}  
	
	public InventoryItem findInventoryItemByPosition(Position pos) {
		return invFacade.checkIfPositionAlreadyUsed(pos);
	}

	public void orderInventoryItems(List<InventoryItem> items){
		Collections.sort(items);
	}
	
	public List<InventoryItem> getInventoryItemsUnderStdLevel(){
		return invFacade.getInventoryItemsUnderStdLevel();
	}
	
} 




