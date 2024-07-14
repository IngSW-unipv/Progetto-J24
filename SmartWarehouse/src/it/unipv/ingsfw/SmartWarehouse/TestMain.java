//
package it.unipv.ingsfw.SmartWarehouse;

import it.unipv.ingsfw.SmartWarehouse.Controller.MainController;
import it.unipv.ingsfw.SmartWarehouse.Controller.PickingController;
import it.unipv.ingsfw.SmartWarehouse.View.MainView;
import it.unipv.ingsfw.SmartWarehouse.View.PickingView;

public class TestMain {
	
	public static void main(String[] args) {
	
		//new MainController(new MainView());
		new PickingController(new PickingView());
		

    }

}
