package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;

public class PaymentProcess {
    private IPayment paymentMode;
    private String senderEmail;
    private String receiverEmail;
    
    public PaymentProcess(IPayment paymentMode,String senderEmail,String receiverEmail) {
    	this.paymentMode=paymentMode;
    	this.senderEmail=senderEmail;
    	this.receiverEmail=receiverEmail;
    }

    public void startPayment(double importo) throws PaymentException{
        if (paymentMode != null) {
        	paymentMode.makePayment(importo,senderEmail,receiverEmail);
        } else {
            throw new PaymentException();
        }
    }
}
