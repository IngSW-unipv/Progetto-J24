package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;


public class Registration extends Authentication{

	public Registration(User u) {
		super(u);
	}

	public boolean register(String ConfirmPassword) {
		boolean success = false;
		
		try{
			
			idCheck();
			accountCheck();
			fieldCheck(ConfirmPassword);
			passwordCheck(ConfirmPassword);
			successfulOperationCheck();
			setTypeOfUser();
			success = true;
			HomeView f = new HomeView();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			
		}	catch (EmptyFieldException e) {
			
			e.showPopup();
			System.out.println(e.toString());

		} catch (WrongFieldException e) {
			
			e.showPopup();
			System.out.println(e.toString());

		} catch (DatabaseException e) {
			e.showPopup();
			System.out.println(e.toString());
		}
		
		catch (AccountAlreadyExistsException e) {
			e.showPopup();
			System.out.println(e.toString());
		}
		
		return success;
	}

	private void fieldCheck(String password) throws EmptyFieldException {
		if (this.u.getEmail().isEmpty() || this.u.getName().isEmpty() || this.u.getSurname().isEmpty()
				|| this.u.getEmail().isEmpty() || this.u.getAddress().isEmpty()
				|| String.valueOf(this.u.getPassword()).equals("") || password.equals("")) {
			throw new EmptyFieldException();
		}
	}

	// controlla chi vuole accedere 
	private void idCheck() throws WrongFieldException {
		// Verifica formato matricola
		if (this.u.getEmail().matches("[SPR]\\d{6}")) {
			char tipoEmail = this.u.getEmail().charAt(0);
			switch (tipoEmail) {
			case 'S':
				if (!this.u.getType().toLowerCase().contains("operator")) {
					throw new WrongFieldException();
				}
				break;
			case 'P':
				if (!this.u.getType().toLowerCase().contains("user")) {
					throw new WrongFieldException();
				}
				break;
			default:
				// Tipo non riconosciuto
				throw new WrongFieldException();
			}
		} else {
			// Il formato della matricola non è valido
			throw new WrongFieldException();
		}

	}

	private void passwordCheck(String password) throws WrongFieldException {

		if (!String.valueOf(this.u.getPassword()).equals(password))
			throw new WrongFieldException();
	}

	private void successfulOperationCheck() throws DatabaseException {
		boolean inserimentoRiuscito = SingletonUser.getInstance().getUserDAO().insertUser(u);
		if (!inserimentoRiuscito)
			throw new DatabaseException();

	}
	
	private void accountCheck() throws AccountAlreadyExistsException {
		if(this.u.getEmail().equals(SingletonUser.getInstance().getUserDAO().selectEmail(u))) {	
			throw new AccountAlreadyExistsException();
		}
	}

}