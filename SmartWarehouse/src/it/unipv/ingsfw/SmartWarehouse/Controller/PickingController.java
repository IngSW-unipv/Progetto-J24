package it.unipv.ingsfw.SmartWarehouse.Controller;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;

import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchItemException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchPackageException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongPackageException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.manager.PickingManager;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.IPackageStrategy;
import it.unipv.ingsfw.SmartWarehouse.View.PickingView;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PickingController {
	private PickingView view;
	private HashMap<Integer, JButton> orderButtons = new HashMap<>();
	private PickingManager pickingmanager;

	public PickingController(PickingView view) {
		this.view = view;
		this.pickingmanager = PickingManager.getInstance();
		displayOrderIds();
		addCalculatePackListener();
		addItemButtonListener();
		addPackedListener();
		addPackageTypeListeners();
		disablePackageTypeButtons();
	}
	private void addPackageTypeListeners() {
	    view.getSmallButton().addActionListener(e -> {
	        choosePackageType("Small"); 
	    });
	    view.getMediumButton().addActionListener(e -> { 
	        choosePackageType("Medium"); 
	    });
	    view.getLargeButton().addActionListener(e -> {  
	        choosePackageType("Large"); 
	    });
	}
	private void addCalculatePackListener() {
	    view.getCalculatePack().addActionListener(e -> {
	        int id = view.getSelectedOrderId();
	        if (id != -1) {
	           OrderP or= (OrderP) RegisterFacade.getIstance().selectOrder(id);  
	           IPackageStrategy strategy= this.pickingmanager.calculatePack(or);
	            if (strategy != null) {
	                String packageInfo = strategy.calculatePackages();
	                view.displayPackageInfo(packageInfo);
	                enablePackageTypeButtons();
	                view.getCalculatePack().setEnabled(false);
	            } else {
	                throw new ReturnableOrderNullPointerException();
	            }
	        } else {
	            view.showErrorMessage("No order selected.");
	        }
	    });
	}
	private void displayOrderIds() {
		ArrayList<Integer> idList = RegisterDAOFacade.getIstance().selectAllIds();
		ArrayList<Integer> displayedIds = new ArrayList<>();
		Collections.sort(idList);
		ActionListener orderButtonListener = e -> {
			String command = e.getActionCommand();
			int id = Integer.parseInt(command);
			showItems(id);
			view.setSelectedOrderId(id);
			view.clearMessages();
			view.clearPackageSummary();
			disablePackageTypeButtons();
			view.getCalculatePack().setEnabled(true);
			
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
	    view.getItemButton().addActionListener(e -> {
	        int selectedOrderId = view.getSelectedOrderId();
	        if (selectedOrderId == -1) {
	            view.showErrorMessage("no order selected.");
	            return;
	        }
	        HashMap<String, Integer> itemDetails = view.getItemDetails();
	        if (itemDetails != null && !itemDetails.isEmpty()) {
	            for (String sku : itemDetails.keySet()) {
	                int qty = itemDetails.get(sku);
	                OrderP order = (OrderP) RegisterFacade.getIstance().selectOrder(selectedOrderId);
	                int check = 0;
	                try {
	                    check = order.selectItemqty(sku, qty);
	                    if (check != 0) {
	                        view.addItemInserted(sku, qty);
	                    }
	                } catch (ItemNotFoundException | QuantityMismatchItemException e1) {
	                    view.showErrorMessage(e1.getMessage());
	                }
	            }
	        }
	    });
	}


    
	private void showItems(int id) {
		OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
		List<String> itemDetailsList = or.getItemDetails();
		view.displayOrderItems(itemDetailsList);
	}
	private void choosePackageType(String typeP) {
        int id = view.getSelectedOrderId();
        int[] packageDetails = view.getPackageDetails();
        if (packageDetails != null) {
            int quantityToAdd = packageDetails[0];
            int fragility = packageDetails[1];
            OrderP order = (OrderP) RegisterFacade.getIstance().selectOrder(id);
			boolean result=false;
			try {
				result = pickingmanager.addAndComparePackageSize(typeP, quantityToAdd, fragility, order);
				if (result) {
				    view.getSelected().append( "Selected package type: " + typeP + "\n");
				    view.getSelected().append("Quantity: " + quantityToAdd + "\n");
				    view.getSelected().append("Fragility: " + fragility + "\n");
				}
			} 
			catch (WrongPackageException e) {
				view.showErrorMessage(e.getMessage());
			}
        }
    }
	private void addPackedListener() {
	    view.getPacked().addActionListener(e -> {
	        int id = view.getSelectedOrderId();
	        if (id != -1) {
	            OrderP order = (OrderP) RegisterFacade.getIstance().selectOrder(id);
	            if (order != null) {
	                boolean allPacked = false;
	                try {
	                    allPacked = pickingmanager.allPackPresent(order);
	                    if (allPacked && order.allItemPresent()) {
	                        JButton orderButton = this.orderButtons.get(id);
	                        if (orderButton != null) {
	                            orderButton.setEnabled(false);
	                            confirmPackaging(order);	                            
	                        }
	                    } else {
	                        view.showErrorMessage("Not all items are packed or inserted.");
	                    }
	                } catch (QuantityMismatchPackageException | QuantityMismatchItemException e1) {
	                    view.showErrorMessage(e1.getMessage());
	                }
	            }
	        } else {
	            view.showErrorMessage("No order selected.");
	        }
	    });
	}
  			
	private void confirmPackaging(OrderP order) {
		view.showConfirmationMessage("Order packed successfully.");
		view.clearPackageSummary();
		view.clearMessages();
		view.clearInsertedItemsDisplay();
        RegisterDAOFacade.getIstance().setPicked(order.getId());
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
	
}
