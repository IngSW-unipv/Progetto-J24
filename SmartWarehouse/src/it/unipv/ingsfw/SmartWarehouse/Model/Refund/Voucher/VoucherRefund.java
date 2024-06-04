//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class VoucherRefund {
	private double value;
	public VoucherRefund(double value) {
		this.value=value;
	}
	public boolean makeVoucher() {
		boolean result=true;
		System.out.println("Emesso un voucher del valore di "+value);
		try {
			Client client=(Client) SingletonManager.getInstance().getLoggedUser();
			client.setWallet(client.getWallet()+value);
		}
		catch(ClassCastException e) {
			result=false;
			e.printStackTrace();
			return result;
		}
		return result;
		

		
	}
	public String toString() {
		return "Voucher di "+value;
	}
	public double getValue() {
		return value;
	}





}
