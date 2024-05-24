package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnFACADE;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnableOrdersView;

public class ReturnableOrdersController {

	private ReturnableOrdersView returnView;

	public ReturnableOrdersController(ReturnableOrdersView returnView) {
		this.returnView=returnView;
		returnViewInitComponents();
	}
	
	/*
	 * Controller for ReturnView: it manages the choice of order to return.
	 */

	private void returnViewInitComponents() {
		// TODO Auto-generated method stub
		
		/*
		 * Listener for confirmButton: choose the order
		 */
		ActionListener confirmButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}
			
			private void manageAction() {
				
				ButtonModel button=returnView.getOrderButtonGroup().getSelection();
				if(button==null) {
					returnView.showWarningMessagge("Selezionare un ordine per procedere.");
					returnView.setVisible(true);
					return;
				}
				Reasons.initializeReasons();
	            Map<String, String> possibleReasons = Reasons.getReasons();
	            returnView.setVisible(false);
                ReturnFACADE rf= new ReturnFACADE(SingletonManager.getInstance().getRegisterFacade().selectOrder(Integer.parseInt(button.getActionCommand())));
                new ReturnController(rf,new ReturnItemsAndReasonsView(Integer.parseInt(button.getActionCommand()),possibleReasons)); 
			}
		};
		returnView.getConfirmButton().addActionListener(confirmButtonLister);
	}
}
