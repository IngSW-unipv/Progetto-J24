package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.ArrayList;

public class RegisterFacade { 
	private Register reg;
	public static RegisterFacade istance;
	
	private RegisterFacade() {
		this.reg=new Register();
	}
	public static RegisterFacade getIstance() {
		if(istance == null) {
			istance=new RegisterFacade();
		}
		return istance;
	}
	
	public void addOrd(Order o) {
		reg.addOrd(o);
	}
	
	public Order selectOrder(int id) {
		return reg.selectOrder(id);
	}
	
	public ArrayList<Order> selectOrderWhereClient(String email){
		return reg.selectOrderWhereClient(email);
	}
}
