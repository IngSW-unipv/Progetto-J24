package it.unipv.ingsfw.SmartWarehouse.Exception;

public class UnableToReturnException extends Exception {

	
	public UnableToReturnException() {
		super("You can no longer return this order: too many days have passed!");
	}
	public UnableToReturnException(String itemDescription) {
		super("Maximum returnable quantity reached for the item "+itemDescription);
	}

}
