package it.unipv.ingsfw.SmartWarehouse.Model.Payment;

public interface IPayment {
	public void makePayment(double importo,String senderEmail,String receiverEmail);

}
