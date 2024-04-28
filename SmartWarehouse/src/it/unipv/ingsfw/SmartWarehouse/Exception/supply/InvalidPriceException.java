package exception.supply;

public class InvalidPriceException extends InvalidSupplyException {
	
	public InvalidPriceException() {
		super("the price inserted is not valid");
	}
}
