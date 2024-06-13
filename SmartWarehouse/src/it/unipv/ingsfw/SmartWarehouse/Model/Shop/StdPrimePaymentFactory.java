package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class StdPrimePaymentFactory {
	public static IStdPrimePaymentStrategy spedi(boolean prime) {
		IStdPrimePaymentStrategy stra;
		if(prime) {
			stra = new PrimeStrategy();
		}
		else stra = new StdStrategy();
		return stra;
	}
	
}
