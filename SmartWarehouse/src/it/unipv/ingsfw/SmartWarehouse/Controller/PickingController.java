package it.unipv.ingsfw.SmartWarehouse.Controller;

import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Register;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy.PackageStrategy;
import it.unipv.ingsfw.SmartWarehouse.View.PickingView;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.awt.*;

public class PickingController {

    private PickingView view;
    private ArrayList<OrderLine> o;
    private Register reg;

    public PickingController(PickingView view) {
        this.view = view;
        reg=new Register();
        displayOrderIds();
        typepack();
        packed();
        calculatePackageType();
       
    }
    private void typepack() {
        ActionListener packageTypeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.equals(view.getSmall())){
                    choosePackageType("Small");
                }
                else if (button.equals(view.getMedium()))
                {
                    choosePackageType("Medium");
                } else  {
                    choosePackageType("Large");
                
                }
            }
        };

        JButton[] buttons = {
            view.getSmall(),
            view.getMedium(),
            view.getLarge(),
            //view.getFragility(),
            view.getCalculatePack(),
            view.getPacked()
        };

        for (JButton button : buttons) {
            button.addActionListener(packageTypeListener);
        }
    }
    

    /* method used for show the orders
     * the orders are in the db
     */


    private void displayOrderIds() {
        ArrayList<Integer> idList = SingletonManager.getInstance().getRegisterDAO().selectAllIds();
        ArrayList<Integer> displayedIds = new ArrayList<>(); 
        
        Collections.sort(idList);

        ActionListener orderButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                int id = Integer.parseInt(command);

                
                o = SingletonManager.getInstance().getRegisterDAO().selectOrder(id);
                showItems(id);
                view.setSelectedOrderId(id); 
            }
        };

        for (Integer orderId : idList) {
            if (!displayedIds.contains(orderId)) { 
                JButton button = view.displayOrderIds(orderId);
                button.addActionListener(orderButtonListener);
                displayedIds.add(orderId); 
            }
        }
    }

    
    private void showItems(int id) {
        id = SingletonManager.getInstance().getRegisterDAO().selectId(id); 
        Order or = RegisterFacade.getIstance().selectOrder(id);
        StringBuilder orderItemsString = new StringBuilder();
        orderItemsString.append(or.toString());
        view.getText().setText(orderItemsString.toString());
    }

    private void calculatePackageType() {
        view.getCalculatePack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId();
                if (id != -1) {
                    Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                        PackageStrategy ps = new PackageStrategy((OrderP)or);
                        StringBuilder packageInfoText = new StringBuilder();
                        ps.morePack();
                        packageInfoText.append(ps.morePack());
                      
                        view.displayPackageInfo(packageInfoText.toString());
                    } else {
                        throw new ReturnableOrderNullPointerException();
                    }
                } else {
                    view.showErrorMessage("Nessun ordine selezionato.");
                }
            }
        });
    }


    private void packed() {
        ActionListener packedListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = view.getSelectedOrderId(); //rebuildOrder(o, id);
                if (id != -1) { // Controlla se un ordine è stato selezionato
                	Order or = RegisterFacade.getIstance().selectOrder(id);
                    if (or != null) {
                        confirmPackaging(or);
                    } else {
                        throw new ReturnableOrderNullPointerException();
                    }
                } else {
                    view.showErrorMessage("Nessun ordine selezionato.");
                }
            }
        };

        view.getPacked().addActionListener(packedListener);
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
   
}
