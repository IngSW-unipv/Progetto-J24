package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemAlreadyPresentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.IRandomGenerator;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.RandomGenerator;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.InventoryOperator;

public class InventoryItem implements IInventoryItem {
	private String description;
	private ItemDetails details;
	private String sku;
	private double price;
	private int qty; 
	private int stdLevel; 
	private Position pos; 
	private InventoryDAOFacade inventoryDAOFacade;

	/**
	 * Constructor used to create an object InventoryItem already present in the warehouse (in fact it already has the sku)
	 */
	public InventoryItem(String description, ItemDetails details, String sku, double price, int qty, int stdLevel,
			Position pos) {
		super();
		inventoryDAOFacade=InventoryDAOFacade.getInstance();
		this.description = description;
		this.details = details;
		this.sku = sku;
		this.price=price;
		this.qty=qty;
		this.stdLevel=stdLevel;
		this.pos=pos;
	}

	/**
	 * Constructor used to create an object InventoryItem which does not yet exists in the warehouse, 
	 */
	public InventoryItem(String description, ItemDetails details, double price, int stdLevel, Position pos) throws IllegalArgumentException {
		super();
		inventoryDAOFacade=InventoryDAOFacade.getInstance();
		this.description = description;
		this.details = details;
		this.sku = createSku(new RandomGenerator());
		this.setPrice(price);
		this.qty=0;
		this.setStdLevel(stdLevel);
		this.setPos(pos);
	}
	
	public String getDescription() {
		return description;
	}

	public ItemDetails getDetails() {
		return details;
	}
	
	public String getSku() {
		return sku;
	}

	public double getPrice() {
		return price;
	}

	public int getQty() {
		return qty;
	}
	
	public int getStdLevel() {
		return stdLevel;
	}
	
	public Position getPos() {
		return pos;
	}
	public Position getPosBySku() throws ItemNotFoundException {
	    IInventoryItem item = inventoryDAOFacade.findInventoryItemBySku(sku);
	    if (item != null) {
	        return item.getPos();
	    } else {
	        throw new ItemNotFoundException("Item not found for SKU: " + sku);
	    }
	}

	
	/**
	 * Sets the price only if it is positive
	 * @param price
	 * @throws IllegalArgumentException
	 */
	public void setPrice(double price) throws IllegalArgumentException {
		if (price <= 0) {
			throw new IllegalArgumentException("Price cannot be negative, item: " + sku);
		}
		this.price = price;
	}

	/**
	 * Sets the quantity only if it is not negative
	 * @param qty
	 * @throws IllegalArgumentException
	 */
	public void setQty(int qty) throws IllegalArgumentException {
		if (qty < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative, item: " + sku);
		}
		this.qty=qty;
	}

	/**
	 * Sets stdLevel, there is a minimum level that is read from files in the class SmartWarehouseInfoPoint
	 * @param stdLevel
	 * @throws IllegalArgumentException
	 */
	public void setStdLevel(int stdLevel) throws IllegalArgumentException {
		if (stdLevel < SmartWarehouseInfoPoint.getMin_Std_Level()) {
			throw new IllegalArgumentException("StdLevel must be almost: "+SmartWarehouseInfoPoint.getMin_Std_Level());
		}
		this.stdLevel = stdLevel;
	}

	/**
	 * Sets the position of the InventoryItem
	 * @param pos
	 * @throws IllegalArgumentException
	 */
	public void setPos(Position pos) throws IllegalArgumentException {
		if(pos==null) {
			throw new IllegalArgumentException("Position null");
		}
		if(inventoryDAOFacade.checkIfPositionAlreadyUsed(pos)!=null) {
			throw new IllegalArgumentException("Position already used");
		}
		this.pos = pos;
	}
	
	/**
	 * Returns the sku created checking that it does not already exists.
	 * Sku length is read from file in SmartWarehouseInfoPoint class.
	 */
	private String createSku(IRandomGenerator rg) {
		int skusize = SmartWarehouseInfoPoint.getSkuSize();
		String sku= rg.generateRandomString(skusize);
		while(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) {
	    	sku=rg.generateRandomString(skusize);
	    }
		return sku;
	}
	
	private void checkAuthorization() throws AuthorizationDeniedException {
		try {
			InventoryOperator op= (InventoryOperator)SingletonUser.getInstance().getLoggedUser();
		} catch (ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
	/**
	 * Checks that the operator is an InventoryOperator.
	 * Adds the item to the Inventory if it is not already present.
	 */
	public IInventoryItem addToInventory() throws AuthorizationDeniedException, ItemAlreadyPresentException{  
		checkAuthorization();
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) {
			throw new ItemAlreadyPresentException();
		}
		inventoryDAOFacade.insertItem(this);
		return this; 		
	} 
	 
	/**
	 * Checks that the operator is an InventoryOperator.
	 * Delete the item from the Inventory after checking the presence, 
	 * delete also supply and supplyOrders associated so as not to violate the integrity constraint.
	 */
	public void delete() throws ItemNotFoundException, AuthorizationDeniedException { 
		checkAuthorization();
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
			for(Supply supply: SupplyDAOFacade.getInstance().findSupplyBySku(sku)) {
				SupplyDAOFacade.getInstance().deleteSupplyOrder(supply);
				SupplyDAOFacade.getInstance().deleteSupply(supply);
			}
			inventoryDAOFacade.deleteItem(sku);
		} else {
			throw new ItemNotFoundException(); 
		}	
	}
	
	public boolean updateQty(int qty) throws ItemNotFoundException, IllegalArgumentException {
		this.setQty(qty);
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
			return inventoryDAOFacade.updateInventoryItemQty(sku, qty);
		} else { 
	        throw new ItemNotFoundException();  
		}	  
	} 
	
	/**
	 * increase the quantity by 1 compared to the quantity currently present in the database.
	 */
	public boolean increaseQty() throws ItemNotFoundException, IllegalArgumentException {
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
			int newQty = inventoryDAOFacade.getInventoryItemQty(this) + 1;
			this.setQty(newQty);
			return inventoryDAOFacade.updateInventoryItemQty(sku, qty);
		} else { 
	        throw new ItemNotFoundException();  
		}		
	} 

	/**
	 * decrease the quantity by 1 compared to the quantity currently present in the database.
	 */
	public boolean decreaseQty() throws ItemNotFoundException, IllegalArgumentException {
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) { 
			int newQty = inventoryDAOFacade.getInventoryItemQty(this) - 1;
			this.setQty(newQty);
			return inventoryDAOFacade.updateInventoryItemQty(sku, qty);
		} else { 
	        throw new ItemNotFoundException();  
		}	
	}
	
	/**
	 * returns the InventoryItem suppliers with prices and maxQty, sorted by price
	 */
	public List<Object[]> getSuppliersInfo() throws ItemNotFoundException {
		if(inventoryDAOFacade.findInventoryItemBySku(sku)!=null) {
			return inventoryDAOFacade.getSuppliersInfo(this);
		} else {
			throw new ItemNotFoundException();
		} 
	}
	  
	@Override
	public int compareTo(IInventoryItem o) {
		int diffThis = this.stdLevel-this.qty;
        int diffOther = o.getStdLevel()-o.getQty();
        return Integer.compare(diffOther, diffThis);
	}
	
}
