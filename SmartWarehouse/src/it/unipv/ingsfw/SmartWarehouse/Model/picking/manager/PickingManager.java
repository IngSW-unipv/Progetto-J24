package it.unipv.ingsfw.SmartWarehouse.Model.picking.manager;

import java.util.HashMap;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchItemException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchPackageException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;

public class PickingManager {
    private static PickingManager instance;
    private HashMap<String, Integer> items;
	private int countpack = 0;

    private PickingManager() {
        items = new HashMap<>();
    }

    public static PickingManager getInstance() {
        if (instance == null) {
            instance = new PickingManager();
        }
        return instance;
    }

    public boolean addItem(String sku, int quantity, OrderP order) throws ItemNotFoundException, QuantityMismatchItemException {
        if (order.selectItemqty(sku, quantity) != 0) {
            items.merge(sku, quantity, Integer::sum);
            return true;
        } else {
            return false;
        }
    }

    /**
	 * Calculates the packaging strategy for the order
	 */
	public IPackageStrategy calculatePack(OrderP or) {
		return new PackageStrategyFactory().getPackageStrategy(or);
	}
  
  	public boolean addAndComparePackageSize(String typeP, int quantity, int fr,OrderP or) throws WrongPackageException {
  	   IPackageStrategy strategy = calculatePack(or); 
  	   strategy.calculatePackages();
  	    if (strategy.isPackageCorrect(typeP, quantity, fr)) {
  	        countpack += quantity;
  	        return true;
  	    } else {
  	        throw new WrongPackageException("Wrong package size or fragility."+strategy.calculatePackages());
  	    }
  	}
  	/*
  	 * method for check if all required packs are present
  	 */
  	public boolean allPackPresent(OrderP or ) throws QuantityMismatchPackageException {
  		IPackageStrategy strategy =calculatePack(or); 
  		if (strategy.getCountPack() == countpack) 
  			return true;
  		else 
  			throw new QuantityMismatchPackageException("not all the package are selected");
  		
  	}
  	
  	public int getCountp() {
  		return countpack;
  	}

}
