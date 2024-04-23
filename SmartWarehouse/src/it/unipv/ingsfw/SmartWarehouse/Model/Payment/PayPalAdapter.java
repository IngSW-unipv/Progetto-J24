package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PayPalAdapter implements IPayment {
    private PayPal payPal;

    public PayPalAdapter(PayPal pp) {
        this.payPal = pp;
    }

	@Override
	public void makePayment(double importo) {
		// TODO Auto-generated method stub
		payPal.makePayPalPayment(importo);
		
	}

  
}