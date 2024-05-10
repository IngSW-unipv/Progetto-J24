package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.ArrayList;
import java.util.HashMap;

import it.unipv.ingsfw.SmartWarehouse.Database.IRegisterDAO;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;

public class RegisterFacade {
	private int id;
	IRegisterDAO red;
	InventoryManager inv;		//serve nella fase di ricostruzione
	
	public RegisterFacade(IRegisterDAO red, InventoryManager i) {
		this.red = red;
		this.inv=i;
		this.id=this.red.selectLastId();
	}
	
	public void addOrd(Order o) {
		o.setId(id);
		red.insertOrder(makeLine(o));
		id++;
	}
	
	public Order selectOrder(int id) {
		return rebuildOrder(red.selectOrder(id),id);
	}
	
	private Order rebuildOrder(ArrayList<OrderLine> o, int id) { 
		HashMap<InventoryItem, Integer> hs = new HashMap<InventoryItem, Integer>();
		OrderLine temp=o.get(0);
		for(OrderLine ord: o) {
			hs.put(inv.findInventoryItem(ord.getSku()), ord.getQty()); //eccezione gestita da InventoryManager se sku non esiste
		}
		return new Order(hs, id, temp.getEmail(), temp.getDate());
	}
	
	private ArrayList<OrderLine> makeLine(Order o) {
		ArrayList<OrderLine> ord = new ArrayList<OrderLine>();
		for(InventoryItem i: o.getSet()) {
			ord.add(new OrderLine(o.getId(), i.getSku(), o.getQtyOfItem(i),
					o.getEmail(), o.getDate()));
		}
		return ord;
	}
}
