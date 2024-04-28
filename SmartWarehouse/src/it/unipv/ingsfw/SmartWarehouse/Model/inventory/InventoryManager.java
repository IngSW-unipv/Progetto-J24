package model.inventory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import exception.AuthorizationDeniedException;
import exception.ItemNotFoundException;
import facade.InventoryDAOFacade;
import model.operator.InventoryOperator;
import model.operator.SupplyOperator;
import model.operator.WarehouseOperator;

public class InventoryManager {  
	private InventoryDAOFacade inventoryDAOFacade;
	
	public InventoryManager() {
		this.inventoryDAOFacade = InventoryDAOFacade.getInstance();
	}
	
	public List<InventoryItem> getInventory() {
		return inventoryDAOFacade.viewInventory();
	}  
	
	public InventoryItem findInventoryItem(String sku) {
		return inventoryDAOFacade.findInventoryItemBySku(sku);
	}  
	
	public InventoryItem findInventoryItemByPosition(Position pos) throws IllegalArgumentException {
		if(pos==null || pos.getLine().isEmpty() || pos.getPod().isEmpty() || pos.getBin().isEmpty()) {
			throw new IllegalArgumentException("Insert a position valid, without empty fields");
		}
		return inventoryDAOFacade.checkIfPositionAlreadyUsed(pos);
	}
	
	//useful?
	public void orderInventoryItems(List<InventoryItem> items){
		Collections.sort(items);
	}
	//???
	public Set<InventoryItem> getInventoryItemsUnderStdLevel(){
		return inventoryDAOFacade.getInventoryItemsUnderStdLevel();
	}

} 




