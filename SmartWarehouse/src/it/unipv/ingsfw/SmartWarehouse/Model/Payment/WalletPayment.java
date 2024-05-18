package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;

public class WalletPayment { 
	
		public void makeWalletPayment(double importo,String senderEmail,String receiverEmail) {
			// Logica per il pagamento tramite PayPal
			Client c=SingletonManager.getInstance().getClientDAO().selectClient(senderEmail);
			if(c.getWallet()<importo) {
				System.err.println("GESTIRE L'ECCEZIONE DI PAGAMENTO");
			}
			else {
				c.setWallet(c.getWallet()-importo);
			}
			
			System.out.println("Pagamento di euro " + importo + " effettuato tramite Wallet.");
		}
}
