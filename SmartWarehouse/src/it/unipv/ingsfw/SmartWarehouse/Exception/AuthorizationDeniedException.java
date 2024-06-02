package it.unipv.ingsfw.SmartWarehouse.Exception;

public class AuthorizationDeniedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthorizationDeniedException() {
		super("Authorization Denied");
	}
	
	public AuthorizationDeniedException(String message) {
		super(message);
	}
}
