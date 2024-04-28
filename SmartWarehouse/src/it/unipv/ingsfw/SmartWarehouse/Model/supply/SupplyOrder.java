package model.supply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import exception.AuthorizationDeniedException;
import exception.supply.SupplyDoesNotExistException;
import facade.SupplyDAOFacade;
import model.operator.SupplyOperator;
import model.operator.WarehouseOperator;

public class SupplyOrder {   //ipotesi: una fornitura per ordine
	private int n_order;
	private Supply supply;
	private int qty;
	private double totPrice;
	private LocalDateTime date; 
	
	public SupplyOrder(int n_order, String ids_supply, int qty, double TotPrice, LocalDateTime date) {
		this.n_order = n_order;
		this.supply = SupplyDAOFacade.getInstance().findSupply(ids_supply);
		this.qty = qty;
		this.totPrice=TotPrice;
		this.date=date;
	}
	
	public int getN_order() {
		return n_order;
	}

	public void setN_order(int n_order) {
		this.n_order = n_order;
	}

	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getPrice() {
		return totPrice;
	}

	public void setPrice(double TotPrice) {
		this.totPrice = TotPrice;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return n_order+ " "+ supply.getID_Supply()+" "+qty+" "+totPrice+" "+date.format(formatter);
	} 

}
