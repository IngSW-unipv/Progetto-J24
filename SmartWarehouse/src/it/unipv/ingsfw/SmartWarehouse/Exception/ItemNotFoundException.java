package it.unipv.ingsfw.SmartWarehouse.Exception;

public class ItemNotFoundException extends Exception {
	private static String errorMessage = "Item not found";

	public ItemNotFoundException() {
		super(errorMessage);
	}
}
