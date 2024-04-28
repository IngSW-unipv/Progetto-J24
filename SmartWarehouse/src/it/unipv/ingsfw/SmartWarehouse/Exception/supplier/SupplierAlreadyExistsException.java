package exception.supplier;

public class SupplierAlreadyExistsException extends InvalidSupplierException{

	public SupplierAlreadyExistsException() {
		super("The supplier you want to insert already exists");
	}
	
	
}
