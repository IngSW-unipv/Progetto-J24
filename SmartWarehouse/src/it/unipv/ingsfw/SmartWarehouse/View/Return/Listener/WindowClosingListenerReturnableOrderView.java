package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import it.unipv.ingsfw.SmartWarehouse.Controller.MainController;
import it.unipv.ingsfw.SmartWarehouse.View.MainView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

public class WindowClosingListenerReturnableOrderView implements WindowListener {
    private ReturnableOrdersView returnableOrdersView;

    public WindowClosingListenerReturnableOrderView(ReturnableOrdersView returnableOrdersView) {
        this.returnableOrdersView = returnableOrdersView;
    }

    @Override
    public void windowClosing(WindowEvent e) {
    	returnableOrdersView.setVisible(false);
    	new MainController(new MainView());
    	
    }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
