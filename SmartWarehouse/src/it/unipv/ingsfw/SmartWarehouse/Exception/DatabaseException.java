package it.unipv.ingsfw.SmartWarehouse.Exception;


public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private static String errorMessage = "problem with the database";
	public DatabaseException() {
		
		super(errorMessage);

	}
	

}
