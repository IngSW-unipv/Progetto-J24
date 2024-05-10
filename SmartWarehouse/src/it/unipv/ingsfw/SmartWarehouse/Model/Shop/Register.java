package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class Register {
	RegisterFacade reg;
	public Register(RegisterFacade reg) {
		this.reg=reg;
	}
	
	public void addOrd(Order o) {
		reg.addOrd(o);
	}
	
	public Order selectOrder(int id) {
		return reg.selectOrder(id);
	}
}
