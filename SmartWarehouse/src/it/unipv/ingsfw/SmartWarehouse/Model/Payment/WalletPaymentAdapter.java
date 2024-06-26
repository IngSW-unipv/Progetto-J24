//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;

public class WalletPaymentAdapter implements IPayment {
	    private WalletPayment walletPayment;

	    public WalletPaymentAdapter(WalletPayment wp) {
	        this.walletPayment = wp;
	    }

		@Override
		public void makePayment(double amount,String senderEmail,String receiverEmail) throws PaymentException {
			walletPayment.makeWalletPayment(amount,senderEmail,receiverEmail);
		}
		

	  
	
	
	

}
