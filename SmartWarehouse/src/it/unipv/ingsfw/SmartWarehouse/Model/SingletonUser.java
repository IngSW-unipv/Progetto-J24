package model;

import model.operator.InventoryOperator;
import model.operator.SupplyOperator;
import model.operator.WarehouseOperator;

public class SingletonUser {
	private static SingletonUser instance;
	private WarehouseOperator op;
	
	private SingletonUser() {
		op=new SupplyOperator();
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
