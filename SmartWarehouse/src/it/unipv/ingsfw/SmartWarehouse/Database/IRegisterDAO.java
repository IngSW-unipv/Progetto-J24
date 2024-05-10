package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.ArrayList;

import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;

public interface IRegisterDAO {
	public ArrayList<OrderLine>  selectOrder(int id);
	public void insertOrder(ArrayList<OrderLine> o);
	public int selectLastId();
}
