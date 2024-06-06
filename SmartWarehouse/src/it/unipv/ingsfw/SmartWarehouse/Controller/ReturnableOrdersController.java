//
package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.ButtonModel;


import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

/*
 * Controller for ReturnableOrdersView: it manages the choice of order to return.
 */
public class ReturnableOrdersController {

	private ReturnableOrdersView returnableOrdersView;

	public ReturnableOrdersController(ReturnableOrdersView returnableOrdersView) {
		this.returnableOrdersView=returnableOrdersView;
		initWithClientOrders();
		chooseOrderToReturn();
	}


	/*
	 * Initializes the ReturnableOrdersView with client order.
	 */
	private void initWithClientOrders() {
		//ArrayList<Order> allClientOrders = RegisterFacade.getIstance().selectOrderWhereClient("john.doe@example.com"); 
		ArrayList<Order> allClientOrders = RegisterFacade.getIstance().selectOrderWhereClient(SingletonManager.getInstance().getLoggedUser().getEmail()); 
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


	/*
	 * Listener for confirmButton: choose the order
	 */
	private void chooseOrderToReturn() {
		// TODO Auto-generated method stub
		ActionListener confirmButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}

			private void manageAction() {

				ButtonModel button=returnableOrdersView.getOrderButtonGroup().getSelection();
				if(button==null) {
					returnableOrdersView.showWarningMessagge("Selezionare un ordine per procedere.");
					returnableOrdersView.setVisible(true);
					return;
				}
				returnableOrdersView.setVisible(false);
				ReturnService returnService=null;
				try {
					returnService=ReturnManager.getIstance().getReturnService(RegisterFacade.getIstance().selectOrder(Integer.parseInt(button.getActionCommand())));
					//ReturnService returnService=order.createReturnService(); //order glielo passo a questo costruttorre e order avrà il metodo createReturn che chiama ResoManager.getInstance.getReturn ecc
				} catch (UnableToReturnException e) {
					// TODO Auto-generated catch block
					returnableOrdersView.setVisible(true);
					returnableOrdersView.showErrorMessagge(e.getMessage());
					return;
				}
				new ReturnController(returnService,new ReturnItemsAndReasonsView(),returnableOrdersView); 
			}
		};
		returnableOrdersView.getConfirmButton().addActionListener(confirmButtonLister);
	}
}


