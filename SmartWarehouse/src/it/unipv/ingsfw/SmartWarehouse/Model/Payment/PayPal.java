package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PayPal {
	public void makePayPalPayment(double importo,String senderEmail,String receiverEmail) {
		// Logica per il pagamento tramite PayPal
		System.out.println("Pagamento di euro " + importo + " effettuato tramite PayPal.");
	}
}
