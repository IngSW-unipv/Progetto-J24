package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class SinglePackStrategy implements IPackageStrategy {
    private OrderP o;
    private int counts = 0;
    private int countm = 0;
    private int countl = 0;
    private int countfrs = 0;
    private int countfrm = 0;
    private int countfrl = 0;
   

    public SinglePackStrategy(OrderP o) {
        this.o = o;
    }

    public boolean isPackageFragile(List<IInventoryItem> pack) {
        for (IInventoryItem item : pack) {
            if (item.getDetails().getFragility() > IPackageStrategy.N) {
                return true;
            }
        }
        return false;
    }

    public String calculatePackages() {
        List<IInventoryItem> itemList = o.getSkuqtyAsList();
        int totalSize = o.calculateTotalSize();
        boolean isFragile = isPackageFragile(itemList);
        String packageInfo = "";

        if (totalSize <= IPackageStrategy.maxs) {
            counts++;
            packageInfo = "small";
            if (isFragile) {
                countfrs++;
                packageInfo += " and requires fragile packaging.";
            }
        } else if (totalSize <= IPackageStrategy.maxm) {
            countm++;
            packageInfo = "medium";
            if (isFragile) {
                countfrm++;
                packageInfo += " and requires fragile packaging.";
            }
        } else if (totalSize <= IPackageStrategy.maxl) {
            countl++;
            packageInfo = "large";
            if (isFragile) {
                countfrl++;
                packageInfo += " and requires fragile packaging.";
            }
        }
        return "Package 1 is a " + packageInfo + " pack.";
    }

    public boolean isPackageCorrect(String packageType, int quantity, int fr) {
        switch (packageType) {
            case "small":
                return fr == countfrs && quantity == counts;

            case "medium":
                return quantity == countm && fr == countfrm;

            case "large":
                return fr == countfrl && quantity == countl;

            default:
                return false;
        }
    }

    public int getCountPack() {
    	
        return 1;
    }

    public int getCounts() {
        return counts;
    }

    public int getCountm() {
        return countm;
    }

    public int getCountl() {
        return countl;
    }

    public int getCountfrs() {
        return countfrs;
    }

    public int getCountfrm() {
        return countfrm;
    }

    public int getCountfrl() {
        return countfrl;
    }
}
