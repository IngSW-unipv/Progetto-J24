package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class MultiplePackStrategy implements IPackageStrategy {
    private OrderP o;

    public MultiplePackStrategy(OrderP o) {
        this.o = o;
    }
    /*
     * method used for calculate how many package an order need:I divide the total size of the items 
     * with the maxl then divide the list into lists of how many packages i need 
     * and distribute the elements. 
     * 
     */
    @Override
    public String calculatePackages() {
        List<InventoryItem> itemList = o.getSkuqtyAsList();
        List<List<InventoryItem>> packs = new ArrayList<>();
        List<InventoryItem> currentPack = new ArrayList<>();
        int currentPackSize = 0;
        for (InventoryItem item : itemList) {
            int itemSize = item.getDetails().getDimension();
            if (currentPackSize + itemSize > maxl) {
                packs.add(new ArrayList<>(currentPack));
                currentPack.clear();
                currentPackSize = 0;
            }
            currentPack.add(item);
            currentPackSize += itemSize;
        }

        if (!currentPack.isEmpty()) {
            packs.add(currentPack);
        }

        return printSummary(packs);
    }

 /*
  * method that print the package you need
  */
    private String printSummary(List<List<InventoryItem>> packages) {
        StringBuilder summary = new StringBuilder();

        for (int i = 0; i < packages.size(); i++) {
            List<InventoryItem> pack = packages.get(i);
            int totalSize = calculateTotalSize(pack);
            String packSize;
            if (totalSize <= maxs) {
                packSize = "small";
            } else if (totalSize <= maxm) {
                packSize = "medium";
            } else {
                packSize = "large";
            }

            summary.append("Package ").append(i + 1).append(" is a ").append(packSize).append(" pack");
            if (isPackageFragile(pack)) {
                summary.append(" and requires fragile packaging.\n");
            } else {
                summary.append(".\n");
            }
        }
        return summary.toString();
    }
 /*
  * check if the inventory item is fragile
  */
    private boolean isPackageFragile(List<InventoryItem> pack) {
        for (InventoryItem item : pack) {
            if (item.getDetails().getFragility() >= N) {
                return true; 
            }
        }
        return false;
    }
    private int calculateTotalSize(List<InventoryItem> items) {
        int totalSize = 0;
        for (InventoryItem item : items) {
            totalSize += item.getDetails().getDimension(); 
        }
        return totalSize;
    }
}

