package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

public class BackButtonListenerForReturnableOrdersView implements ActionListener{
	private ReturnableOrdersView returnableOrdersView;
	private ShopFrame shopFrame;

	public BackButtonListenerForReturnableOrdersView(ReturnableOrdersView returnableOrdersView,ShopFrame shopFrame) {
		super();
		this.returnableOrdersView=returnableOrdersView;
		this.shopFrame=shopFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		returnableOrdersView.setVisible(false);
		shopFrame.setVisible(true);
	}
	

}
