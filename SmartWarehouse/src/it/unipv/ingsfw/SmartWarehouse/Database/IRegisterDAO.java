package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.ArrayList;

import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;

public interface IRegisterDAO {
	public ArrayList<OrderLine>  selectOrder(int id);
	public void insertOrder(ArrayList<OrderLine> o);
	public int selectLastId();
	public ArrayList< ArrayList<OrderLine> > selectOrderWhereClient(String email);
	public ArrayList<Integer> selectAllIds() ;
	public ArrayList<OrderLine> showAllOrder();
	public int selectId(int id);
	public boolean getPicked(int id);
	public boolean setPicked(int id);
}

