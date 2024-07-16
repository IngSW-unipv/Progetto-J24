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
    /**
     * method for get the strategy
     */

    public IPackageStrategy getStrategy(OrderP order) {
        IPackageStrategy strategy = orderStrategies.get(order);
        if (strategy == null) {
            strategy = calculatePack(order);
            this.orderStrategies.put(order, strategy);
        }
        return strategy;
    }

    /**
     * 
     * @param order
     * @return
     * 
     */
    public IPackageStrategy calculatePack(OrderP order) {
        new PackageStrategyFactory();
        IPackageStrategy strategy = PackageStrategyFactory.getInstance()
        		.getPackageStrategy(order);
        this.orderStrategies.put(order, strategy);
        return strategy;
    }
    
    /**
     *
     * @param typeP
     * @param quantity
     * @param fr
     * @param order
     * @return
     * @throws WrongPackageException
     * 
     * check if the package is correct
     */
    
    public boolean addAndComparePackageSize(String typeP, int quantity, int fr, OrderP order) throws WrongPackageException {   
    	IPackageStrategy strategy= this.orderStrategies.get(order);
        if (strategy.isPackageCorrect(typeP, quantity, fr)) {
            int countpack =orderCountPacks.getOrDefault(order, 0);
            countpack += quantity;
            this.orderCountPacks.put(order, countpack);
            return true;
        } else {
            throw new WrongPackageException("Wrong package size or fragility");

        }
    }

    /**
     * 
     * @param order
     * @return
     * @throws QuantityMismatchPackageException
     * 
     * checking if the pack/s are all present
     */
    public boolean allPackPresent(OrderP order) throws QuantityMismatchPackageException {
        IPackageStrategy strategy = getStrategy(order);
        if (strategy.getCountPack() == getCountp(order) && getCountp(order)!=0) {
            return true;
        } else {
            throw new QuantityMismatchPackageException("Not all the packages are selected");
        }
    }

    public int getCountp(OrderP order) {
        return this.orderCountPacks.getOrDefault(order, 0);
    }
}
