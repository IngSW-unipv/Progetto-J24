package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;


public class OrderP extends Order {
    private int countpack = 0; // Counter for packages
    private HashMap<IInventoryItem, Integer> skuqty; 
    public OrderP(HashMap<IInventoryItem, Integer> skuqty, int id, String email, LocalDateTime date) {
        super(skuqty, id, email, date);
        this.skuqty = super.getSkuqty(); 
    }

    /**
     * Calculates the total size of the order based on item dimensions and quantities.
     */
    public int calculateTotalSize() {
        int totalSize = 0;
        for (Entry<IInventoryItem, Integer> entry : skuqty.entrySet()) {
            IInventoryItem item = entry.getKey();
            int quantity = entry.getValue();
            
            // Check if item details are null
            if (item.getDetails() == null) {
                throw new IllegalArgumentException("Item details are not set for SKU: " + item.getSku());
            }
            
            int itemSize = item.getDetails().getDimension();
            totalSize += itemSize * quantity;
        }
        return totalSize;
    }

    /**
     * this method is for trasforming the orderp  SKU quantity map into a list of InventoryItems.
     */
    public List<IInventoryItem> getSkuqtyAsList() {
        List<IInventoryItem> itemList = new ArrayList<>();
        for (Entry<IInventoryItem, Integer> entry : skuqty.entrySet()) {
            InventoryItem item = (InventoryItem) entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                itemList.add(item);
            }
        }
        return itemList;
    }
    /**
     * with this method you can take the details of items in the order sorted by position.
     */
    public List<String> getItemDetails() {
        List<Map.Entry<IInventoryItem, Integer>> sortedEntries = new ArrayList<>(skuqty.entrySet());
        sortedEntries.sort(new Comparator<Map.Entry<IInventoryItem, Integer>>() {
            @Override
            public int compare(Map.Entry<IInventoryItem, Integer> entry1, Map.Entry<IInventoryItem, Integer> entry2) {
                Position pos1 = entry1.getKey().getPos();
                Position pos2 = entry2.getKey().getPos();
                int lineComparison = pos1.getLine().compareTo(pos2.getLine());
                if (lineComparison != 0) {
                    return lineComparison;
                }
                int podComparison = pos1.getPod().compareTo(pos2.getPod());
                if (podComparison != 0) {
                    return podComparison;
                }
                return pos1.getBin().compareTo(pos2.getBin());
            }
        });
        List<String> itemDetailsList = new ArrayList<>();
        for (Map.Entry<IInventoryItem, Integer> entry : sortedEntries) {
            IInventoryItem item = entry.getKey();
            int quantity = entry.getValue();
            if (item == null || item.getSku() == null) {
                throw new IllegalArgumentException("Item or SKU is null in the inventory.");
            }
            
            String itemDetails = "SKU: " + item.getSku() + ", Pos: " + item.getPos().toString() + ", Quantity: " + quantity + "\n";
            itemDetailsList.add(itemDetails);
        }
        return itemDetailsList;
    }


    /**
     * method for see if i select the correct item and the correct qty
     */
    public void selectItemqty(String sku, int qty) throws ItemNotFoundException, QuantityMismatchException {
        boolean itemFound = false;
        int actualQuantity = 0;
        for (IInventoryItem item : skuqty.keySet()) {
            if (item.getSku().equals(sku)) {
                itemFound = true;
                actualQuantity = skuqty.get(item);
                break;
            }
        }
        if (!itemFound) {
            throw new ItemNotFoundException("Item not found for SKU: " + sku);
        }
        if (actualQuantity != qty) {
            if (qty > actualQuantity) {
                throw new QuantityMismatchException("Too many items of SKU " + sku + " inserted. Please remove " + (qty - actualQuantity) + " items.");
            }
            if (qty <= 0) {
                throw new QuantityMismatchException("Negative or zero quantity " + sku + " inserted. Please insert a valid quantity.");
            }
        }
    }
    /**
     * Calculates the packaging strategy for the order
     */
    public IPackageStrategy calculatePack() {
        return new PackageStrategyFactory().getPackageStrategy(this);
    }
    /**
     * method for checks if the package size, the quantity and the fragility are correct
     */
    public boolean addAndComparePackageSize(String packageType, int quantity, int fr) throws WrongPackageException {
        IPackageStrategy strategy = new PackageStrategyFactory().getPackageStrategy(this);
        strategy.calculatePackages();
        if (strategy.isPackageCorrect(packageType, quantity, fr)) {
            countpack += quantity;
            return true;
        } else {
            throw new WrongPackageException("Wrong package size or fragility.");
        }
    }

    /**
     * method for check if all required packs are present
     */
    public boolean allPackPresent(int n) {
        IPackageStrategy strategy = new PackageStrategyFactory().getPackageStrategy(this);
        if (strategy.getCountPack() == n) {
            return true;
        }
        return false;
    }
    public int getCountp() {
        return countpack;
    }
   
}
