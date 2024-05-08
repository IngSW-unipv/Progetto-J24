package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.WarehouseOperator;


//useful to InventoryDAO, POJO
public class InventoryItem implements Comparable<InventoryItem>{
	private Item item;
	private String sku;
	private double price;
	private int qty; 
	private int stdLevel; 
	private Position pos; 
	private InventoryDAOFacade inventoryDAOFacade;
	
	public InventoryItem(Item item, String sku, double price, int qty, int stdLevel, Position pos) {
		this.item=item;
		this.sku=sku;
		this.price = price;
		this.qty = qty;
		this.stdLevel = stdLevel; 
		this.pos = pos;
		inventoryDAOFacade=InventoryDAOFacade.getInstance();
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getStdLevel() {
		return stdLevel;
	}

	public void setStdLevel(int stdLevel) {
		this.stdLevel = stdLevel;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	@Override
	public String toString() {
		return sku+ ", "+item.toString()+", "+price+", "+ qty+", "+stdLevel+", "+pos.toString();
	}
	 
	//check that the operator is an InventoryOperator
	public void delete(WarehouseOperator op) throws ItemNotFoundException, AuthorizationDeniedException {
		try {
			InventoryOperator InventoryOperator = (InventoryOperator) op; 
			if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
				inventoryDAOFacade.deleteItem(sku);
			} else {
				 throw new ItemNotFoundException(); 
			}	
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
	public boolean updateQty(int qty) throws ItemNotFoundException, IllegalArgumentException {
		if (qty<0) {
			throw new IllegalArgumentException("quantity can't be negative");
		}
		this.setQty(qty);
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
			return inventoryDAOFacade.updateInventoryItemQty(sku, qty);
		} else { 
	        throw new ItemNotFoundException(); 
		}	  
	}
	
	//increase the quantity by 1
	public boolean increaseQty() throws ItemNotFoundException, IllegalArgumentException {
		int newQty=qty+1;
		return this.updateQty(newQty);
	} 
	
	public boolean decreaseQty() throws ItemNotFoundException, IllegalArgumentException {
		int newQty=qty-1;
		return this.updateQty(newQty);
	}
	 
	//returns the suppliers of the intentoryItem order by price
	//ItemNotFoundException is useful?
	public List<Object[]> getSuppliersInfo() throws ItemNotFoundException {
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) {
			return InventoryDAOFacade.getInstance().getSuppliersInfo(this);
		} else {
			throw new ItemNotFoundException();
		} 
	}
	 
	@Override
	public int compareTo(InventoryItem o) {
		int diffThis = this.stdLevel-this.qty;
        int diffOther = o.getStdLevel()-o.getQty();

        //descending sort with the difference between stdLevel and quantity
        return Integer.compare(diffOther, diffThis);
	}
	
	

}
