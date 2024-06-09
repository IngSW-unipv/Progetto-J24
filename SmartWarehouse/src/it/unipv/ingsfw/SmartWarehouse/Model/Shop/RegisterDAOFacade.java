package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.ArrayList;
import it.unipv.ingsfw.SmartWarehouse.Database.IRegisterDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.RegisterDAO;

public class RegisterDAOFacade{
	private static RegisterDAOFacade istance;
	IRegisterDAO regD;
	
	private RegisterDAOFacade() {
		regD = new RegisterDAO();
	}
	
	public static RegisterDAOFacade getIstance() {
		if(istance == null) {
			istance = new RegisterDAOFacade();
		}
		return istance;
	}
	
	public ArrayList<OrderLine>  selectOrder(int id){
		return regD.selectOrder(id);
	}

	public void insertOrder(ArrayList<OrderLine> o) {
		regD.insertOrder(o);
		
	}
	public int selectLastId() {
		return regD.selectLastId();
	}
	public ArrayList<ArrayList<OrderLine>> selectOrderWhereClient(String email) {
		return regD.selectOrderWhereClient(email);
	}
	/*
	 * for picking controller
	 */
	public ArrayList<Integer> selectAllIds() {
		return regD.selectAllIds();
	}
	public int selectId(int id) {
		return regD.selectId(id);
	}
	
}
