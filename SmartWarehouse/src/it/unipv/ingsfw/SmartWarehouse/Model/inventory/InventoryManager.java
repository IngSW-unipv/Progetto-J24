package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
	
	public InventoryItem findInventoryItemByPosition(Position pos) throws IllegalArgumentException {
		if(pos==null || pos.getLine().isEmpty() || pos.getPod().isEmpty() || pos.getBin().isEmpty()) {
			throw new IllegalArgumentException("Insert a position valid, without empty fields");
		}
		return invFacade.checkIfPositionAlreadyUsed(pos);
	}

	public void orderInventoryItems(List<InventoryItem> items){
		Collections.sort(items);
	}
	//???non ancora messo nella view
	public List<InventoryItem> getInventoryItemsUnderStdLevel(){
		return invFacade.getInventoryItemsUnderStdLevel();
	}

} 




