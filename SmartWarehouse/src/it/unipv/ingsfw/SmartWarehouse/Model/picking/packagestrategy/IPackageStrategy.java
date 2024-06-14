package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.HashMap;

public interface IPackageStrategy {
	public static final int maxs = 10;
	public static final int maxm = 15;
	public static final int maxl = 20;
	public static final int N = 3;
	public String  calculatePackages();
	public int getCounts();
   	public int getCountm();
        public int getCountl();
}
