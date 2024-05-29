package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.time.LocalDateTime;

public class OrderLine { 
	private int id;
	private String sku;
	private int qty;
	private String email;
	private LocalDateTime date;
	private boolean picked;
	
	public OrderLine(int id, String sku, int qty, String email, LocalDateTime date, boolean pick) {
		this.id=id;
		this.sku=sku;
		this.qty=qty;
		this.email=email;
		this.date=date;
		this.picked=pick;
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

	public LocalDateTime getDate() {
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

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}
}
