package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;


public class PaymentFactory {


	private static PayPalAdapter ppadapter; 
	private static final String PP_PROPERTYNAME="paypal.adapter.class.name"; //Chiave del file di properties

	public static PayPalAdapter getPayPalAdapter() {
		PayPal pp=new PayPal();
		if(ppadapter==null) {
			String paypalAdaptClassName;

			try {
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("src\\FactoryFile"));
				paypalAdaptClassName=p.getProperty(PP_PROPERTYNAME); 
				//Ottengo il nome completo della classe dell'adapter che mi interessa

				Constructor c = Class.forName(paypalAdaptClassName).getConstructor(PayPal.class);
				ppadapter=(PayPalAdapter)c.newInstance(pp);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(ppadapter);
		return ppadapter;

	}
}

