package it.unipv.ingsfw.SmartWarehouse.Database;

import it.unipv.ingsfw.SmartWarehouse.Model.Client;

public interface IClientDAO {
	public Client selectClient(String email);
	public void InsertClient(Client c);
}