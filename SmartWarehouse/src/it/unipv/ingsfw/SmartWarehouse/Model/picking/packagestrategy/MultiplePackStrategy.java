package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
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
        List<List<IInventoryItem>> packs = new ArrayList<>();
        List<IInventoryItem> itemList = o.getSkuqtyAsList();
        List<IInventoryItem> currentPack = new ArrayList<>();
        int currentPackSize = 0;

        for (IInventoryItem item : itemList) {
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
        List<IInventoryItem> itemList = o.getSkuqtyAsList();
        List<List<IInventoryItem>> packs = new ArrayList<>();
        List<IInventoryItem> currentPack = new ArrayList<>();
        int currentPackSize = 0;
        int packCount = 0;

        for (IInventoryItem item : itemList) {
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

    public boolean isPackageFragile(List<IInventoryItem> pack) {
        for (IInventoryItem item : pack) {
            if (item.getDetails().getFragility() > N) {
                return true;
            }
        }
        return false;
    }

    private String printSummary(List<List<IInventoryItem>> packages) {
        String summary = "";
        for (int i = 0; i < packages.size(); i++) {
            List<IInventoryItem> pack = packages.get(i);
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
    
    private int calculateTotalSize(List<IInventoryItem> pack) {
        int totalSize = 0;
        for (IInventoryItem item : pack) {
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
    
    
   


