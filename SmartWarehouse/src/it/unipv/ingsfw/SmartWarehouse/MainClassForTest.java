//
package it.unipv.ingsfw.SmartWarehouse;

import it.unipv.ingsfw.SmartWarehouse.Controller.MainController;
import it.unipv.ingsfw.SmartWarehouse.View.MainView;

public class MainClassForTest {
	
	public static void main(String[] args) {
		new MainController(new MainView());
		
		
		/*
		InventoryView iv=new InventoryView();
		InventoryManager w=new InventoryManager();
		InventoryController ic=new InventoryController(w, iv);
		SupplyManager sm=new SupplyManager();
		SupplyController sc=new SupplyController(sm, iv.getSupplyPanel());
		*/


		/*
		 ReturnableOrdersView rov= new ReturnableOrdersView();
		 ReturnableOrdersController roc=new ReturnableOrdersController(rov);
		 System.out.println(roc);
		 */
		
		

    }

}
