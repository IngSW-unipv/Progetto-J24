package it.unipv.ingsfw.SmartWarehouse.Model.user;

public class Client extends User {
	private double wallet;
	private boolean prime;
	private String password;

	public Client(String name, String surname, String email, String password) {
		super(name,surname,email);
		wallet = 0;
		prime = false;
		this.password=password;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	
	public boolean getPrime() {
		return prime;
	}
	
	public void setPrime(boolean prime) {
		this.prime=prime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
