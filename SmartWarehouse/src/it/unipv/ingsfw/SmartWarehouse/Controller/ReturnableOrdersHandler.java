//
package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.ButtonModel;


import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

/**
 * Controller for ReturnableOrdersView: it manages the choice of order to return.
 */
public class ReturnableOrdersHandler {

	private ReturnableOrdersView returnableOrdersView;

	public ReturnableOrdersHandler(ReturnableOrdersView returnableOrdersView) {
		this.returnableOrdersView=returnableOrdersView;
		
		initWithClientOrders();
		chooseOrderToReturn();
	}


	/**
	 * Initializes the ReturnableOrdersView with client order.
	 */
	private void initWithClientOrders() {
		String customerEmail=SingletonUser.getInstance().getLoggedUser().getEmail();
		ArrayList<Order> allClientOrders = RegisterFacade.getIstance().selectOrderWhereClient(customerEmail); 
		ArrayList<String> ordersDescriptionsForButton = new ArrayList<>();
		Integer[] orderIdForActionCommand=new Integer[allClientOrders.size()];
		int count=0;
		for(Order or: allClientOrders){
			ordersDescriptionsForButton.add(or.toString());
			orderIdForActionCommand[count]=or.getId();
			count++;
		}
		returnableOrdersView.initWithClientOrders(ordersDescriptionsForButton,orderIdForActionCommand);
		if(allClientOrders.size()==0) {
			returnableOrdersView.getConfirmButton().setEnabled(false);
		}
	}


	/**
	 * Listener for confirmButton: choose the order
	 */
	private void chooseOrderToReturn() {
		ActionListener confirmButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {

				ButtonModel button=returnableOrdersView.getOrderButtonGroup().getSelection();
				if(button==null) {
					returnableOrdersView.setVisible(true);
					returnableOrdersView.showWarningMessagge("Select an order to proceed.");
					return;
				}
				ReturnService returnService=null;
				try {
					returnService=ReturnManager.getIstance().getReturnService(RegisterFacade.getIstance().selectOrder(Integer.parseInt(button.getActionCommand())));
				} catch (UnableToReturnException e) {
					returnableOrdersView.setVisible(true);
					returnableOrdersView.showErrorMessagge(e.getMessage());
					return;
				}
				new ReturnController(returnService,new ReturnItemsAndReasonsView(returnableOrdersView,returnableOrdersView.getShopFrame()));
				returnableOrdersView.setVisible(false);
			}
		};
		returnableOrdersView.getConfirmButton().addActionListener(confirmButtonLister);
	}
}


