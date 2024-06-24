//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class VoucherAdapter implements IRefund{

	private VoucherRefund vr; 

	public VoucherAdapter(VoucherRefund vr) {

		this.vr=vr;
	}

	@Override
	public boolean issueRefund() throws PaymentException {
		return vr.makeVoucher();

	}
	@Override
	public double getValue() {
		return vr.getValue();
	}
	@Override
	public String toString() {
		return vr.toString();
	}


}
