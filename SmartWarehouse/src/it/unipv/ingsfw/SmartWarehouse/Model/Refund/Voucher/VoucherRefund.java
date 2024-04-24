package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

public class VoucherRefund {
	private double value;
	public VoucherRefund(double value) {
		this.value=value;
	}
	public void makeVoucher() {
		System.out.println("Emesso un voucher del valore di "+value);
		//logica di emissione del buono
		
	}
	public String toString() {
		return "Voucher di "+value;
	}
	


	

}
