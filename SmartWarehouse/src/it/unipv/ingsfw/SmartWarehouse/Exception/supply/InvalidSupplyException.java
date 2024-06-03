package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class InvalidSupplyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidSupplyException(String message) {
		super(message);
	}
 
	public String suggestAlternative(){
		return "try with another supply";
	}
}
