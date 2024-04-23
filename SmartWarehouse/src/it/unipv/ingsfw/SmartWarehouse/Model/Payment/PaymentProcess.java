package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PaymentProcess {
    private IPayment paymentMode;
    
    public PaymentProcess(IPayment paymentMode) {
    	this.paymentMode=paymentMode;
    }

    public void startPayment(double importo) {
        if (paymentMode != null) {
        	paymentMode.makePayment(importo);
        } else {
            System.out.println("Metodo di pagamento non impostato.");
        }
    }
}
