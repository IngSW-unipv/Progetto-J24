package it.unipv.ingsfw.SmartWarehouse.Exception;


public class WrongFieldException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private static String errorMessage = "Errata compilazione dei campi!";

	public WrongFieldException() {
		super(errorMessage);
	}

	public void showPopup() {
		PopupManager.showPopup(errorMessage);
	}
	
	public void showPopup(String s) {
		PopupManager.showPopup(s);
	}
	
}