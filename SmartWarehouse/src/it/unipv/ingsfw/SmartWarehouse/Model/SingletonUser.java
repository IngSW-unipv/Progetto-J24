package it.unipv.ingsfw.SmartWarehouse.Model;

import it.unipv.ingsfw.SmartWarehouse.Model.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.operator.WarehouseOperator;

public class SingletonUser {
	private static SingletonUser instance;
	private WarehouseOperator op;
	
	private SingletonUser() {
		op=new InventoryOperator();
	}   
	
	public static synchronized SingletonUser getInstance() {
		if(instance==null) {
			instance = new SingletonUser();
		}
		return instance;
	}
	
	public WarehouseOperator getOp() {
		return op;
	}
}
