package it.unipv.ingsfw.SmartWarehouse.Model.user.operator;

import it.unipv.ingsfw.SmartWarehouse.Model.user.User;

public class WarehouseOperator extends User { 
	private String id;
	
	public WarehouseOperator (String name, String surname, String email, String id) {  
		super(name, surname, email);
		this.id= id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 

}








