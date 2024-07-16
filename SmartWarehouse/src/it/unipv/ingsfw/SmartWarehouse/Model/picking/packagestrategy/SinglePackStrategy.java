package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

import java.util.LinkedList;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class SinglePackStrategy implements IPackageStrategy {
    private OrderP o;    
    public int counts = 0;
    public int countm = 0;
    public int countl = 0;
    private int countfrs = 0;
    private int countfrm = 0;
    private int countfrl = 0; 
  
    public SinglePackStrategy(OrderP o) {
        this.o = o;
    }
    
    public String calculatePackages() {
        LinkedList<IInventoryItem> itemList = this.o.getSkuqtyAsList();
        int totalSize = this.o.calculateTotalSize();
        boolean isFragile = this.o.checkFragility(itemList);
        String packageInfo = "";
       
        if (totalSize <= SMALL) {   
            packageInfo = "Small";
            counts++;
            if (isFragile) {
                countfrs++;
                packageInfo += " and requires fragile packaging.";
            }
        }   
        else if (totalSize <= MEDIUM) {
            packageInfo = "Medium";
            countm++;
            if (isFragile) {
                countfrm++;
                packageInfo += " and requires fragile packaging.";
            }
        }
              
        else { 
            packageInfo = "Large";
            countl++;
            if (isFragile) {
                countfrl++;
                packageInfo += " and requires fragile packaging.";
            }
        }
        
        return "Package 1 is a " + packageInfo ;
        
    }
    /**
     * check if the package, qty and fr are correct
     */
    public boolean isPackageCorrect(String typeP, int quantity, int fr) {
    	String packageType=typeP;
        switch (packageType) {
            case "Small":
                return quantity == counts &&  fr == getCountfrs();
            case "Medium":
                return quantity==countm && fr == getCountfrm();
            case "Large":
                return quantity == countl && fr == getCountfrl();
            default:
                return false;
        }
    }
   
    public int getCountPack() {
        return 1;
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
