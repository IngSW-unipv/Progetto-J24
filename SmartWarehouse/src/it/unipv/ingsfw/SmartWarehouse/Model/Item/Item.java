package it.unipv.ingsfw.SmartWarehouse.Model.Item;

public class Item implements InterfacciaItem{
	private String sku;
	private String nome;
	private double price;
	
	public Item(String sku,String nome,double price)
	{
		this.sku=sku;
		this.nome=nome;
		this.price=price;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPrice()
	{
		return price;
	}
	
	@Override
	public String toString() {
		return sku;
		
	}
	
	
	

}
