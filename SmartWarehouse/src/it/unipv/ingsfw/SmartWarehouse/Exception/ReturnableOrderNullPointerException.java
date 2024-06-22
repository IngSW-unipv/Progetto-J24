package it.unipv.ingsfw.SmartWarehouse.Exception;

public class ReturnableOrderNullPointerException extends NullPointerException {
	public ReturnableOrderNullPointerException(){
		super("Select an order to proceed");
	}
}
