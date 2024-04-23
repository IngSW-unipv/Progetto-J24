package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class VoucherAdapter implements IRefund{
	
	private VoucherRefund vr; 

	public VoucherAdapter(VoucherRefund vr) {

		this.vr=vr;
	}

	@Override
	public void issueRefund() {
		 vr.makeVoucher();
		
	}
	
	public String toString() {
		return vr.toString();
	}

}
