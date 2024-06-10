//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class WalletPayment { 
	
		public void makeWalletPayment(double amount,String senderEmail,String receiverEmail) throws PaymentException{
			Client c=(Client) SingletonManager.getInstance().getLoggedUser();
			if(c.getWallet()<amount) {
				throw new PaymentException();
			}
			else {
				c.setWallet(c.getWallet()-amount);
			}
			
			System.out.println("Pagamento di euro " + amount + " effettuato tramite Wallet.");
		}
}
