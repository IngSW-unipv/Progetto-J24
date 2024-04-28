package model.supply;

import exception.AuthorizationDeniedException;
import exception.EmptyFieldException;
import exception.supplier.InvalidSupplierException;
import exception.supplier.SupplierAlreadyExistsException;
import exception.supplier.SupplierDoesNotExistException;
import facade.SupplyDAOFacade;
import model.operator.SupplyOperator;
import model.operator.WarehouseOperator;

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
	
	@Override
	public String toString() {
		// qui ci sarà n super.toString magari dato che implementa utente
		return "IDS: "+IDS +", fullname: "+ fullname + ", address: "+address+ ", email: "+email;
	}
	 
	//check valid fields
	public void add(WarehouseOperator op) throws InvalidSupplierException, AuthorizationDeniedException, EmptyFieldException {
		this.checkSupplierAuthorization(op); 
		if(IDS.isEmpty() || fullname.isEmpty() || address.isEmpty() || email.isEmpty()) {
			throw new EmptyFieldException();
		}
		if(supplyDAOFacade.findSupplier(IDS)==null) {
			supplyDAOFacade.insertNewSupplier(this);
		} else {
			throw new SupplierAlreadyExistsException();
		}	 
	}
	
	public boolean delete(WarehouseOperator op) throws InvalidSupplierException, AuthorizationDeniedException { 
		this.checkSupplierAuthorization(op); 
		if(supplyDAOFacade.findSupplier(IDS)!=null) { 
			return supplyDAOFacade.deleteSupplier(this);
		} else {
			throw new SupplierDoesNotExistException();
		} 
	} 
	
	private boolean checkSupplierAuthorization(WarehouseOperator op) throws AuthorizationDeniedException {
		try {
			SupplyOperator su = (SupplyOperator) op;
			return true;
		} catch(ClassCastException e) {
			throw new AuthorizationDeniedException();
		}
	}
	
}
