package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class PackageStrategy implements IPackageStrategy {
    private int maxs = 9;
    private int maxm = 12;
    private int maxl = 15;
    private final int N = 3;
    private OrderP o;

    public PackageStrategy(OrderP o) {
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
     * method used for calculate how many package an order need:I divide the total size of the items 
     * with the maxl then divide the list into lists of how many packages i need 
     * and distribute the elements. 
     * 
     */
    public String morePack() {
        String packageInfoText = "";
        int totalsize = o.calculateTotalSize();
        int numPacks = totalsize / maxl; 
        if (totalsize % maxl != 0) {
            numPacks++;   
        }
        packageInfoText = "You need " + numPacks + " packages\n";
        List<InventoryItem> itemList = o.getSkuqtyAsList();
        List<List<InventoryItem>> packs = new ArrayList<>(); 
        for (int i = 0; i < numPacks; i++) {
            packs.add(new ArrayList<>());
        }

        int packIndex = 0;
        for (InventoryItem item : itemList) {
            packs.get(packIndex).add(item);
            packIndex = (packIndex + 1) % numPacks;
        }
        
        packageInfoText = packageInfoText.concat(printSummary(packs));
        return packageInfoText;
    }    
    /*
     * method used for print the type of package needed
     */
    private String printSummary(List<List<InventoryItem>> packs) {
        String summary = "";
        int totalSmallPacks = 0;
        int totalMediumPacks = 0;
        int totalLargePacks = 0;

        for (int i = 0; i < packs.size(); i++) {
            List<InventoryItem> pack = packs.get(i);
            int totalSize = calculateTotalSize(pack);
            String packSize;

            if (totalSize <= maxs) {
                totalSmallPacks++;
                packSize = "small";
            } else if (totalSize <= maxm) {
                totalMediumPacks++;
                packSize = "medium";
            } else {
                totalLargePacks++;
                packSize = "large";
            }

            summary = summary.concat("Package " + (i + 1) + " is a " + packSize + " pack");
            if (isPackageFragile(pack)) {
                summary = summary.concat(" and requires fragile packaging.\n");
            } else {
                summary = summary.concat(".\n");
            }
        }
        return summary;
    }
    /*
     * with this you can calculate the size of singolar list, 
     * the total dimension of the elements
     */
    private int calculateTotalSize(List<InventoryItem> items) {
        int totalSize = 0;
        for (InventoryItem item : items) {
            totalSize += item.getDetails().getDimension(); 
        }
        return totalSize;
    }
}
