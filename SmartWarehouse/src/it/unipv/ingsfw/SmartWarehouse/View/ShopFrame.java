
package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;

@SuppressWarnings("serial")
public class ShopFrame extends JFrame {

	private JPanel shopPan,kartPan,infoPan;
	private ArrayList<JButton> shopbutts;
	private HashSet<JButton> kartbutts;
	private JButton kart,shop,pay,orders,prime, chargeWallet;
	private JLabel kartInfoLab, wallet;
	private JScrollPane scrollShop, scrollKart;

	public ShopFrame() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("SmartWarehouse/Shop");
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		kart =new JButton("Kart->");
		shop =new JButton("<-Shop");
		pay =new JButton("PAY");
		orders =new JButton("Orders");
		prime =new JButton("Get Your Prime");
		chargeWallet =new JButton("Recharge Wallet");
		
		shop.setPreferredSize(new Dimension(90, 40));
		kart.setPreferredSize(new Dimension(90, 40));
		pay.setPreferredSize(new Dimension(90, 40));
		orders.setPreferredSize(new Dimension(110, 40));
		prime.setPreferredSize(new Dimension(90, 40));
		chargeWallet.setPreferredSize(new Dimension(90, 40));
		
		kart.setFocusable(false);
		shop.setFocusable(false);
		pay.setFocusable(false);
		orders.setFocusable(false);
		orders.setVisible(true);
		prime.setVisible(true);
		chargeWallet.setVisible(true);
		
		shopPan=new JPanel();
		kartPan=new JPanel();
		infoPan=new JPanel();
		
		shopPan.setLayout(new GridLayout(10, 10));
		kartPan.setLayout(new GridLayout(10, 10));
		
		kartInfoLab=new JLabel();
		kartInfoLab.setPreferredSize(new Dimension(200,80));
		kartInfoLab.setText("elementi nel carrello: 0");
		infoPan.add(kartInfoLab);
		
		wallet=new JLabel();
		wallet.setPreferredSize(new Dimension(200,80));
		wallet.setText("Wallet: 0 euro");
		infoPan.add(wallet);
		infoPan.add(chargeWallet);
		
		JToolBar bar=new JToolBar();
		bar.add(kart);
		bar.add(shop);
		bar.add(pay);
		bar.add(orders);
		bar.add(prime);
		
		scrollShop = new JScrollPane(shopPan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollKart = new JScrollPane(kartPan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.add(bar, BorderLayout.NORTH);
		this.add(scrollShop, BorderLayout.CENTER);
		this.add(infoPan, BorderLayout.SOUTH);
	}
	
	public void makeShop(InventoryManager inv) {
		shopbutts = new ArrayList<JButton>();
		for(IInventoryItem i: inv.getInventory()) {
			if(i.getQty() > SmartWarehouseInfoPoint.Soglia) {
				JButton b=new JButton(i.getDescription()+": "+i.getPrice()+"$");
				b.setFocusable(false);
				b.setPreferredSize(new Dimension(300,90));
				b.setActionCommand(i.getSku());
				shopbutts.add(b);
				shopPan.add(b);
			}
		}
		
		shop.setVisible(false);
		kart.setVisible(true);
		pay.setVisible(false);
	}
	
	public void makeKart(HashSet<IInventoryItem> i, HashMap<IInventoryItem, Integer> sq) {
		kartbutts = new HashSet<JButton>();
		kartPan.removeAll();
		
		for(IInventoryItem it: i) {
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
		scrollShop.setVisible(true);
		scrollKart.setVisible(false);
		this.add(scrollShop, BorderLayout.CENTER);
		
		shop.setVisible(false);
		kart.setVisible(true);
		pay.setVisible(false);
		this.setTitle("SmartWarehouse/Shop");
	}
	
	public void showKart() {
		scrollKart.setVisible(true);
		scrollShop.setVisible(false);
		this.add(scrollKart, BorderLayout.CENTER);
		
		shop.setVisible(true);
		kart.setVisible(false);
		pay.setVisible(true);
		this.setTitle("SmartWarehouse/Shop/Kart");
	}
	
	public int displayOption()throws NumberFormatException{
		String ret;
		int q = 0;
		ret=JOptionPane.showInputDialog("insert quantity:");
		if(ret != null) {
			q = Integer.parseInt(ret);
		}
		else throw new NumberFormatException();
		return q;
	}
	
	public void displayInfo(String info) {
		JOptionPane.showMessageDialog(this, info, "PAYMENT INFO",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayWarn(String info) {
		JOptionPane.showMessageDialog(this, info, "PAYMENT INFO",JOptionPane.WARNING_MESSAGE);
	}
	
	public int displayPaymentOption() throws NumberFormatException{
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
		return met;
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
	
	public void setWalletLabImp(double tot) {
		this.wallet.setText("Wallet: "+ tot +" euro");
	}
	
	public JLabel getWalletLab() {
		return this.wallet;
	}

	public JButton getOrders() {
		return orders;
	}

	public void setOrders(JButton orders) {
		this.orders = orders;
	}

	public JButton getChargeWallet() {
		return chargeWallet;
	}

}
