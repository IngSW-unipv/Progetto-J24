package it.unipv.ingsfw.SmartWarehouse.Exception;


public class EmptyFieldException extends Exception {

	private static final long serialVersionUID = 1L;
	private static String errorMessage = "there are some empty field";

	public EmptyFieldException() {
		super(errorMessage);
	}


}





