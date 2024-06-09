package it.unipv.ingsfw.SmartWarehouse.Exception;

public class WrongOperatorException extends Exception{
	private static String errorMessage = "Operator not valid";
	public WrongOperatorException() {
		super(errorMessage);

	}
	

}
