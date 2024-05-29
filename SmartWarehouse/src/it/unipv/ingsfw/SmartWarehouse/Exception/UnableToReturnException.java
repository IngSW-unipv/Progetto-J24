package it.unipv.ingsfw.SmartWarehouse.Exception;

public class UnableToReturnException extends Exception {

	
	public UnableToReturnException() {
		super("Non puoi più restituire questo ordine: sono passati troppi giorni!");
	}
	public UnableToReturnException(String itemDescription) {
		super("Quantità massima restituibile raggiunta per l'articolo "+itemDescription);
	}

}
