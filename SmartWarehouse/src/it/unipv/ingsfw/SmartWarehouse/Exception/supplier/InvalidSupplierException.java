package exception.supplier;

public class InvalidSupplierException extends Exception {
	
	public InvalidSupplierException(String message) {
		super(message);
	}
	
	public InvalidSupplierException(String message, Throwable e) {
		super(message, e);
	}
 
	public String suggestAlternative(){
		return "try with another supplier";
	}
}
