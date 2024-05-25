package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Shop;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnableOrdersView;
import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;

public class ShopController {
	private Shop model;
	private ShopFrame view;
	
	public ShopController(Shop model, ShopFrame view) {
		this.model=model;
		this.view=view;
		this.view.makeShop(this.model.getInv());
		
		if(this.model.getCl().getPrime()) {
			this.view.getPrime().setBackground(Color.green);
		}
		
		initcomponents();
	}
		
	public void initcomponents() {
		ActionListener addk=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(model.getInv().findInventoryItem(e.getActionCommand()).toString());
				int q=0;
				try {
				q=view.displayOption();
				
				}catch(NumberFormatException ex1) {
					System.err.print("invalid argument please retry");}
				try {
				model.addToKart(e.getActionCommand(),q);
				
				}catch(IllegalArgumentException ex2) {
					System.err.print("invalid argument please retry");
				}
				view.setInfoLabText(model.getKart().toString());
			}
			
		};
		for(JButton b: view.getShopButts()){
			b.addActionListener(addk);
		}
		
		ActionListener rmvk=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(view.displayConfirm()==0) {
					model.removeFromKart(e.getActionCommand());
					for(JButton b: view.getKartButts()) {
						if(b.getActionCommand().equals(e.getActionCommand())) {
							b.setVisible(false);	
							view.setInfoLabText(model.getKart().toString());
						}
					}
				}
			}
			
		};
		
		ActionListener payord=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(view.displayConfirm()==0) {
					
					PaymentProcess pay=new PaymentProcess(view.displayPaymentOption(), model.getCl().getEmail(), "magazzo");		
					try {
						pay.startPayment(model.getKart().getTotal());
						model.makeOrder();
					} catch (PaymentException ex) {
						ex.printStackTrace();
						//stampare che i fondi sono stati insufficienti
					}				
				}
			}
		};
		
		ActionListener gokart=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.makeKart(model.getKart().getSet(), model.getKart().getSkuqty());
				view.showKart();
				for(JButton b: view.getKartButts()) {
					b.addActionListener(rmvk);
				}
			}
		};
		
		view.getPay().addActionListener(payord);
		view.getKart().addActionListener(gokart);
		
		ActionListener retshop=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.showShop();
				System.out.println(model.getKart().toString());
			}
		};
		
		view.getShop().addActionListener(retshop);
		
		ActionListener getprime=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(view.displayConfirm()==0 && !model.getCl().getPrime()) {
					
					PaymentProcess pay=new PaymentProcess(view.displayPaymentOption(), model.getCl().getEmail(), "magazzo");
					try {
						pay.startPayment(model.getPrimeImport());
						view.displayInfo("pagamento");
						
					} catch (PaymentException ex) {
						System.err.println("è stato impossibile effettuare l'abbonamento a prime");
					}
					model.setPrime();
				}
				if(model.getCl().getPrime()) {
					view.getPrime().setBackground(Color.green);
				}
			}
		};
		
		view.getPrime().addActionListener(getprime);
		
		ActionListener goToOrderArea=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new ReturnableOrdersController(new ReturnableOrdersView(model.getCl()));
			}
		};
		view.getOrders().addActionListener(goToOrderArea);
	}
}
