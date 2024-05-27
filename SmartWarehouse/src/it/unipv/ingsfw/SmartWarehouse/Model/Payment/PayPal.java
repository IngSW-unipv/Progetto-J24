package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PayPal {
	public void makePayPalPayment(double amount,String senderEmail,String receiverEmail) {
		// Logica per il pagamento tramite PayPal
		System.out.println("Pagamento di euro " + amount + " effettuato tramite PayPal.");
	}
}
