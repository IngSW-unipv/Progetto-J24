package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class MultiplePackStrategy implements IPackageStrategy {
    private OrderP o;
    public int counts = 0;
    public int countm = 0;
    public int countl = 0;
    public int countfrs = 0;
    public int countfrm = 0;
    public int countfrl = 0;
    

    public MultiplePackStrategy(OrderP o) {
        this.o = o;
    }

    @Override
    
    public String calculatePackages() {
        int packCount = getCountPack(); // Chiamiamo il metodo totpack() per ottenere il numero totale di pacchi
        List<List<InventoryItem>> packs = new ArrayList<>();
        List<InventoryItem> itemList = o.getSkuqtyAsList();
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
    public int getCountPack() {
        List<InventoryItem> itemList = o.getSkuqtyAsList();
        List<List<InventoryItem>> packs = new ArrayList<>();
        List<InventoryItem> currentPack = new ArrayList<>();
        int currentPackSize = 0;
        int packCount = 0;

        for (InventoryItem item : itemList) {
            int itemSize = item.getDetails().getDimension();
            if (currentPackSize + itemSize > maxl) {
                packs.add(new ArrayList<>(currentPack));
                currentPack.clear();
                currentPackSize = 0;
                packCount++;
            }
            currentPack.add(item);
            currentPackSize += itemSize;
        }

        if (!currentPack.isEmpty()) {
            packs.add(currentPack);
            packCount++;
        }

        return packCount;
    }

    public boolean isPackageFragile(List<InventoryItem> pack) {
        for (InventoryItem item : pack) {
            if (item.getDetails().getFragility() > N) {
                return true;
            }
        }
        return false;
    }

    private String printSummary(List<List<InventoryItem>> packages) {
        String summary = "";
        for (int i = 0; i < packages.size(); i++) {
            List<InventoryItem> pack = packages.get(i);
            int totalSize = calculateTotalSize(pack);
            String packSize;
            boolean isFragile = isPackageFragile(pack);
            if (totalSize <= maxs) {
                packSize = "small";
                counts++;
                if (isFragile) {
                    countfrs++;
                }
            } else if (totalSize <= maxm) {
                packSize = "medium";
                countm++;
                
                if (isFragile) {
                    countfrm++;
                }
            } else {
                packSize = "large";
                countl++;
         
                if (isFragile) {
                    countfrl++;
                }
            }
         
            summary = summary.concat("Package ").concat(String.valueOf(i + 1)).concat(" is a ")
                             .concat(packSize)
                             .concat(" pack");
            if (isFragile) {
                summary = summary.concat(" and requires fragile packaging.\n");
            } else {
                summary = summary.concat(".\n");
            }
        }
       
        return summary;
    }
    
    private int calculateTotalSize(List<InventoryItem> items) {
        int totalSize = 0;
        for (InventoryItem item : items) {
            totalSize += item.getDetails().getDimension();
        }
        return totalSize;
    }

    public boolean isPackageCorrect(String packageType, int quantity, int fr) {
        boolean isFragile = false;
        
        switch (packageType) {
            case "small":
                isFragile = fr == getCountfrs();
                return getCounts() == quantity && isFragile;
                
            case "medium":
                isFragile = fr == getCountfrm();
                return getCountm() == quantity && isFragile;
                
            case "large":
                isFragile = fr == getCountfrl();
                return getCountl() == quantity && isFragile;
                
            default:
                return false;
        }
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
    
    
   


