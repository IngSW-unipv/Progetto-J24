package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;

public interface IInventoryItem {
	public String getSku();
	public String getDescription();
	public double getPrice();
	public int getQty();
	public int getStdLevel();
	public Position getPos();
	public ItemDetails getDetails();
	public IInventoryItem addToInventory() throws AuthorizationDeniedException;
	public void delete() throws ItemNotFoundException, AuthorizationDeniedException;
	public boolean updateQty(int qty) throws ItemNotFoundException, IllegalArgumentException;
	public boolean increaseQty() throws ItemNotFoundException, IllegalArgumentException;
	public boolean decreaseQty() throws ItemNotFoundException, IllegalArgumentException;
	public List<Object[]> getSuppliersInfo() throws ItemNotFoundException;
}

  