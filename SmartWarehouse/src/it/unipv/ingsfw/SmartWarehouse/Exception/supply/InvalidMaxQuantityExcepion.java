package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class InvalidMaxQuantityExcepion extends InvalidSupplyException {
	
	public InvalidMaxQuantityExcepion() {
		super("the maximum quantity that can be purchased must be positive");
	}
}
