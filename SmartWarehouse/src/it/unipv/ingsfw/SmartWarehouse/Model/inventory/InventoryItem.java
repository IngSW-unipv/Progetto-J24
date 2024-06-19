package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.IRandomGenerator;
import it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator.RandomGenerator;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;

public class InventoryItem implements IInventoryItem, Comparable<InventoryItem> {
	private String description;
	private ItemDetails details;
	private String sku;
	private double price;
	private int qty; 
	private int stdLevel; 
	private Position pos; 

	/**
	 * Constructor used to create an object InventoryItem already present in the warehouse (in fact it already has the sku)
	 */
	public InventoryItem(String description, ItemDetails details, String sku, double price, int qty, int stdLevel,
			Position pos) {
		super();
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
		this.description = description;
		this.details = details;
		this.sku = createSku();
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
		if(InventoryDAOFacade.getInstance().checkIfPositionAlreadyUsed(pos)!=null) {
			throw new IllegalArgumentException("Position already used");
		}
		this.pos = pos;
	}
	
	/**
	 * Returns the sku created checking that it does not already exists
	 */
	private String createSku() {
		InventoryManager im=new InventoryManager();
		IRandomGenerator rg=new RandomGenerator();
		String sku= rg.generateRandomString(10);
		while(InventoryDAOFacade.getInstance().findInventoryItemBySku(sku)!=null) {
	    	sku=rg.generateRandomString(10);
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
	 
	//check that the operator is an InventoryOperator, only him can do this operation
	//method called on an inventoryItem created with the 2° constructor
	public IInventoryItem addToInventory() throws AuthorizationDeniedException {  
		checkAuthorization();
		InventoryDAOFacade.getInstance().insertItem(this);
		return this; 		
	} 
	 
	//check that the operator is an InventoryOperator
	//delete also the supply and supplyOrders associated
	public void delete() throws ItemNotFoundException, AuthorizationDeniedException { 
		checkAuthorization();
		if(InventoryDAOFacade.getInstance().findInventoryItemBySku(sku)!=null) { 
			for(Supply supply: SupplyDAOFacade.getInstance().findSupplyBySku(sku)) {
				SupplyDAOFacade.getInstance().deleteSupplyOrder(supply);
				SupplyDAOFacade.getInstance().deleteSupply(supply);
			}
			InventoryDAOFacade.getInstance().deleteItem(sku);
		} else {
			throw new ItemNotFoundException(); 
		}	
	}
	
	public boolean updateQty(int qty) throws ItemNotFoundException, IllegalArgumentException {
		this.setQty(qty);
		if(InventoryDAOFacade.getInstance().findInventoryItemBySku(sku)!=null) { 
			return InventoryDAOFacade.getInstance().updateInventoryItemQty(sku, qty);
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
	 
	//returns the suppliers with prices and maxQty, sorted by price 
	public List<Object[]> getSuppliersInfo() throws ItemNotFoundException {
		if(InventoryDAOFacade.getInstance().findInventoryItemBySku(sku)!=null) {
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
	
	public static void main(String[] args) {
		WarehouseOperator op = new WarehouseOperator(null, null, null, null);
		User u = op;
		System.out.println(u);
		System.out.println(op);
	}
		

}
