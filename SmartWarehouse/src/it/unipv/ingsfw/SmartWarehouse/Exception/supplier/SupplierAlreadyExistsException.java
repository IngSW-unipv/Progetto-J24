package it.unipv.ingsfw.SmartWarehouse.Exception.supplier;

public class SupplierAlreadyExistsException extends InvalidSupplierException{

	public SupplierAlreadyExistsException() {
		super("The supplier you want to insert already exists");
	}
	
}
