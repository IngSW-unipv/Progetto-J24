package it.unipv.ingsfw.SmartWarehouse.model.picking.packagefactory;
import it.unipv.ingsfw.SmartWarehouse..model.picking.orderp.*;
import packagestrategy.*;

public class PackageStrategyFactory {
	public static PackageStrategy getPackageStrategy(String strategyType) {
			return new PackageStrategy();
    }
}

