//
package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;


public class PaymentFactory {


	private static PayPalAdapter ppadapter; 
	private static WalletPaymentAdapter wpadapter;
	private static final String PP_PROPERTYNAME="paypal.adapter.class.name"; //Chiave del file di properties
	private static final String WP_PROPERTYNAME="wallet.adapter.class.name"; //Chiave del file di properties

	public static PayPalAdapter getPayPalAdapter(PayPal pp) {
		if(ppadapter==null) {
			String paypalAdaptClassName;

			try {
				Properties p = new Properties(System.getProperties());
				//properties\\FactoryFile
				p.load(new FileInputStream("properties/FactoryFile"));
				paypalAdaptClassName=p.getProperty(PP_PROPERTYNAME); 

				Constructor c = Class.forName(paypalAdaptClassName).getConstructor(PayPal.class);
				ppadapter=(PayPalAdapter)c.newInstance(pp);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ppadapter;
	}

	public static WalletPaymentAdapter getWalletPaymentAdapter(WalletPayment wp) {
		if(wpadapter==null) {
			String walletAdaptClassName;

			try {
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("properties/FactoryFile"));
				walletAdaptClassName=p.getProperty(WP_PROPERTYNAME); 

				Constructor c = Class.forName(walletAdaptClassName).getConstructor(WalletPayment.class);
				wpadapter=(WalletPaymentAdapter)c.newInstance(wp);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wpadapter;
	}
}


