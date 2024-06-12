package it.unipv.ingsfw.SmartWarehouse.Controller;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;

import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Register;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
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
    private String sku;
    private int qty;
    private HashMap<String, Integer> insertedItems = new HashMap<>();

    public PickingController(PickingView view) {
        this.view = view;
        new Register();
        displayOrderIds();
        addPackageTypeListeners();
        addCalculatePackListener();
        addItemButtonListener();
        addPackedListener();
    }
    /*
     * method for select the type of the pack
     */
    private void addPackageTypeListeners() {
        ActionListener packageTypeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                        JButton button = (JButton) e.getSource();
                        if (button.equals(view.getSmall())) {
                            choosePackageType("Small");
                        } 
                        else if (button.equals(view.getMedium())) {
                            choosePackageType("Medium");
                        } 
                        else if (button.equals(view.getLarge())) {
                            choosePackageType("Large");
                        }
                    } 
                    else {
                        view.showErrorMessage("No order selected.");
                    }
                }
                else {
                    view.showErrorMessage("you can't select a pack if you don't select an order.");
                }
            }
        };

        view.getSmall().addActionListener(packageTypeListener);
        view.getMedium().addActionListener(packageTypeListener);
        view.getLarge().addActionListener(packageTypeListener);
    }
    /*
     * method for calulate the type of the pack
     */
    private void addCalculatePackListener() {
        view.getCalculatePack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                    	OrderP o=(OrderP)or;
                    	IPackageStrategy strategy =  o.calculatePack();
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
    /*
     * method for add the item at the pack
     */
    private void addItemButtonListener() {
        view.getItemButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Integer> itemDetails = view.getItemDetails();
                if (itemDetails != null) {
                    String item = itemDetails.keySet().iterator().next();
                    int quantity = itemDetails.get(item);
                    sku = item; 
                    qty = quantity; 
                    insertedItems.put(item, quantity);
                    view.getSelected().append("Item: " + item + ", Quantity: " + quantity + "\n");
                }
            }
        });
    }
    /*
     * method for packed a pack
     */
    private void addPackedListener() {
        view.getPacked().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                        try {
                            validateItems(sku, qty);
                            confirmPackaging(or);
                            boolean updated = RegisterDAOFacade.getIstance().setPicked(id);
                            if (updated) {
                                String spuLabel = ((OrderP) or).getSpuLabel();
                                view.showSpuLabel(spuLabel);
                            } else {
                                view.showErrorMessage("Failed to mark order as packed.");
                            }
                        } catch (ItemNotFoundException | QuantityMismatchException ex) {
                            view.showErrorMessage(ex.getMessage());
                        }
                    } else {
                        throw new ReturnableOrderNullPointerException();
                    }
                } else {
                    view.showErrorMessage("No order selected.");
                }
            }
        });
    }

    /*
     * method for select the type of the pack
     */

    private void displayOrderIds() {
        ArrayList<Integer> idList = RegisterDAOFacade.getIstance().selectAllIds();
        ArrayList<Integer> displayedIds = new ArrayList<>();
        Collections.sort(idList);

        ActionListener orderButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                int id = Integer.parseInt(command);

                SingletonManager.getInstance().getRegisterDAO().selectOrder(id);
                showItems(id);
                view.setSelectedOrderId(id);
            }
        };

        for (Integer orderId : idList) {
        	boolean picked =RegisterDAOFacade.getIstance().getPicked(orderId);
            if (!picked && !displayedIds.contains(orderId)) {
                JButton button = view.displayOrderIds(orderId);
                button.addActionListener(orderButtonListener);
                displayedIds.add(orderId);
            }
        }
    }
    /*
     * method for show the pack
     */
    
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

    private void validateItems(String sku, int qty) throws ItemNotFoundException, QuantityMismatchException {
        int id = view.getSelectedOrderId();
        if (id != -1) {
            OrderP or = (OrderP) RegisterFacade.getIstance().selectOrder(id);
            if (or != null) {
                or.selectItemqty(sku, qty);
            } else {
                throw new ReturnableOrderNullPointerException();
            }
        } else {
            view.showErrorMessage("No order selected.");
        }
    }

    

    
}
