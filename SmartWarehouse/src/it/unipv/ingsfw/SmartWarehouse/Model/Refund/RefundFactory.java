//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.*;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherAdapter;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;

public class RefundFactory {
	
		private static VoucherAdapter vadapter;
		private static BankTransferAdapter badapter;
		private static final String V_PROPERTYNAME="voucher.adapter.class.name"; 
		private static final String B_PROPERTYNAME="bonifico.adapter.class.name";
		
		public static VoucherAdapter getVoucherAdapter(VoucherRefund vr) {
				String voucherAdaptClassName;
				try {
					Properties p = new Properties(System.getProperties());
					p.load(new FileInputStream("properties/FactoryFile"));
					voucherAdaptClassName=p.getProperty(V_PROPERTYNAME); 

					Constructor c = Class.forName(voucherAdaptClassName).getConstructor(VoucherRefund.class);
					vadapter=(VoucherAdapter)c.newInstance(vr);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return vadapter;
		}
		public static BankTransferAdapter getBankTransferAdapter(BankTransfer br) {
				String bonificoAdaptClassName;

				try {
					Properties p = new Properties(System.getProperties());
					p.load(new FileInputStream("properties/FactoryFile"));
					bonificoAdaptClassName=p.getProperty(B_PROPERTYNAME); 

					Constructor c = Class.forName(bonificoAdaptClassName).getConstructor(BankTransfer.class);
					badapter=(BankTransferAdapter)c.newInstance(br);

				} catch (Exception e) {
					e.printStackTrace();
				}
			return badapter;
		}



}
