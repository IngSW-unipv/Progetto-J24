package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;


import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public interface IPackageStrategy {
	public static final int maxs = 10;
	public static final int maxm = 15;
	public static final int maxl = 20;
	public static final int N = 3;
	public String  calculatePackages();
   	public boolean isPackageCorrect(String packageType, int quantity,int fr);
    public boolean isPackageFragile(List<InventoryItem> pack);
	public int getCountfrl();
	public int getCountfrm();
	public int getCountfrs();
	public int getCounts();
	public int getCountm();
	public int getCountl();
	public int getCountPack();
}
