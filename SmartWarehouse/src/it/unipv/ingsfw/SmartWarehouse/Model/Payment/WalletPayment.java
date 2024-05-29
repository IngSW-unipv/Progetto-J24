//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;

public class WalletPayment { 
	
		public void makeWalletPayment(double amount,String senderEmail,String receiverEmail) {
			// Logica per il pagamento tramite PayPal
			Client c=SingletonManager.getInstance().getClientDAO().selectClient(senderEmail);
			if(c.getWallet()<amount) {
				System.err.println("GESTIRE L'ECCEZIONE DI PAGAMENTO");
			}
			else {
				c.setWallet(c.getWallet()-amount);
			}
			
			System.out.println("Pagamento di euro " + amount + " effettuato tramite Wallet.");
		}
}
