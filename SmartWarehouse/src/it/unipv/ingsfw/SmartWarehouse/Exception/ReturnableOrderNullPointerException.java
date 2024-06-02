package it.unipv.ingsfw.SmartWarehouse.Exception;

public class ReturnableOrderNullPointerException extends NullPointerException {
	public ReturnableOrderNullPointerException(){
		super("Selezionare un ordine per continuare");
	}
}
