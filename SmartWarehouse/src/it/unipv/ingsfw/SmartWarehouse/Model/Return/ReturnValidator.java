package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.time.LocalDate;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

/**
 * Utility class for validating return eligibility and returnable inventory items.
 */
public class ReturnValidator {
	private ReturnValidator() {
	}
	
    /**
     * Checks if the return service is within the allowable return period.
     * @param returnService the return service to be checked
     * @return true if the return service is within the allowable return period, false otherwise
     */
	public static boolean checkReturnability(ReturnService returnService){
		LocalDate criticalDate=returnService.getCriticalDate();
		LocalDate now=LocalDate.now();
		return now.isAfter(criticalDate) ? false : true;
	}

    /**
     * Checks if an inventory item can be returned: qty check
     * @param inventoryItem the inventory item to be checked
     * @param returnService the return service context
     * @return true if the inventory item can be returned, false otherwise
     */
	public static boolean checkReturnabilityOfInventoryItem(IInventoryItem inventoryItem,ReturnService returnService){
		int qtyYouWouldLikeToReturn=getQtyReturned(inventoryItem,returnService)+1;
		int qtyYouAreAllowedToReturn=returnService.getQtyYouAreAllowedToReturn(inventoryItem);
		return qtyYouWouldLikeToReturn > qtyYouAreAllowedToReturn ? false : true;
	}
	
    /**
     * Gets the quantity of the specified inventory item that has already been returned.
     * @param inventoryItem the inventory item
     * @param returnService the return service context
     * @return the quantity of the inventory item that has already been returned
     */
	private static int getQtyReturned(IInventoryItem inventoryItem, ReturnService returnService) {
		int count=0;
		for(IInventoryItem returnedItem:returnService.getReturnedItemsKeySet()) {
			if(returnedItem.getSku().equals(inventoryItem.getSku())) {
				count++; 
			}
		}
		return count;
	}
}
