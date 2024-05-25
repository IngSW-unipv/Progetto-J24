package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.*;
import packagestrategy.*;

public class PackageStrategyFactory {
	public static PackageStrategy getPackageStrategy(String strategyType) {
			return new PackageStrategy();
    }
}

