//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class WalletPaymentAdapter implements IPayment {
	    private WalletPayment walletPayment;

	    public WalletPaymentAdapter(WalletPayment wp) {
	        this.walletPayment = wp;
	    }

		@Override
		public void makePayment(double amount,String senderEmail,String receiverEmail) {
			walletPayment.makeWalletPayment(amount,senderEmail,receiverEmail);
		}

	  
	
	
	

}
