package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.Database.ReturnServiceFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;

public class ReturnView extends JFrame{

	private JPanel mainPanel;
	private JPanel selectOrderPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private ButtonGroup orderButtonGroup;
	private JButton confirmButton;
	private JButton backButton;


	public ReturnView(Client client) {
		setTitle("Return Service");
		setSize(400, 200);

		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		// Imposto le dimensioni della finestra in base alle dimensioni dello schermo
		int windowWidth = (int) (screenWidth*0.9); 
		int windowHeight = (int) (screenHeight*0.9); 
		setSize(windowWidth, windowHeight);
		setLocationRelativeTo(null); // Centro la finestra
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		selectOrderPanel = new JPanel();
		selectOrderPanel.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
		JLabel selectOrderLabel = new JLabel("Seleziona un ordine:");
		selectOrderPanel.add(selectOrderLabel);


		ArrayList<Order> allClientOrders = SingletonManager.getInstance().getRegisterFacade().selectOrderWhereClient(client.getEmail()); 
		ArrayList<String> ordersDescriptionsForButton = new ArrayList<>();
		Integer[] orderIdForActionCommand=new Integer[allClientOrders.size()];
		int count=0;
		for(Order or: allClientOrders){
			ordersDescriptionsForButton.add(or.toString());
			orderIdForActionCommand[count]=or.getId();
			count++;
		}
		orderButtonGroup = new ButtonGroup();
		count=0;
		for (String i : ordersDescriptionsForButton) {
			JRadioButton radioButton = new JRadioButton(i);
			orderButtonGroup.add(radioButton);
			radioButton.setActionCommand(orderIdForActionCommand[count].toString());
			count++;
			selectOrderPanel.add(radioButton, BorderLayout.LINE_START);
		}
		mainPanel.add(selectOrderPanel, BorderLayout.CENTER);

		confirmPanel = new JPanel();
		confirmButton = new JButton("Conferma Scelta");
		//confirmButton.setIcon(new ImageIcon("check_icon.png")); // Aggiungo un'icona al pulsante
		confirmPanel.add(confirmButton);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);


		backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
		backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
		backPanel.add(backButton);
		mainPanel.add(backPanel, BorderLayout.NORTH);
		//pack();
		setVisible(true);

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
}






















