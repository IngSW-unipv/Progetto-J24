//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;

public interface IPayment {
	public void makePayment(double amount,String senderEmail,String receiverEmail) throws PaymentException;

}
