package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;

public class CategoryStrategy implements IReplenishmentStrategy {
	private Category category;
	
	public CategoryStrategy(Category category) {
		this.category=category;
	}
	
	@Override
	public void replenish(List<InventoryItem> items) throws AuthorizationDeniedException {
		int q;
		for(InventoryItem i:items) { 
			if(i.getDetails().getCategory().equals(category)) {
				Supply s = SupplyDAOFacade.getInstance().getCheaperSupplyByInventoryItem(i);
				if(s!=null) { 
					q = i.getQty();
					while(q < i.getStdLevel()) {
						s.replenishSupply(Math.min(s.getMaxqty(), i.getStdLevel()-q));	
						q += Math.min(s.getMaxqty(), i.getStdLevel()-q);
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
