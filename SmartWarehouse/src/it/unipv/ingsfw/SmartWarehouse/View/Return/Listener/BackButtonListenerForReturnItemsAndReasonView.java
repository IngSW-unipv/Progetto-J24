package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

public class BackButtonListenerForReturnItemsAndReasonView implements ActionListener{

	private ReturnItemsAndReasonsView riarView;
	private ReturnableOrdersView returnableOrdersView;

	public BackButtonListenerForReturnItemsAndReasonView(ReturnItemsAndReasonsView riarView,
			ReturnableOrdersView returnableOrdersView) {
		super();
		this.riarView = riarView;
		this.returnableOrdersView = returnableOrdersView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		riarView.setVisible(false);
		returnableOrdersView.setVisible(true);
	}

}
