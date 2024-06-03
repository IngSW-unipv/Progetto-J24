package it.unipv.ingsfw.SmartWarehouse.Exception.supplier;

public class SupplierDoesNotExistException extends InvalidSupplierException {
	
	private static final long serialVersionUID = 1L;

	public SupplierDoesNotExistException() {
		super("The supplier entered does not esist");
	}
}
