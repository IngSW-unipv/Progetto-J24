package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

public class StdStrategy implements IStdPrimePaymentStrategy{
	private final double sped;
	
	public StdStrategy() {
		sped = 50;
	}
	@Override
	public double pay(double imp) {
		return sped + imp;
	}
	
}
