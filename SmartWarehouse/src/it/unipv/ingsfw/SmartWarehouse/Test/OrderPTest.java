package it.unipv.ingsfw.SmartWarehouse.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchItemException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

class OrderPTest {
    private Position pos1 = new Position("G", "A", "A");
    private ItemDetails itemDetails1 = new ItemDetails(1, 1, Category.TOYS);
    private ItemDetails itemDetails2 = new ItemDetails(1, 1, Category.CLOTHING);
    private IInventoryItem inventoryItem1 = new InventoryItem("peluche", itemDetails1, "SKU001", 10, 92, 10, pos1);
    private IInventoryItem inventoryItem2 = new InventoryItem("t-shirt", itemDetails2, "SKU002", 10, 92, 10, pos1);

    private OrderP orderP;
    private HashMap<IInventoryItem, Integer> skuqty;

    @BeforeEach
    public void initOrderp() {
        skuqty = new HashMap<IInventoryItem, Integer>();
        skuqty.put(inventoryItem1, 3);
        skuqty.put(inventoryItem2, 5);
        orderP = new OrderP(skuqty, 1, "test@example.com", LocalDateTime.now());
    }
    
    @Test
    public void testSelectItemqty_ItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> {
            orderP.selectItemqty("SKU999", 1);
        });
    }
    
    @Test
    public void testSelectItemqty_QuantityMismatchItemException() {
        assertThrows(QuantityMismatchItemException.class, () -> {
            orderP.selectItemqty("SKU001", 15);
        });

        assertThrows(QuantityMismatchItemException.class, () -> {
            orderP.selectItemqty("SKU001", -1);
        });
    }
    
    @Test
    public void testAllItem() {
    	assertThrows(QuantityMismatchItemException.class, () -> {
            orderP.allItemPresent();
        });
    }
    
    

}
