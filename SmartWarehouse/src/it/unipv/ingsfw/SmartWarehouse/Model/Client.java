package it.unipv.ingsfw.SmartWarehouse.Model;

public class Client extends User{
	double wallet;
	boolean prime;

	public Client(String name, String surname, String email, String address, String password) {
		super(name,surname,email,address,password,"Cliente");
		wallet = 0;
		prime = false;
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
}
