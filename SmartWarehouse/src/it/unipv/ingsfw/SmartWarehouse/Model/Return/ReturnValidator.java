package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

public class ReturnValidator {
	private ReturnValidator() {
	}
	public static boolean checkReturnability(ReturnService returnService){
		LocalDate criticalDate=returnService.getCriticalDate();
		if (LocalDate.now().isAfter(criticalDate)) {
			return false;
		}
		return true;
	}
	public static boolean checkReturnabilityOfInventoryItem(IInventoryItem inventoryItem,ReturnService returnService){
		int qtyYouWouldLikeToReturn=getQtyReturned(inventoryItem,returnService)+1;
		int qtyYouAreAllowedToReturn=returnService.getQtyYouAreAllowedToReturn(inventoryItem);
		
		if(qtyYouAreAllowedToReturn<qtyYouWouldLikeToReturn) {
			return false;
		}
		return true;
	}
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
