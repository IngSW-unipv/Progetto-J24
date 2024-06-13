package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class PrimeStrategy implements IStdPrimePaymentStrategy{
	
	@Override
	public double pay(double imp) {
		return imp;
	}
		
}
