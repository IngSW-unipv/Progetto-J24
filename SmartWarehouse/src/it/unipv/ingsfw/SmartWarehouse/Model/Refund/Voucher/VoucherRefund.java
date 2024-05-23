package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

public class VoucherRefund {
	private double value;
	public VoucherRefund(double value) {
		this.value=value;
	}
	public void makeVoucher() {
		System.out.println("Emesso un voucher del valore di "+value);
		/*
		 * try{
		 * Client client=(client) SingletonManager.getInstance().getUser
		 * client.setWallet(client.getWallet()+value);
		 * }catch{(classCastException e)
		 * e.printStackTrace();}
		 */
		// 
		//logica di emissione del buono
	}
	public String toString() {
		return "Voucher di "+value;
	}
	public double getValue() {
		return value;
	}
	


	

}
