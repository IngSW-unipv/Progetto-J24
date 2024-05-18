package it.unipv.ingsfw.SmartWarehouse.Model.inventory;
public interface IInventoryItem {
	
	public Item getItem();
	public void setItem(Item item);
	public String getSku();
	public void setSku(String sku);
	public double getPrice();
	public void setPrice(double price);
	public int getQty();
	public void setQty(int qty);
	public int getStdLevel();
	public void setStdLevel(int stdLevel);
	public Position getPos();
	public void setPos(Position pos);
	public String toString();
}
