package exception.supply;

public class InvalidSupplyException extends Exception {
	
	public InvalidSupplyException(String message) {
		super(message);
	}
	
	public InvalidSupplyException(String message, Throwable e) {
		super(message, e);
	}
 
	public String suggestAlternative(){
		return "try with another supply";
	}
}
