package it.unipv.ingsfw.SmartWarehouse.Exception.supply;

public class InvalidSupplyException extends Exception {
	
	public InvalidSupplyException(String message) {
		super(message);
	}
 
	public String suggestAlternative(){
		return "try with another supply";
	}
}
