package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class PaymentProcess {
    private IPayment paymentMode;
    private String senderEmail;
    private String receiverEmail;
    
    public PaymentProcess(IPayment paymentMode,String senderEmail,String receiverEmail) {
    	this.paymentMode=paymentMode;
    	this.senderEmail=senderEmail;
    	this.receiverEmail=receiverEmail;
    }

    public void startPayment(double importo) {
        if (paymentMode != null) {
        	paymentMode.makePayment(importo,senderEmail,receiverEmail);
        } else {
            System.out.println("Metodo di pagamento non impostato.");
        }
    }
}
