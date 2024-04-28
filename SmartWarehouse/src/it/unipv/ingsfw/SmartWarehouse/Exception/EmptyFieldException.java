package exception;

public class EmptyFieldException extends Exception {
	
	public EmptyFieldException() {
		super("one or more fields are empty");
	}
}
