package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ReturnableOrdersView extends JFrame{

	private JPanel mainPanel;
	private JPanel selectOrderPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private ButtonGroup orderButtonGroup;
	private JButton confirmButton;
	private JButton backButton;


	/*
	 * Init the View
	 */
	public ReturnableOrdersView() {
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
		int windowWidth = (int) (screenWidth*0.9); 
		int windowHeight = (int) (screenHeight*0.9); 
		setSize(windowWidth, windowHeight);
		setLocationRelativeTo(null); // Centro la finestra
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		mainPanel = new JPanel();
		mainPanel.repaint();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		selectOrderPanel = new JPanel();
		selectOrderPanel.repaint();
		selectOrderPanel.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
		JLabel selectOrderLabel = new JLabel("Qui puoi vedere gli ordini da te effettuati/n Seleziona un ordine per cominciare la procedura di reso\n");
		selectOrderPanel.add(selectOrderLabel);
		
		
		
		
		
		
		//TODO initWithClientOrders() chiamato dal controller.... Qui sotto fai un altro metodo completeView() chiamaro da initWithClientOrders
		
		
		
		
	
		confirmPanel = new JPanel();
		confirmPanel.repaint();
		confirmButton = new JButton("Conferma Scelta");
		confirmPanel.add(confirmButton);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);

		backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
		backPanel.repaint();
		backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
		backPanel.add(backButton);
		mainPanel.add(backPanel, BorderLayout.NORTH);
		//pack();
		setVisible(true);

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
		mainPanel.add(selectOrderPanel, BorderLayout.CENTER);
		
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






















