package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnFACADE;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnableOrdersView;

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
		ArrayList<Order> allClientOrders = RegisterFacade.getIstance().selectOrderWhereClient("john.doe@example.com");  //ArrayList<Order> allClientOrders = RegisterFacade.getIstance().selectOrderWhereClient(singletonManager.getInstance().getUser().getEmail()); 
		ArrayList<String> ordersDescriptionsForButton = new ArrayList<>();
		Integer[] orderIdForActionCommand=new Integer[allClientOrders.size()];
		int count=0;
		for(Order or: allClientOrders){
			ordersDescriptionsForButton.add(or.toString());
			orderIdForActionCommand[count]=or.getId();
			count++;
		}
		returnableOrdersView.initWithClientOrders(ordersDescriptionsForButton,orderIdForActionCommand);
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
                ReturnFACADE rf= new ReturnFACADE(RegisterFacade.getIstance().selectOrder(Integer.parseInt(button.getActionCommand())));
                new ReturnController(rf,new ReturnItemsAndReasonsView()); 
			}
		};
		returnableOrdersView.getConfirmButton().addActionListener(confirmButtonLister);
	}
}
