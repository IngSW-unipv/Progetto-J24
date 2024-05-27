package it.unipv.ingsfw.SmartWarehouse.Exception.supplier;

public class InvalidSupplierException extends Exception {
	
	public InvalidSupplierException(String message) {
		super(message);
	}
 
	public String suggestAlternative(){
		return "try with another supplier";
	}
}
