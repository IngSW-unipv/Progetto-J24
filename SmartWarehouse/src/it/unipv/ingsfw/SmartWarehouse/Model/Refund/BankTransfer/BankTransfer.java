package it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.*;

public class BankTransfer {
	private double value;
	public BankTransfer(double value) {
		this.value=value;
	}
	public void makeBankTransfer() {
		PaymentProcess pp= new PaymentProcess(PaymentFactory.getPayPalAdapter());
		System.out.println("Emissione bonifico in corso.... attendere");
        pp.startPayment(value);
	}
	public double getValue() {
		return value;
	}
	public String toString() {
		return "Bonifico di "+getValue();
	}

}
