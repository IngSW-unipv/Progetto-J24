package it.unipv.ingsfw.SmartWarehouse.Model.user;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;

public class Client extends User {
	private double wallet;
	private boolean prime;
	private String password;
	
	/**
	 * when a new client registers wallet and prime have default values
	 */

	public Client(String name, String surname, String email, String password) {
		super(name,surname,email);
		wallet = 0;
		prime = false;
		this.password=password;
	}
	
	/**
	 * when a client log-in all his attribute can be set
	 */
	
	public Client(String name, String surname, String email, String password, boolean prime, double wallet) {
		super(name,surname,email);
		this.wallet = wallet;
		this.prime = prime;
		this.password=password;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
		SingletonUser.getInstance().getCustoumerDAO().updateWallet(this);
	}
	
	public boolean getPrime() {
		return prime;
	}
	
	public void setPrime(boolean prime) {
		this.prime=prime;
		SingletonUser.getInstance().getCustoumerDAO().updatePrime(this);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
