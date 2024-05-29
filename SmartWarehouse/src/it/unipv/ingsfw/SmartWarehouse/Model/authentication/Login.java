package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;


public class Login extends Authentication {

	public Login(User u) {
		super(u);
	}

	public boolean login() {

		boolean success = false;

		try {
			
			fieldCheck();			
			passwordCheck();
			setTypeOfUser();
			success = true;
			HomeView f = new HomeView();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			//HomeController controller = new HomeController(f);

		} catch (EmptyFieldException e) {

			e.showPopup();
			System.out.println(e.toString());

		} catch (WrongFieldException e) {

			e.showPopup();
			System.out.println(e.toString());

		}

		return success;

	}

	private void fieldCheck() throws EmptyFieldException, WrongFieldException {
		
		if (this.u.getEmail().isEmpty() == true || this.u.getPassword().isEmpty() == true) {
			throw new EmptyFieldException();
		}
		
		if (!Character.isUpperCase(this.u.getEmail().charAt(0))) {
	        throw new WrongFieldException();
	    }
		
	}

	private void passwordCheck() throws WrongFieldException {

		if (!u.getPassword().equals(SingletonUser.getInstance().getUserDAO().selectPassword(u))) {
			throw new WrongFieldException();
		}

	}

}
