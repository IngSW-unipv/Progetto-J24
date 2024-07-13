package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class MultiplePackStrategy implements IPackageStrategy {
    private OrderP o;
    public int counts = 0;
    public int countm = 0;
    public int countl = 0;
    public int countfrs = 0;
    public int countfrm = 0;
    public int countfrl = 0;
    public int packCount = 0; 
   
    public MultiplePackStrategy(OrderP o) {
        this.o = o;
    }
    /**
     * Calculate packages based on item dimensions and fragility.
     */
    public String calculatePackages() {
        LinkedList<List<IInventoryItem>> packs = new LinkedList<>(); 
        LinkedList<IInventoryItem> itemList = o.getSkuqtyAsList(); 
        List<IInventoryItem> currentPack = new LinkedList<>(); 
        int currentPackSize = 0; 
        for (IInventoryItem item : itemList) {
            int itemSize = item.getDetails().getDimension();  
            if (currentPackSize + itemSize > LARGE) {
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
        return getSummary(packs); 
    }
  
    /**
     * Generate a summary string for the list of packs.
     */
    private String getSummary(LinkedList<List<IInventoryItem>> packages) {
        for (List<IInventoryItem> pack : packages) {
            int totalSize = calculateTotalSize(pack);
            boolean isFragile = o.checkFragility(pack);
            if (totalSize <= SMALL) {
                counts++;
                if (isFragile) {
                	countfrs++;
                }
            }              
            else if (totalSize <= MEDIUM) {
                countm++;
                if (isFragile) {
                    countfrm++;
                }
            }  
             else {
                countl++;
                if (isFragile) {
                   countfrl++;
                }
             } 
        }
        String summary = "Total packages summary:\n";
        summary += "Small packs: " + counts + " (Fragile: " + countfrs + ")\n";
        summary += "Medium packs: " + countm + " (Fragile: " + countfrm + ")\n";
        summary += "Large packs: " + countl + " (Fragile: " + countfrl + ")\n";

        return summary;
    }

    private int calculateTotalSize(List<IInventoryItem> items) {
        int totalSize = 0;
        for (IInventoryItem item : items) {
            totalSize += item.getDetails().getDimension(); 
        }
        return totalSize; 
    }

    /**
     * method to check if the package i insert is correct,and the quantity and the fragility too
     */
    public boolean isPackageCorrect(String typeP, int quantity, int fr) {
        boolean isFragile = false; 
        String packageType=typeP;
        switch (packageType) {
            case "Small":
                isFragile = fr == getCountfrs(); 
                return getCounts() == quantity && isFragile; 
                
            case "Medium":
                isFragile = fr == getCountfrm();
                return getCountm() == quantity && isFragile;                 
            case "Large":
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
    public int getCountPack() {
    	return packCount;
    }
   
}