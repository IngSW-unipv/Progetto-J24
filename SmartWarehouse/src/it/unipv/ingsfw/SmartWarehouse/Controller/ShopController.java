

package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyKartExceptio;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PayPal;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentProcess;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.WalletPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IStdPrimePaymentStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Shop;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.StdPrimePaymentFactory;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;
import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;

public class ShopController {
	private Shop model;
	private ShopFrame view;
	
	public ShopController(Shop model, ShopFrame view) {
		this.model=model;
		this.view=view;
		this.view.makeShop(this.model.getInv());
		this.view.setWalletLabImp(model.getCl().getWallet());
		
		if(this.model.getCl().getPrime()) {
			this.view.getPrime().setBackground(Color.green);
			this.view.getPrime().setText("You are Prime");
		}
		
		initcomponents();
	}
		
	public void initcomponents() {
		ActionListener addk=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int q=0;
				try {
					q=view.displayOption();
					try {
						model.addToCart(e.getActionCommand(),q);
					}catch(IllegalArgumentException ex2) {
						view.displayWarn(ex2.getMessage());
					}
					view.setInfoLabText(model.getCart().getSkuqty().size());
				} catch (NumberFormatException exx) {
					//do nothing
				}
				
			}
		};
		for(JButton b: view.getShopButts()){
			b.addActionListener(addk);
		}
		
		ActionListener rmvk=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(view.displayConfirm()==0) {
					model.removeFromCart(e.getActionCommand());
					for(JButton b: view.getKartButts()) {
						if(b.getActionCommand().equals(e.getActionCommand())) {
							b.setVisible(false);	
							view.setInfoLabText(model.getCart().getSkuqty().size());
						}
					}
				}
			}
			
		};
		
		ActionListener payord=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(view.displayConfirm()==0) {
					IPayment mode=null;
					switch (view.displayPaymentOption()) {
				    case 0:
				        mode = PaymentFactory.getPayPalAdapter(new PayPal());
				        break;
				    case 1:
				        mode = PaymentFactory.getWalletPaymentAdapter(new WalletPayment());
				        break;
					}
					double exW = model.getCl().getWallet();
					
					PaymentProcess pay=new PaymentProcess(mode, model.getCl().getEmail(), "warehause");
					IStdPrimePaymentStrategy stdprimestr = StdPrimePaymentFactory.getInstance().getStrategy(model.getCl().getPrime());
					double total = stdprimestr.pay( model.getCart().getTotal() );
					try {
						pay.startPayment(total);
						model.makeOrder();
						view.displayInfo("Payment of: "+ total + "euro succesfully ended");
						view.setInfoLabText(model.getCart().getSkuqty().size());						
					} catch (PaymentException ex) {
						view.displayWarn(ex.getMessage());
					} catch (IllegalArgumentException | EmptyKartExceptio | ItemNotFoundException exx) {
						model.getCl().setWallet(exW);
						view.displayWarn(exx.getMessage());
					}
												
					}
					view.setWalletLabImp(model.getCl().getWallet());
				}
		};
		
		ActionListener gokart=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.makeKart(model.getCart().getSet(), model.getCart().getSkuqty());
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
				view.showShop();
			}
		};
		
		view.getShop().addActionListener(retshop);
		
		ActionListener getprime=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!model.getCl().getPrime()) {
					if(view.displayConfirm()==0) {
						IPayment mode=null;
						switch (view.displayPaymentOption()) {
					    case 0:
					        mode = PaymentFactory.getPayPalAdapter(new PayPal());
					        break;
					    case 1:
					        mode = PaymentFactory.getWalletPaymentAdapter(new WalletPayment());
					        break;
						}
						
						PaymentProcess pay=new PaymentProcess(mode, model.getCl().getEmail(), "warehouse");
						try {
							pay.startPayment(SmartWarehouseInfoPoint.PrimeImport); 
							view.displayInfo("Payment of: "+SmartWarehouseInfoPoint.PrimeImport+"euro succesfully ended");
							model.setPrime();
							view.getPrime().setBackground(Color.green);
							view.getPrime().setText("You are Prime");
							
						} catch (PaymentException ex) {
							view.displayWarn(ex.getMessage());
						}
					}
				}
				
				else {
					view.getPrime().setBackground(Color.green);
				}
				view.setWalletLabImp(model.getCl().getWallet());
			}
		};
		
		view.getPrime().addActionListener(getprime);
		
		ActionListener goToOrderArea=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new ReturnableOrdersHandler(new ReturnableOrdersView(view));
				view.setVisible(false);
			}
		};
		view.getOrders().addActionListener(goToOrderArea);
		
		ActionListener chargeWallet=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IPayment mode=null;
				switch (view.displayPaymentOption()) {
			    case 0:
			        mode = PaymentFactory.getPayPalAdapter(new PayPal());
			        break;
			    case 1:
			        mode = PaymentFactory.getWalletPaymentAdapter(new WalletPayment());
			        break;
				}
				PaymentProcess pay=new PaymentProcess(mode, model.getCl().getEmail(), "warehouse");
				int q;	
				try {
					q=view.displayOption();
					pay.startPayment(q);
					model.getCl().setWallet(model.getCl().getWallet() + q);
					view.displayInfo("Payment of: "+q+"euro succesfully ended");
				} catch (PaymentException e1) {
					view.displayWarn(e1.getMessage());
				} catch(NumberFormatException nf) {
					//do nothing
				}
				view.setWalletLabImp(model.getCl().getWallet());
			}
		};
		view.getChargeWallet().addActionListener(chargeWallet);
	}
}
