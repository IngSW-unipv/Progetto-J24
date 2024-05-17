package it.unipv.ingsfw.SmartWarehouse.Exception;

public class PaymentException extends Exception{
	 public PaymentException() {
		super("errore durante il pagamento, riprova");
	}
}
