package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class SinglePackStrategy implements IPackageStrategy {
	private OrderP o;
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
         int numPacks = totalsize / maxl; 
         if (totalsize % maxl != 0) {
             numPacks++;   
         }
         
        String packageInfo = "You need 1 package\n";
        packageInfo = packageInfo.concat("Package is a ");
        if (totalsize <= maxs) {
            packageInfo = packageInfo.concat("small pack");
        } 
        else if (totalsize <= maxm) {
            packageInfo = packageInfo.concat("medium pack");
        } 
       else if (totalsize <= maxl)
       {
            packageInfo = packageInfo.concat("large pack");
       }
       if (isFragile) {
            packageInfo = packageInfo.concat(" and requires fragile packaging.");
       }
       packageInfo = packageInfo.concat(".\n");

       return packageInfo;
           
            
    }
    
   
    
}
