package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class SinglePackStrategy implements IPackageStrategy {
	private OrderP o;
	public  int counts=0;
	public  int countm = 0;
	public int countl = 0;
	
    public SinglePackStrategy(OrderP o) {
       this.o=o;
      
    }
    /*
     * method for checking if a list of items contain a fragility one 
     */
    private boolean isPackageFragile(List<InventoryItem> pack) {
        for (InventoryItem item : pack) {
            if (item.getDetails().getFragility() > N) {
                return true; 
            }
        }
        return false;
    }
    /*
     * with this i calculate the packages
     */
    public String calculatePackages() {
        int totalsize = o.calculateTotalSize();
        List<InventoryItem> itemList = o.getSkuqtyAsList();
        boolean isFragile = isPackageFragile(itemList);

        String packageInfo = "";
        if (totalsize <= maxs) {
            packageInfo = packageInfo.concat("small 1");
            counts++;
            System.out.println("small " + counts);
        } else if (totalsize <= maxm) {
            packageInfo = packageInfo.concat("medium 1");
            countm++;
            System.out.println("medium " + countm);
        } else if (totalsize <= maxl) {
            packageInfo = packageInfo.concat("large 1");
            countl++;
            System.out.println("large " + countl);
        }
        if (isFragile) {
            packageInfo = packageInfo.concat(" and requires fragile packaging.");
        }
        
        return packageInfo;
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

    public boolean isPackageCorrect(String packageType, int quantity) {
        switch (packageType.toLowerCase()) {
            case "small":
                return counts == quantity;
            case "medium":
                return countm == quantity;
            case "large":
                return countl == quantity;
            default:
                return false;
        }
    }

    
}
