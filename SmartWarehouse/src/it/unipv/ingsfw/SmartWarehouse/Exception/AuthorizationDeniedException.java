package exception;

public class AuthorizationDeniedException extends Exception {

	public AuthorizationDeniedException() {
		super("Authorization Denied");
	}
}
