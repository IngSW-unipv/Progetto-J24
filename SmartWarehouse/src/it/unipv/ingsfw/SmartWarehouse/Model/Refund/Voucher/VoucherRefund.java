package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

public class VoucherRefund {
	private double value;
	public VoucherRefund(double value) {
		this.value=value;
	}
	public boolean makeVoucher() {
		System.out.println("Emesso un voucher del valore di "+value);
		return true;
		/*
		 * try{
		 * Client client=(client) SingletonManager.getInstance().getUser
		 * client.setWallet(client.getWallet()+value);
		 * }catch{(classCastException e)
		 * e.printStackTrace();}
		 */
		// 
	}
	public String toString() {
		return "Voucher di "+value;
	}
	public double getValue() {
		return value;
	}
	


	

}
