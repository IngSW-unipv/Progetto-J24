package it.unipv.ingsfw.SmartWarehouse.Exception;

public class EmptyKartExceptio extends Exception{
	public EmptyKartExceptio() {
		super("impossible to make order, the kart is empty.");
	}
}
