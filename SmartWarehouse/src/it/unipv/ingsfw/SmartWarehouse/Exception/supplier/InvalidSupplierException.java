package it.unipv.ingsfw.SmartWarehouse.Exception.supplier;

public class InvalidSupplierException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidSupplierException(String message) {
		super(message);
	}
 
	public String suggestAlternative(){
		return "try with another supplier";
	}
}
