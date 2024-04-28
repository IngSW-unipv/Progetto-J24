package exception.supply;

public class SupplyDoesNotExistException extends InvalidSupplyException {

	public SupplyDoesNotExistException() {
		super("The supply entered does not exist");
	}

}
