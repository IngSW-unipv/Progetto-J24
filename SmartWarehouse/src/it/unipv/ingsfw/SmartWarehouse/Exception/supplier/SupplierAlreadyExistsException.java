package it.unipv.ingsfw.SmartWarehouse.Exception.supplier;

public class SupplierAlreadyExistsException extends InvalidSupplierException{

	private static final long serialVersionUID = 1L;

	public SupplierAlreadyExistsException() {
		super("The supplier you want to insert already exists");
	}
	
}
