package it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class BankTransferAdapter implements IRefund {
		
		private BankTransfer br; 

		public BankTransferAdapter(BankTransfer br) {

			this.br=br;
		}
		@Override
		public void issueRefund() throws PaymentException {
			br.makeBankTransfer();
		}
		 public String toString() {
			return br.toString();
		}


}
	
	

