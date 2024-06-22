package it.unipv.ingsfw.SmartWarehouse.Database;

import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public interface IClientDAO {
	public void updatePrime(Client c);
	public void updateWallet(Client c);
}
