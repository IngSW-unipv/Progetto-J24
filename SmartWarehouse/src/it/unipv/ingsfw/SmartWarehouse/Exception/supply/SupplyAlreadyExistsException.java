package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class SupplyAlreadyExistsException extends InvalidSupplyException {

	public SupplyAlreadyExistsException() {
		super("the supply entered already exist");
	}
	
}
