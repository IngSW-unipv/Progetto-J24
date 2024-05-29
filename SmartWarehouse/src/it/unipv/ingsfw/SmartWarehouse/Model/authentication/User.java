package it.unipv.ingsfw.SmartWarehouse.authentication;

public class User {
	private String name;
	private String surname;
	private String email;
	private String address;
	private String password;
	private String type; 

	public User(String name,String surname,String email,String address,String password,String type) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.password=password;
		this.type=type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		// TODO Auto-generated method stub
		this.address=address;
	}	
}
