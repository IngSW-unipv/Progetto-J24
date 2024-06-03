package it.unipv.ingsfw.SmartWarehouse.Exception;

public class ItemNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
        super("Item not found");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
