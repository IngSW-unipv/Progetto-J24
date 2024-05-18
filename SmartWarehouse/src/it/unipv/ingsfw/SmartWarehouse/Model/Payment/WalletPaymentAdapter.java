package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public class WalletPaymentAdapter implements IPayment {
	    private WalletPayment walletPayment;

	    public WalletPaymentAdapter(WalletPayment wp) {
	        this.walletPayment = wp;
	    }

		@Override
		public void makePayment(double importo,String senderEmail,String receiverEmail) {
			// TODO Auto-generated method stub
			walletPayment.makeWalletPayment(importo,senderEmail,receiverEmail);
		}

	  
	
	
	

}
