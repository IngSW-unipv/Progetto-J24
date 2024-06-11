package it.unipv.ingsfw.SmartWarehouse.Exception;


public class AccountAlreadyExistsException extends Exception{
	public AccountAlreadyExistsException() {
        super("account already exists");
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
	
}
