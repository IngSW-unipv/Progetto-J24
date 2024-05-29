package it.unipv.ingsfw.SmartWarehouse.Model.authentication;


public class Authentication {

	protected User u;
	
	public Authentication(User u) {
		this.u=u;
	}
	
	
	
	protected void setTypeOfUser() {
		
		String tipo = u.getType();
		switch (tipo) {
		case "Client":
			SingletonUser.getInstance().setLoggedUser(new User(u.getName(), u.getSurname() ,
					u.getEmail(),u.getAddress(), String.valueOf(u.getPassword()), null));
			break;
		case "Operator":
			SingletonUser.getInstance().setLoggedUser(new User(u.getName(), u.getSurname() ,
					u.getEmail(),u.getAddress(), String.valueOf(u.getPassword()), null));
			break;
					
		}
	}
	
}
