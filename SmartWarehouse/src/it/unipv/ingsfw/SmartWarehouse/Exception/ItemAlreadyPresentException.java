package it.unipv.ingsfw.SmartWarehouse.Exception;

public class ItemAlreadyPresentException extends Exception {
	private static final long serialVersionUID = 1L;

	public ItemAlreadyPresentException() {
        super("Item is already part of the Inventory");
    }

    public ItemAlreadyPresentException(String message) {
        super(message);
    }
}
