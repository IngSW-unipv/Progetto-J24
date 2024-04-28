package exception.supplier;

public class SupplierDoesNotExistException extends InvalidSupplierException {
	
	public SupplierDoesNotExistException() {
		super("The supplier entered does not esist");
	}
}
