package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory;

import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.MultiplePackStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.SinglePackStrategy;


public class PackageStrategyFactory {
	
	    public IPackageStrategy getPackageStrategy(OrderP o) {
	        int totalSize = o.calculateTotalSize();
	        if (totalSize <= 20) {
	            return new SinglePackStrategy(o);
	        } else {
	            return new MultiplePackStrategy(o);
	        }
	    }
}
