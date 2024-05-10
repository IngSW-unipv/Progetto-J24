package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class OrderLine { 
	private int id;
	private String sku;
	private int qty;
	private String email;
	private String date;
	
	public OrderLine(int id, String sku, int qty, String email, String date) {
		this.id=id;
		this.sku=sku;
		this.qty=qty;
		this.email=email;
		this.date=date;
	}

	public int getId() {
		return id;
	}

	public String getSku() {
		return sku;
	}

	public int getQty() {
		return qty;
	}

	public String getEmail() {
		return email;
	}

	public String getDate() {
		return date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
