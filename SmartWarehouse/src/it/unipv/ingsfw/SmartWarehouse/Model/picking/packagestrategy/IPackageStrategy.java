package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.*;
public interface IPackageStrategy {
    public boolean calculatePackageSizes(Orderp o);
}
