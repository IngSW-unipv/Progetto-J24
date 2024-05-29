package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory;

import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.PackageStrategy;

public class PackageStrategyFactory {
	public static PackageStrategy getPackageStrategy(String strategyType) {
			return new PackageStrategy();
    }
}

