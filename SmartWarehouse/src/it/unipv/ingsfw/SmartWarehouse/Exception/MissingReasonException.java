package it.unipv.ingsfw.SmartWarehouse.Exception;

public class MissingReasonException extends Exception{
	
	    public MissingReasonException() {
	        super("Motivazione mancante. Si prega di inserire una motivazione.");
	    }
	}
