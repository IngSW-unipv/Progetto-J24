//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class WalletPayment { 
	
		public void makeWalletPayment(double amount,String senderEmail,String receiverEmail) throws PaymentException{
			Client c=(Client) SingletonUser.getInstance().getLoggedUser();
			if(c.getWallet()<amount) {
				throw new PaymentException();
			}
			else {
				c.setWallet(c.getWallet()-amount);
			}
			
		}
}
