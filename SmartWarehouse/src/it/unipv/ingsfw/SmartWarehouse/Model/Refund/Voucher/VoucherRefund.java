//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PayPal;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class VoucherRefund {
	private double value;
	private String senderEmail;
	private String receiverEmail;
	public VoucherRefund(double value,String senderEmail,String receiverEmail) {
		this.value=value;
		this.senderEmail=senderEmail;
		this.receiverEmail=receiverEmail;
	}
	public boolean makeVoucher() throws PaymentException {
		PaymentProcess pp= new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),senderEmail,receiverEmail);
		if( pp.startPayment(value) ) {
			Client client=(Client) SingletonUser.getInstance().getLoggedUser();
			client.setWallet(client.getWallet()+value);
			return true;
		}
		else {
			return false;
		}
	}
	public String toString() {
		return "Voucher value: "+value;
	}
	public double getValue() {
		return value;
	}
}
