package it.unipv.ingsfw.SmartWarehouse.Exception;

public class UnableToReturnException extends Exception {

	public UnableToReturnException(String itemDescription) {
		super("Quantità massima restituibile raggiunta per l'articolo "+itemDescription);
	}

}
