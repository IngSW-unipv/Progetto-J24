package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.UUID;

import it.unipv.ingsfw.SmartWarehouse.Model.Item.InterfacciaItem;

public class ItemToBeReturned {
	private InterfacciaItem item;
	private String id;

	
	public ItemToBeReturned(InterfacciaItem item){
		this.item=item;
		this.id = identifierBuilder(UUID.randomUUID());
	}
	public ItemToBeReturned(InterfacciaItem item,String id){
		this.item=item;
		this.id = id;
	}
	public String identifierBuilder(UUID uuid) {
		StringBuilder idBuilder = new StringBuilder(item.getSku());
		idBuilder.append("-");
		idBuilder.append(uuid);
		return idBuilder.toString();
	}
	public InterfacciaItem getItem() {
		return item;
	}
	public void setItem(InterfacciaItem item) {
		this.item = item;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSkuItem() {
		return item.getSku();
	}
	public String getNomeItem() {
		return item.getNome();
	}
	
	public double getPriceItem(){
		return item.getPrice();
	}
	public String getID() {
		return id;
	}
	
	

}
