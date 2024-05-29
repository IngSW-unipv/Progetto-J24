//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.*;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.*;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherAdapter;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;

public class RefundFactory {
	
		private static VoucherAdapter vadapter; //Variabile dove salvo l'adapter che il factory creerà.
		private static BankTransferAdapter badapter;
		private static final String V_PROPERTYNAME="voucher.adapter.class.name"; //Chiave del file di properties
		private static final String B_PROPERTYNAME="bonifico.adapter.class.name";
		
		public static VoucherAdapter getVoucherAdapter(VoucherRefund vr) {
			
			//if(vadapter==null) {
				String voucherAdaptClassName;

				try {
					Properties p = new Properties(System.getProperties());
					p.load(new FileInputStream("properties\\FactoryFile"));
					voucherAdaptClassName=p.getProperty(V_PROPERTYNAME); 
					//Ottengo il nome completo della classe dell'adapter che mi interessa

					Constructor c = Class.forName(voucherAdaptClassName).getConstructor(VoucherRefund.class);
					vadapter=(VoucherAdapter)c.newInstance(vr);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}

			return vadapter;
		}
		public static BankTransferAdapter getBankTransferAdapter(BankTransfer br) {
			
			//if(badapter==null) {
				String bonificoAdaptClassName;

				try {
					Properties p = new Properties(System.getProperties());
					p.load(new FileInputStream("properties\\FactoryFile"));
					bonificoAdaptClassName=p.getProperty(B_PROPERTYNAME); 
					//Ottengo il nome completo della classe dell'adapter che mi interessa

					Constructor c = Class.forName(bonificoAdaptClassName).getConstructor(BankTransfer.class);
					badapter=(BankTransferAdapter)c.newInstance(br);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}

			return badapter;
		}



}
