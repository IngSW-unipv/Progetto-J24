package it.unipv.ingsfw.SmartWarehouse.packagefactory;
import it.unipv.ingsfw.SmartWarehouse.orderp.*;

import packagestrategy.*;

public class PackageStrategyFactory {
	public static PackageStrategy getPackageStrategy(String strategyType) {
			return new PackageStrategy();
    }
}

