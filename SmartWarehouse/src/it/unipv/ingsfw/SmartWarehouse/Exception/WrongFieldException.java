package it.unipv.ingsfw.SmartWarehouse.Exception;


public class WrongFieldException extends Exception{

	private static String errorMessage = "Errata compilazione dei campi!";

	public WrongFieldException() {
		super(errorMessage);
	}

	
}
