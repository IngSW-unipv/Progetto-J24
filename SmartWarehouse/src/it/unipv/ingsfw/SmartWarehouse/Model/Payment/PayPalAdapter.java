//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PayPalAdapter implements IPayment {
    private PayPal payPal;

    public PayPalAdapter(PayPal pp) {
        this.payPal = pp;
    }

	@Override
	public void makePayment(double amount,String senderEmail, String receiverEmail) {
		payPal.makePayPalPayment(amount,senderEmail,receiverEmail);
	}

  
}