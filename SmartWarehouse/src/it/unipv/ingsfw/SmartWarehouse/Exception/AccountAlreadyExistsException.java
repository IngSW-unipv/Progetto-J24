package it.unipv.ingsfw.SmartWarehouse.Exception;

public class AccountAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	private static String errorMessage = "Account already exists";

	public AccountAlreadyExistsException() {
		
		super(errorMessage);

	}

	public void showPopup() {
		PopupManager.showPopup(errorMessage);
	}
	
}