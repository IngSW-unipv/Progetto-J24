package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.UUID;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

public class ItemToBeReturned {
	private IInventoryItem item;
	private String id;

	
	public ItemToBeReturned(IInventoryItem item){
		this.item=item;
		this.id = identifierBuilder(UUID.randomUUID());
	}
	public ItemToBeReturned(IInventoryItem item,String id){
		this.item=item;
		this.id = id;
	}
	public String identifierBuilder(UUID uuid) {
		StringBuilder idBuilder = new StringBuilder(item.getSku());
		idBuilder.append("-");
		idBuilder.append(uuid);
		return idBuilder.toString();
	}
	public IInventoryItem getItem() {
		return item;
	}
	public void setItem(IInventoryItem item) {
		this.item = item;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSkuItem() {
		return item.getSku();
	}
	
	public double getPriceItem(){
		return item.getPrice();
	}
	public String getID() {
		return id;
	}
	public String getDescription() {
		return item.getItem().getDescription();
	}
	
	

}