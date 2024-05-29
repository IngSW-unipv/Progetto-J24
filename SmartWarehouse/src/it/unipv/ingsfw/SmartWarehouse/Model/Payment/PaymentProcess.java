//
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

    public boolean startPayment(double amount) throws PaymentException{
    	boolean result=false;
    	if(amount<0||paymentMode==null||senderEmail==null||receiverEmail==null||senderEmail.isBlank()||senderEmail.isEmpty()
    			||receiverEmail.isBlank()||receiverEmail.isEmpty()) {
    		throw new PaymentException();
    	}
    	else {
    		paymentMode.makePayment(amount,senderEmail,receiverEmail);
        	result=true;
    	}
        return result;
    }
}
