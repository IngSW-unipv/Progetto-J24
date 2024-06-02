//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class VoucherAdapter implements IRefund{

	private VoucherRefund vr; 

	public VoucherAdapter(VoucherRefund vr) {

		this.vr=vr;
	}

	@Override
	public boolean issueRefund() {
		return vr.makeVoucher();

	}
	public double getValue() {
		return vr.getValue();
	}

	public String toString() {
		return vr.toString();
	}



}
