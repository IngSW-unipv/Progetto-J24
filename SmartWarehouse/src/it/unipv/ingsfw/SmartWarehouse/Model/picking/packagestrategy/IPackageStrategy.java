package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;


import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
public interface IPackageStrategy {
    public boolean calculatePackageSizes(Order o);
}
