
package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.ArrayList;
import java.util.HashMap;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;

public class Register {
	private int id;
	RegisterDAOFacade red;
	InventoryManager inv;		
	
	public Register() {
		this.red = RegisterDAOFacade.getIstance();
		this.inv= new InventoryManager();
		this.id=this.red.selectLastId() + 1;
	}
	
	public void addOrd(Order o) {
		o.setId(id);
		red.insertOrder(makeLine(o));
		id++;
	}
	
	public Order selectOrder(int id) {
		return rebuildOrder(red.selectOrder(id),id);
	}
	
	
	public ArrayList<Order> selectOrderWhereClient(String email){
		ArrayList< ArrayList<OrderLine> > orderLineList=red.selectOrderWhereClient(email);
		ArrayList<Order> orders=new ArrayList<Order>();
		for(ArrayList<OrderLine> o:orderLineList) {
			orders.add(rebuildOrder(o,o.get(0).getId()));
		}
		return orders;
	}
	
	
	public Order rebuildOrder(ArrayList<OrderLine> o, int id) { 
		HashMap<IInventoryItem, Integer> hs = new HashMap<IInventoryItem, Integer>();
		OrderLine temp=o.get(0);
		for(OrderLine ord: o) {
			hs.put(inv.findInventoryItem(ord.getSku()), ord.getQty());
		}
		return new OrderP(hs, id, temp.getEmail(), temp.getDate());
	}
	
	private ArrayList<OrderLine> makeLine(Order o) {
		ArrayList<OrderLine> ord = new ArrayList<OrderLine>();
		for(IInventoryItem i: o.getSet()) {
			ord.add(new OrderLine(o.getId(), i.getSku(), o.getQtyOfItem(i),
					o.getEmail(), o.getDate(), false));
		}
		return ord;
	}
}
