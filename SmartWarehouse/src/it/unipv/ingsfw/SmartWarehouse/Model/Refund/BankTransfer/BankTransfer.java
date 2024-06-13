//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.*;

public class BankTransfer {
	private double value;
	private String senderEmail;
	private String receiverEmail;
	public BankTransfer(double value,String senderEmail,String receiverEmail) {
		this.value=value;
		this.senderEmail=senderEmail;
		this.receiverEmail=receiverEmail;
	}
	public boolean makeBankTransfer() throws PaymentException {
		PaymentProcess pp= new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),senderEmail,receiverEmail);
        return pp.startPayment(value);
	}
	public double getValue() {
		return value;
	}
	public String toString() {
		return "Bonifico di "+getValue();
	}

}
