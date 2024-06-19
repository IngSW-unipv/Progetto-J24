package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;

public class CategoryStrategy implements IReplenishmentStrategy {
	private Category category;
	
	public CategoryStrategy(Category category) {
		this.category=category;
	}
	
	/**
	 * The method replenishes the InventoryItems of the indicated category until the threshold (stdLevel) 
	 * from the cheaper Supplier. 
	 * If an InventoryItem has not a Supplier associated it will not be replenished.
	 */
	@Override
	public void replenish(List<IInventoryItem> items) throws AuthorizationDeniedException {
		int q;
		int orderQty;
		for(IInventoryItem i:items) { 
			if(i.getDetails().getCategory().equals(category)) {
				Supply s = SupplyDAOFacade.getInstance().getCheaperSupplyByInventoryItem(i);
				if(s!=null) { 
					q = i.getQty();
					while(q < i.getStdLevel()) {
						orderQty = Math.min(s.getMaxqty(), i.getStdLevel()-q);
						s.replenishSupply(orderQty);	
						q += orderQty;
					}
				}
			}
		}	
	}
	
	@Override
	public String getName() {
		return "Category Strategy";
	}
}
