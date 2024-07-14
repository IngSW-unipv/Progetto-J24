package it.unipv.ingsfw.SmartWarehouse.Model.picking.manager;

import java.util.HashMap;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchPackageException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;

public class PickingManager {
    private static PickingManager instance;
    private HashMap<OrderP, IPackageStrategy> orderStrategies;
    private HashMap<OrderP, Integer> orderCountPacks;

    private PickingManager() {
        orderStrategies = new HashMap<>();
        orderCountPacks = new HashMap<>();
    }

    public static PickingManager getInstance() {
        if (instance == null) {
            instance = new PickingManager();
        }
        return instance;
    }

    public IPackageStrategy getStrategy(OrderP order) {
        IPackageStrategy strategy = orderStrategies.get(order);
        if (strategy == null) {
            strategy = calculatePack(order);
            orderStrategies.put(order, strategy);
        }
        return strategy;
    }

    /**
     * Calculates the packaging strategy for the order
     */
    public IPackageStrategy calculatePack(OrderP order) {
    	 IPackageStrategy strategy = orderStrategies.get(order);
         if (strategy == null) {
             strategy = new PackageStrategyFactory().getPackageStrategy(order);
             orderStrategies.put(order, strategy);
         }
        return strategy;
    }

    public boolean addAndComparePackageSize(String typeP, int quantity, int fr, OrderP order) throws WrongPackageException {   
    	  IPackageStrategy strategy= orderStrategies.get(order);
        if (strategy.isPackageCorrect(typeP, quantity, fr)) {
            int countpack = orderCountPacks.getOrDefault(order, 0);
            countpack += quantity;
            orderCountPacks.put(order, countpack);
            return true;
        } else {
            throw new WrongPackageException("Wrong package size or fragility." + strategy.calculatePackages());
        }
    }

    /**
     * Method to check if all required packs are present
     */
    public boolean allPackPresent(OrderP order) throws QuantityMismatchPackageException {
        IPackageStrategy strategy = getStrategy(order);
        int countpack = orderCountPacks.getOrDefault(order, 0);
        if (strategy.getCountPack() == countpack) {
            return true;
        } else {
            throw new QuantityMismatchPackageException("Not all the packages are selected");
        }
    }

    public int getCountp(OrderP order) {
        return orderCountPacks.getOrDefault(order, 0);
    }
}
