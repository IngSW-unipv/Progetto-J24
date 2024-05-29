package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.Model.Payment.IPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.PaymentFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.WalletPayment;
import it.unipv.ingsfw.SmartWarehouse.Model.Payment.WalletPaymentAdapter;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;

@SuppressWarnings("serial")
public class ShopFrame extends JFrame{

	private JPanel shopPan,kartPan,infoPan;
	private ArrayList<JButton> shopbutts;
	private HashSet<JButton> kartbutts;
	private JButton kart,shop,pay,orders,prime;
	private JLabel kartInfoLab, wallet;

	public ShopFrame() {
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setTitle("SmartWarehouse/Shop");
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		kart =new JButton("Kart->");
		shop =new JButton("<-Shop");
		pay =new JButton("PAY");
		orders =new JButton("Orders");
		prime =new JButton("Get Your Prime");
		
		shop.setPreferredSize(new Dimension(90, 40));
		kart.setPreferredSize(new Dimension(90, 40));
		pay.setPreferredSize(new Dimension(90, 40));
		orders.setPreferredSize(new Dimension(110, 40));
		
		kart.setFocusable(false);
		shop.setFocusable(false);
		pay.setFocusable(false);
		orders.setFocusable(false);
		orders.setVisible(true);
		prime.setVisible(true);
		
		shopPan=new JPanel();
		kartPan=new JPanel();
		infoPan=new JPanel();
		
		kartInfoLab=new JLabel();
		kartInfoLab.setPreferredSize(new Dimension(200,80));
		kartInfoLab.setText("elementi nel carrello: 0");
		infoPan.add(kartInfoLab);
		
		wallet=new JLabel();
		wallet.setPreferredSize(new Dimension(200,80));
		wallet.setText("Wallet: 0 euro");
		infoPan.add(wallet);
		
		JToolBar bar=new JToolBar();
		bar.add(kart);
		bar.add(shop);
		bar.add(pay);
		bar.add(orders);
		bar.add(prime);
		
		this.add(bar, BorderLayout.NORTH);
		this.add(shopPan, BorderLayout.CENTER);
		this.add(infoPan, BorderLayout.SOUTH);
	}
	
	public void makeShop(InventoryManager inv) {
		shopbutts = new ArrayList<JButton>();
		for(InventoryItem i: inv.getInventory()) {
			JButton b=new JButton(i.getDescription()+": "+i.getPrice()+"$");
			
			b.setFocusable(false);
			b.setPreferredSize(new Dimension(150,100));
			b.setActionCommand(i.getSku());
			shopbutts.add(b);
			shopPan.add(b);
		}
		
		shop.setVisible(false);
		kart.setVisible(true);
		pay.setVisible(false);
	}
	
	public void makeKart(HashSet<InventoryItem> i, HashMap<InventoryItem, Integer> sq) {
		kartbutts = new HashSet<JButton>();
		kartPan.removeAll();
		
		for(InventoryItem it: i) {
			JButton b=new JButton(it.getDescription()+"-"+sq.get(it)+": "+it.getPrice()+"$");
			
			b.setFocusable(false);
			b.setPreferredSize(new Dimension(150,100));
			b.setActionCommand(it.getSku());
			kartbutts.add(b);
			shopPan.add(b);
		}
		for(JButton b: kartbutts) {
			kartPan.add(b);
		}
		
		shop.setVisible(true);
		kart.setVisible(false);
		pay.setVisible(true);
	}
	
	public void showShop() {
		shopPan.setVisible(true);
		kartPan.setVisible(false);
		this.add(shopPan, BorderLayout.CENTER);
		
		shop.setVisible(false);
		kart.setVisible(true);
		pay.setVisible(false);
		this.setTitle("SmartWarehouse/Shop");
	}
	
	public void showKart() {
		kartPan.setVisible(true);
		shopPan.setVisible(false);
		this.add(kartPan, BorderLayout.CENTER);
		
		shop.setVisible(true);
		kart.setVisible(false);
		pay.setVisible(true);
		this.setTitle("SmartWarehouse/Shop/Kart");
	}
	
	public int displayOption() throws NumberFormatException{
		int ret=0;
		ret=Integer.parseInt(JOptionPane.showInputDialog("insert quantity:"));
		if(ret<=0) {
			NumberFormatException ex = new NumberFormatException();
			throw ex;
		}
		return ret;
	}
	
	public void displayInfo(String info) {
		JOptionPane.showMessageDialog(this, info, "PAYMENT INFO",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayWarn(String info) {
		JOptionPane.showMessageDialog(this, info, "PAYMENT INFO",JOptionPane.WARNING_MESSAGE);
	}
	
	public IPayment displayPaymentOption() throws NumberFormatException{
		String[] option = {"PayPall", "Wallet"};
		int met=JOptionPane.showOptionDialog(
                this,                               
                "Scegli il metodo di pagamento:",    
                "Metodo di Pagamento",               
                JOptionPane.DEFAULT_OPTION,          
                JOptionPane.INFORMATION_MESSAGE,     
                null,                                
                option,                            
                option[0]                          
        );
		IPayment mode=null;
		switch (met) {
	    case 0:
	        mode = PaymentFactory.getPayPalAdapter();
	        break;
	    case 1:
	        mode = PaymentFactory.getWalletPaymentAdapter();
	        break;
		}
		return mode;
	}
	
	public int displayConfirm() {
		return JOptionPane.showConfirmDialog(this, "are you shure to continue?");
	}
	
	public JPanel getShopPan() {
		return shopPan;
	}
	
	public JPanel getKartPanel() {
		return kartPan;
	}
	
	public ArrayList<JButton> getShopButts(){
		return shopbutts;
	}
	
	public HashSet<JButton> getKartButts(){
		return kartbutts;
	}	

	public JButton getKart() {
		return kart;
	}

	public JButton getShop() {
		return shop;
	}
	
	public JButton getPay() {
		return pay;
	}
	
	public JButton getPrime() {
		return prime;
	}
	
	public JLabel getInfoLab() {
		return kartInfoLab;
	}

	public void setInfoLab(JLabel infoLab) {
		this.kartInfoLab = infoLab;
	}
	
	public void setInfoLabText(int i) {
		this.kartInfoLab.setText("elementi nel carrello: "+i);
	}

	public JButton getOrders() {
		return orders;
	}

	public void setOrders(JButton orders) {
		this.orders = orders;
	}

}
