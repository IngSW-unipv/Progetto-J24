package exception;

public class ItemNotFoundException extends Exception {
	private static String errorMessage = "Item not found";

	public ItemNotFoundException() {
		super(errorMessage);
	}
}
