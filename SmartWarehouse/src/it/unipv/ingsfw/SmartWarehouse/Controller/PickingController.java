
package it.unipv.ingsfw.SmartWarehouse.Controller;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Register;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;
import it.unipv.ingsfw.SmartWarehouse.View.PickingView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PickingController {

    private PickingView view;
    private HashMap<String, Integer> insertedItems = new HashMap<>();
    private HashMap<Integer, JButton> orderButtons = new HashMap<>();

    public PickingController(PickingView view) {
        this.view = view;
        new Register();
        displayOrderIds();
        addCalculatePackListener();
        addItemButtonListener();
        addPackedListener();
          
    }

 

    private void addCalculatePackListener() {
        view.getCalculatePack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                        OrderP o = (OrderP) or;
                        IPackageStrategy strategy = o.calculatePack();
                        String packageInfo = strategy.calculatePackages();
                        view.displayPackageInfo(packageInfo);
                    } else {
                        throw new ReturnableOrderNullPointerException();
                    }
                } else {
                    view.showErrorMessage("No order selected.");
                }
            }
        });
    }

    private void addPackedListener() {
        view.getPacked().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null && or instanceof OrderP) {
                        OrderP orderP = (OrderP) or;
                        try {
                            validateItems(); 

                            boolean updated = RegisterDAOFacade.getIstance().setPicked(id);
                            if (updated) {
                                confirmPackaging(orderP);
                                JButton orderButton = orderButtons.get(id);
                                if (orderButton != null) {
                                    orderButton.setEnabled(false);
                                }
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.clearInsertedItemsDisplay();
                                        view.clearPackageSummary();
                                    }
                                });
                            } else {
                                view.showErrorMessage("Failed to mark order as packed.");
                            }
                        } catch (ItemNotFoundException ex) {
                            view.showErrorMessage("Item not found: " + ex.getMessage());
                        } catch (QuantityMismatchException ex) {
                            view.showErrorMessage("Quantity mismatch: " + ex.getMessage());
                        } catch (ReturnableOrderNullPointerException ex) {
                            view.showErrorMessage("Returnable order is null: " + ex.getMessage());
                        }
                    } else {
                        view.showErrorMessage("Invalid order selected or not instance of OrderP.");
                    }
                } else {
                    view.showErrorMessage("No order selected.");
                }
            }
        });
    }

    private void displayOrderIds() {
        ArrayList<Integer> idList = RegisterDAOFacade.getIstance().selectAllIds();
        ArrayList<Integer> displayedIds = new ArrayList<>();
        Collections.sort(idList);

        ActionListener orderButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                int id = Integer.parseInt(command);

                showItems(id);
                view.setSelectedOrderId(id);
                clearInsertedItems();
            }
        };

        for (Integer orderId : idList) {
            boolean picked = RegisterDAOFacade.getIstance().getPicked(orderId);
            if (!picked && !displayedIds.contains(orderId)) {
                JButton button = view.displayOrderIds(orderId);
                button.addActionListener(orderButtonListener);
                displayedIds.add(orderId);
                orderButtons.put(orderId, button);
            }
        }
    }

    private void clearInsertedItems() {
        insertedItems.clear();
    }

 

    private void addItemButtonListener() {
        view.getItemButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Integer> itemDetails = view.getItemDetails();
                if (itemDetails != null && !itemDetails.isEmpty()) {
                    String item = itemDetails.keySet().iterator().next();
                    int quantity = itemDetails.get(item);

                    try {
                        validateItemInOrder(item);

                        int id = view.getSelectedOrderId();
                        OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
                        int requiredQty = or.getItemMap().get(item);

                        int currentInsertedQty = insertedItems.getOrDefault(item, 0);
                        int remainingQty = requiredQty - currentInsertedQty;

                        if (quantity > remainingQty) {
                            view.showErrorMessage("you can't insert " + remainingQty + "you need: " + item + ".try again.");
                            return;
                        }

                        insertedItems.put(item, currentInsertedQty + quantity);

                        updateSelectedItemsDisplay();

                    } catch (ItemNotFoundException ex) {
                        view.showErrorMessage("item not in the order: " + ex.getMessage());
                    } catch (QuantityMismatchException ex) {
                        view.showErrorMessage("quantity not correct: " + ex.getMessage());
                    }
                } else {
                    view.showErrorMessage("insert some valid filed.");
                }
            }
        });
    }



    private void validateItemInOrder(String sku) throws ItemNotFoundException, QuantityMismatchException {
        int id = view.getSelectedOrderId();
        if (id != -1) {
            OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
            if (or != null) {
                HashMap<String, Integer> orderItems = or.getItemMap(); 
                if (!orderItems.containsKey(sku)) {
                    throw new ItemNotFoundException("Item '" + sku + "' is not part of the order.");
                }
            } else {
                throw new ReturnableOrderNullPointerException();
            }
        } else {
            throw new IllegalStateException("No order selected.");
        }
    }

    private void validateItems() throws ItemNotFoundException, QuantityMismatchException {
        int id = view.getSelectedOrderId();
        if (id != -1) {
            OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
            if (or != null) {
                HashMap<String, Integer> orderItems = or.getItemMap();
                for (String sku : orderItems.keySet()) {
                    int requiredQty = orderItems.get(sku);
                    if (!insertedItems.containsKey(sku)) {
                        throw new ItemNotFoundException("Item '" + sku + "' not found in the inserted items.");
                    }
                    int insertedQty = insertedItems.get(sku);
                    if (insertedQty != requiredQty) {
                        throw new QuantityMismatchException("Quantity mismatch for item '" + sku + "'. Required: " + requiredQty + ", Inserted: " + insertedQty);
                    }
                }
                for (String sku : insertedItems.keySet()) {
                    if (!orderItems.containsKey(sku)) {
                        throw new ItemNotFoundException("Inserted item '" + sku + "' is not part of the order.");
                    }
                }
            } else {
                throw new ReturnableOrderNullPointerException();
            }
        } else {
            view.showErrorMessage("No order selected.");
        }
    }

  

    private void showItems(int id) {
        OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
        List<String> itemDetailsList = or.getItemDetails();
        String orderItemsString = "";
        for (String itemDetails : itemDetailsList) {
            orderItemsString = orderItemsString.concat(itemDetails).concat("\n");
        }
        view.displayOrderItems(orderItemsString);
    }

    private void confirmPackaging(Order order) {
        view.showConfirmationMessage("Order packed successfully.");
        view.clearPackageSummary(); 
        view.clearMessages(); 
        view.clearInsertedItemsDisplay();
    }

    private void choosePackageType(String packageType) {
        String[] details = view.getPackageDetails();
        if (details != null) {
            String quantity = details[0];
            String fragility = details[1];
            view.getSelected().append("Selected package type: " + packageType + "\n");
            view.getSelected().append("Quantity: " + quantity + "\n");
            view.getSelected().append("Fragile: " + fragility + "\n");
        }
    }

    private void updateSelectedItemsDisplay() {
        view.getSelected().setText("");
        for (String sku : insertedItems.keySet()) {
            int qty = insertedItems.get(sku);
            view.getSelected().append("Item: " + sku + ", Quantity: " + qty + "\n");
        }
    }
    
}
