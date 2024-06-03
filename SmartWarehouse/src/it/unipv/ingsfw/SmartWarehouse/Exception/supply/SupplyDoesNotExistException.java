package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class SupplyDoesNotExistException extends InvalidSupplyException {

	private static final long serialVersionUID = 1L;

	public SupplyDoesNotExistException() {
		super("The supply entered does not exist");
	}

}
