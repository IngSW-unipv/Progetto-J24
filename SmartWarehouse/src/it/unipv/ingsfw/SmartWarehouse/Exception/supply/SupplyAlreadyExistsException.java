package exception.supply;

public class SupplyAlreadyExistsException extends InvalidSupplyException {

	public SupplyAlreadyExistsException() {
		super("the supply entered already exist");
	}
	
}
