package packagestrategy;

import order.*;
import inventory.*;
import java.util.*;
import java.util.Map.Entry;


public class PackageStrategy implements IPackageStrategy {
	private Order o;
	private int maxs=9;
	private int maxm=12;
	private int maxl=15;
	public boolean typePack(Order o,int n) {
		if(o.calculateTotalSize()<=n) {
			if(o.tfFragility()==true) {
				System.out.println("you need a fragility pack:");
			}
			return true;
		}
		return false;
	}
	public void morePack(Order o) {
	    int totalsize = o.calculateTotalSize();
	    int numPacks = totalsize / maxl; //per calcolo dei pacchi
	    if (totalsize % maxl!= 0) {
	        numPacks++;
	       // Se c'è un resto, ho bisogno di un altro pacco
	    }
	    System.out.println("you need"+" "+numPacks+" "+"packages");
	    List<InventoryItem> itemList = o.getSkuqtyAsList();
	    List<List<InventoryItem>> packs = new ArrayList<>(); //divido la lista in liste, tante liste quanti i pacchi
	    for (int i = 0; i < numPacks; i++) {
	        packs.add(new ArrayList<>());
	    }
	    // Distribuisci gli elementi nei pacchi
	    int packIndex = 0;
	    for (InventoryItem item : itemList) {
	        packs.get(packIndex).add(item);
	        packIndex = (packIndex + 1) % numPacks;
	    }
	    for (int i = 0; i < numPacks; i++) { //stampo il pacco per ogni lista
	        printPackageInfo(packs);
	    }
	}
	public void printPackageInfo(List<List<InventoryItem>> packs) {
	    // Itera attraverso ogni lista di elementi nei pacchi
	    for (int i = 0; i < packs.size(); i++) {
	        List<InventoryItem> pack = packs.get(i);
	        System.out.println("Package " + (i + 1) + ":");
	        int totalSize = calculateTotalSize(pack);
	        if (totalSize <= maxs) { //tipo di pacco
	            System.out.println("Use a small pack");
	        } else if (totalSize <= maxm) {
	            System.out.println("Use a medium pack");
	        } else {
	            System.out.println("Use a large pack");
	        }
	        boolean fragile = isPackageFragile(pack);
	        if (fragile) { //se è fragile ci vuole pacco fragile
	            System.out.println("Fragile packaging is required");
	        }
	        
	        System.out.println();
	    }
	}

	private int calculateTotalSize(List<InventoryItem> items) {
	    int totalSize = 0;
	    for (InventoryItem item : items) {
	        totalSize += item.getDim(); //calcolo la dimensione della lista degli elementi
	    }
	    return totalSize;
	}

	private boolean isPackageFragile(List<InventoryItem> items) {
	    for (InventoryItem item : items) {
	        if (item.getFr()) {
	            return true; //vedo se è nella lista c'è un item fragile
	        }
	    }
	    return false;
	}
	
	@Override
	public boolean calculatePackageSizes(Order o) {
		if(typePack(o,maxs)==true) {
			System.out.println("use a Small pack");
		}
		else if(typePack(o,maxm)==true) {
			System.out.println("use a medium pack");
		}
		else if(typePack(o,maxl)==true) {
			System.out.println("use a Large pack");
		}
		else {
			morePack(o);
		}
		return true;
	} 
}

   

	
	   


	