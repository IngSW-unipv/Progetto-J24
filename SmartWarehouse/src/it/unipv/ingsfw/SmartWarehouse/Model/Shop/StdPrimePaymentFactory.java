
package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class StdPrimePaymentFactory {
	private PrimeStrategy prime; 
	private StdStrategy std;
	private static StdPrimePaymentFactory istance;
	
	private StdPrimePaymentFactory() {
		prime = new PrimeStrategy();
		std = new StdStrategy();
	}
	
	public static StdPrimePaymentFactory getInstance() {
		if(istance == null) {
			istance = new StdPrimePaymentFactory();
		}
		return istance;
	}

	public IStdPrimePaymentStrategy getStrategy(boolean isPrime) {
		IStdPrimePaymentStrategy stra;
		if(isPrime) {
			stra = prime;
		}
		else {
			stra = std;
		}
		return stra;
	}

}
