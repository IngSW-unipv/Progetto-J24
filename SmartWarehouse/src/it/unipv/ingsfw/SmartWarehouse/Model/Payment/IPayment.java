package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public interface IPayment {
	public void makePayment(double amount,String senderEmail,String receiverEmail);

}
