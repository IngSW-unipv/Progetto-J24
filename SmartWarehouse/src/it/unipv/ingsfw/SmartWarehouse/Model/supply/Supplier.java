package it.unipv.ingsfw.SmartWarehouse.Model.supply;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.InvalidSupplierException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.InvalidSupplyException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.SupplyOperator;

public class Supplier {
	private String IDS, fullname, address, email;
	private SupplyDAOFacade supplyDAOFacade;

	public Supplier(String IDS, String fullname, String address, String email) {
		this.IDS = IDS;
		this.fullname = fullname;
		this.address = address;
		this.email = email;
		supplyDAOFacade=SupplyDAOFacade.getInstance();
	}

	public String getIDS() {
		return IDS;
	}

	public void setIDS(String iDS) {
		IDS = iDS;
	}

	public String getFullName() {
		return fullname;
	}

	public void setFullName(String nome) {
		this.fullname = nome;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	 
	public void add() throws InvalidSupplierException, AuthorizationDeniedException {
		this.checkSupplierAuthorization(); 
		if(supplyDAOFacade.findSupplier(IDS)==null) {
			supplyDAOFacade.insertNewSupplier(this);
		} else {
			throw new SupplierAlreadyExistsException();
		}	 
	}
	
	public boolean delete() throws InvalidSupplierException, AuthorizationDeniedException, InvalidSupplyException { 
		this.checkSupplierAuthorization(); 
		if(supplyDAOFacade.findSupplier(IDS)!=null) {
			//delete also the supplies associated with this supplier, and the orders of the supply deleted
			for(Supply supply:supplyDAOFacade.findSupplyBySupplier(IDS)) {
				supply.delete(); 
			}
			return supplyDAOFacade.deleteSupplier(this);
		} else {
			throw new SupplierDoesNotExistException();
		} 
	} 
	
	private boolean checkSupplierAuthorization() throws AuthorizationDeniedException {
		try {
			SupplyOperator op= (SupplyOperator)SingletonUser.getInstance().getLoggedUser();
			return true;
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
}
