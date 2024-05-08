package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
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
	
	//to try
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Description: "+description+", "+details.toString();
	}

	public InventoryItem addToInventory(WarehouseOperator op, double price, int stdLevel, Position pos) throws IllegalArgumentException, EmptyFieldException, AuthorizationDeniedException {  
		try { 
			InventoryOperator io= (InventoryOperator)op;
			InventoryDAOFacade im=InventoryDAOFacade.getInstance();
			IRandomGenerator rg=new RandomGenerator();
		
			if (price<=0) {
				throw new IllegalArgumentException("prezzo inserito non valido");
			}
			
			if (stdLevel<20) {
				throw new IllegalArgumentException("stdLevel deve essere almeno 20");
			}
			
			if(im.checkIfPositionAlreadyUsed(pos)!=null) {
				throw new IllegalArgumentException("posizione già occupata");
			}
			 
			String sku= rg.generateRandomString(10);
			while(im.findInventoryItemBySku(sku)!=null) {
		    	sku=rg.generateRandomString(10);
		    }
			
			InventoryItem in=new InventoryItem(this, sku, price, 0, stdLevel, pos);
			im.insertItem(in);
			return in; 
			
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}		
	}
	 
	
}





