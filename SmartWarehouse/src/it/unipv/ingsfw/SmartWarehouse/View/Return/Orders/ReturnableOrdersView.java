//
package it.unipv.ingsfw.SmartWarehouse.View.Return.Orders;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.BackButtonListenerForReturnableOrdersView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.WindowClosingListenerReturnableOrderView;

public class ReturnableOrdersView extends JFrame{

	private JPanel mainPanel;
	private SelectOrderPanel selectOrderPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private ButtonGroup orderButtonGroup;
	private JButton confirmButton;
	private JButton backButton;
	private ShopFrame shopFrame;

	/*
	 * Init the View
	 */
	public ReturnableOrdersView(ShopFrame shopFrame) {
		super();
		this.shopFrame=shopFrame;
		setTitle("Return Service");
		setSize(1500, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.repaint();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		selectOrderPanel = new SelectOrderPanel();
		mainPanel.add(selectOrderPanel.getScrollPane(),BorderLayout.CENTER);
		
	}
	public void initWithClientOrders(ArrayList<String> ordersDescriptionsForButton, Integer[] orderIdForActionCommand) {
		orderButtonGroup = new ButtonGroup();
		int count=0;
		for (String i : ordersDescriptionsForButton) {
			JRadioButton radioButton = new JRadioButton(i);
			orderButtonGroup.add(radioButton);
			radioButton.setActionCommand(orderIdForActionCommand[count].toString());
			count++;
			selectOrderPanel.add(radioButton, BorderLayout.LINE_START);
		}
		completeTheView();
	}
	private void completeTheView() {
		// TODO Auto-generated method stub
		confirmPanel = new JPanel();
		confirmPanel.repaint();
		confirmButton = new JButton("Confirm >");
		confirmPanel.add(confirmButton);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);

		backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
		backPanel.repaint();
		backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
		backPanel.add(backButton);
		mainPanel.add(backPanel, BorderLayout.NORTH);
		
		addBackButtonListener();
		addWindowClosingListener();
		
		setVisible(true);

	}
	private void addBackButtonListener() {
		BackButtonListenerForReturnableOrdersView bb=new BackButtonListenerForReturnableOrdersView(this,this.shopFrame);
		this.backButton.addActionListener(bb);
		
	}
	private void addWindowClosingListener() {
		WindowClosingListenerReturnableOrderView wcl=new WindowClosingListenerReturnableOrderView(this);
		 this.addWindowListener(wcl);
		
	}
	public void showWarningMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.WARNING_MESSAGE);
	}
	public void showErrorMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}


	public ButtonGroup getOrderButtonGroup() {
		return orderButtonGroup;
	}
	public JButton getConfirmButton() {
		return confirmButton;
	}
	public ShopFrame getShopFrame() {
		return shopFrame;
	}
}






















