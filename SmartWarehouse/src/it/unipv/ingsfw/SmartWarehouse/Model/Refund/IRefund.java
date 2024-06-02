//
package it.unipv.ingsfw.SmartWarehouse.Model.Refund;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;

public interface IRefund {
	 public boolean issueRefund() throws PaymentException;
	 public double getValue();
	
}
