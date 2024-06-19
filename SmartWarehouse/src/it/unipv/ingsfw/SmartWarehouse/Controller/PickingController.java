package it.unipv.ingsfw.SmartWarehouse.Controller;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Register;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagefactory.PackageStrategyFactory;
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
    addPackageTypeListeners();
    disablePackageTypeButtons();
}

private void addPackageTypeListeners() {
    view.getSmallButton().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choosePackageType("small");
        }
    });
    view.getMediumButton().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choosePackageType("medium");
        }
    });
    view.getLargeButton().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choosePackageType("large");
        }
    });
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
                    enablePackageTypeButtons();
                } else {
                    throw new ReturnableOrderNullPointerException();
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
            view.clearMessages();
            view.clearPackageSummary();
            disablePackageTypeButtons();
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

private void addItemButtonListener() {
    view.getItemButton().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, Integer> itemDetails = view.getItemDetails();
            if (itemDetails != null && !itemDetails.isEmpty()) {
                String item = itemDetails.keySet().iterator().next();
                int quantity = itemDetails.get(item);
                try {
                    int id = view.getSelectedOrderId();
                    OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
                    or.selectItemqty(item, quantity);
                    int requiredQty = or.getItemMap().get(item);
                    int currentInsertedQty = insertedItems.getOrDefault(item, 0);
                    int remainingQty = requiredQty - currentInsertedQty;
                    if (quantity > remainingQty) {
                        view.showErrorMessage("You can't insert " + item + ". You need: " + remainingQty + ". Try again.");
                        return;
                    }
                    insertedItems.put(item, currentInsertedQty + quantity);
                    updateSelectedItemsDisplay();
                } catch (ItemNotFoundException ex) {
                    view.showErrorMessage("Item not in the order: " + ex.getMessage());
                } catch (QuantityMismatchException ex) {
                    view.showErrorMessage("Quantity not correct: " + ex.getMessage());
                }
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
                            view.clearInsertedItemsDisplay();
                            view.clearPackageSummary();
                        } else {
                            view.showErrorMessage("Failed to mark order as packed.");
                        }
                    } catch (ItemNotFoundException ex) {
                        view.showErrorMessage("Item not found: " + ex.getMessage());
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

private void validateItems() throws ItemNotFoundException {
    int id = view.getSelectedOrderId();
    if (id != -1) {
        OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
        if (or != null) {
            HashMap<String, Integer> orderItems = or.getItemMap();
            for (String sku : orderItems.keySet()) {
                if (!insertedItems.containsKey(sku)) {
                    throw new ItemNotFoundException("Item '" + sku + "' not found in the inserted items.");
                }
            }
        }
    } else {
        throw new ReturnableOrderNullPointerException();
    }
}




private void showItems(int id) {
    OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
    List<String> itemDetailsList = or.getItemDetails();
    view.displayOrderItems(itemDetailsList);
}

private void clearInsertedItems() {
    insertedItems.clear();
}

private void confirmPackaging(Order order) {
    view.showConfirmationMessage("Order packed successfully.");
    view.clearPackageSummary();
    view.clearMessages();
    view.clearInsertedItemsDisplay();
}
private void choosePackageType(String packageType) {
    int id = view.getSelectedOrderId();
    if (id == -1) {
        disablePackageTypeButtons();
        return;
    }
    OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
    int[] packageDetails = view.getPackageDetails();
    if (packageDetails == null) {
        view.showErrorMessage("Package details were not provided.");
        disablePackageTypeButtons();
        return;
    }
    int quantityToAdd = packageDetails[0];
    int fragility = packageDetails[1];
    try {
		if(or.addAndComparePackageSize(packageType, quantityToAdd, fragility)) {
			int newQuantity =+ quantityToAdd; 
	        or.setCountp(newQuantity);    
		    boolean allPacksPresent = or.allPackPresent(newQuantity);
		    if (allPacksPresent) {
		        enablePackageTypeButtons();
		    }
		    else {
		    	System.out.println("not correct");
		    }
		
		}
	} catch (WrongPackageException e) {
		e.printStackTrace();
	}
    view.getSelected().append("Selected package type: " + packageType + "\n");
    view.getSelected().append("Quantity: " + quantityToAdd + "\n");
}

private void disablePackageTypeButtons() {
    view.getSmallButton().setEnabled(false);
    view.getMediumButton().setEnabled(false);
    view.getLargeButton().setEnabled(false);
}

private void enablePackageTypeButtons() {
    view.getSmallButton().setEnabled(true);
    view.getMediumButton().setEnabled(true);
    view.getLargeButton().setEnabled(true);
}

private void updateSelectedItemsDisplay() {
    for (String sku : insertedItems.keySet()) {
        int qty = insertedItems.get(sku);
        view.getSelected().append("Item: " + sku + ", Quantity: " + qty + "\n");
    }
}

}
