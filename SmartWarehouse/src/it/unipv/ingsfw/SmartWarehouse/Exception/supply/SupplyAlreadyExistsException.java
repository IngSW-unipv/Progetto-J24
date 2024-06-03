package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class SupplyAlreadyExistsException extends InvalidSupplyException {

	private static final long serialVersionUID = 1L;

	public SupplyAlreadyExistsException() {
		super("the supply entered already exist");
	}
	
}
