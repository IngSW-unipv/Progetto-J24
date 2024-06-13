package it.unipv.ingsfw.SmartWarehouse.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PayPal;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;

class PaymentTest {


	private PaymentProcess paymentProcessArray[]=new PaymentProcess[5];
	private PaymentProcess paymentProcess;

	@Before
	public void initTest() {
		paymentProcessArray = new PaymentProcess[5];
	}

	@Test
	public void testPaymentProcessOk() throws PaymentException {
		double amount=1;
		paymentProcess=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"senderEmail","receiverEmail");
		assertTrue(paymentProcess.startPayment(amount));
	}
	@Test
	public void testPaymentProcessNotOk1() throws PaymentException { //importo negativo
		double amount=-1;
		paymentProcess=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"senderEmail","receiverEmail");
		assertThrows(PaymentException.class, () -> {
			paymentProcess.startPayment(amount);
		});
	}
	@Test
	public void testPaymentProcessNotOk2() throws PaymentException { //blank or empty or null email
		double amount=1;
		paymentProcessArray[0]=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"","receiverEmail");
		paymentProcessArray[1]=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"senderEmail","");
		paymentProcessArray[2]=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"senderEmail","     ");
		paymentProcessArray[3]=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),"    ","receiverEmail");
		paymentProcessArray[4]=new PaymentProcess(PaymentFactory.getPayPalAdapter(new PayPal()),null,null);
		for (PaymentProcess p : paymentProcessArray) {
			assertThrows(PaymentException.class, () -> {
				p.startPayment(amount);
			});
		}
	}
}
