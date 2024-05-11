package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.WarehouseOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.*;

public class Item { 
	private String description;
	private ItemDetails details;
	
	public Item(String description, ItemDetails details) {
		this.description = description;
		this.setItemDetails(details);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ItemDetails getItemDetails() {
		return details;
	}

	public void setItemDetails(ItemDetails details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Description: "+description+", "+details.toString();
	}
	
	//check that the operator is an InventoryOperator, only him can do this operation
	public InventoryItem addToInventory(double price, int stdLevel, Position pos) throws IllegalArgumentException, EmptyFieldException, AuthorizationDeniedException {  
		try { 
			WarehouseOperator op=SingletonUser.getInstance().getOp();
			InventoryOperator io=(InventoryOperator)op;
			InventoryManager im=new InventoryManager();
		
			if (price<=0) {
				throw new IllegalArgumentException("Price must be a positive number");
			}
			
			if (stdLevel<20) {
				throw new IllegalArgumentException("StdLevel must be almost 20");
			}
			
			if(im.findInventoryItemByPosition(pos)!=null) {
				throw new IllegalArgumentException("Position already used");
			}
			
			IRandomGenerator rg=new RandomGenerator();
			String sku= rg.generateRandomString(10);
			while(im.findInventoryItem(sku)!=null) {
		    	sku=rg.generateRandomString(10);
		    }
			
			InventoryItem in=new InventoryItem(this, sku, price, 0, stdLevel, pos);
			InventoryDAOFacade.getInstance().insertItem(in);
			return in; 
			
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}		
	} 
	 
	
}





