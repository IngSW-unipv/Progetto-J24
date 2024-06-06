
//
package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public class ReturnItemsAndReasonsView extends JFrame{
	private JPanel mainPanel;
	private OrderDetailsPanel orderDetailsPanel;
	private ItemAndReasonPanel itemAndReasonPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private JPanel refundPanel;
	private JLabel selectedOrderLabel;
	private ArrayList<JCheckBox> checkBoxList=new ArrayList<JCheckBox>();
	private ArrayList< JComboBox<String> > reasonsDropdownList=new ArrayList< JComboBox<String> >();
	private ArrayList<JTextArea> customReasonAreaList=new ArrayList<JTextArea>();
	private ArrayList<JLabel> customReasonLabelList=new ArrayList<JLabel>();
	private ButtonGroup refundButtonGroup;
	public static final String VOUCHER_RADIO_TEXT = "VOUCHER (consigliato): ricevi il rimborso all'istante su un voucher senza scadenza spendibile in tutto lo shop.";
	public static final String BANK_TRANSFER_RADIO_TEXT = "Bonifico Bancario: ricevi un bonifico sulla carta che hai utilizzato per effettuare l'acquisto. Tempi stimati 14 giorni lavorativi.";

	private JButton backButton;
	private JButton nextButton;



	public ReturnItemsAndReasonsView() {
		setTitle("Items and Reasons");
		setSize(600,400);

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // Centra la finestra
		setResizable(true);

		mainPanel = new JPanel();
		mainPanel.repaint();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		orderDetailsPanel=new OrderDetailsPanel();
		mainPanel.add(orderDetailsPanel.getScrollPane(), BorderLayout.WEST);
	}
	
	public void initWithItemOfTheOrder(ArrayList<String> itemsDescriptionsForButton, String[] skuForActionCommand,Map<String, String> map) {
		// TODO Auto-generated method stub
		int count3=0;
		for (String i : itemsDescriptionsForButton) {
			itemAndReasonPanel= new ItemAndReasonPanel();
			itemAndReasonPanel.constraintsForCheckBox();


			JCheckBox checkBox=new JCheckBox(i);
			checkBox.setActionCommand(skuForActionCommand[count3]);
			count3++;
			checkBoxList.add(checkBox);
			itemAndReasonPanel.add(checkBox,gbc);

			gbc.gridx = 1;
			JComboBox<String> reasonsDropdown = new JComboBox<String>(map.values().toArray(new String[0]));
			reasonsDropdown.setSelectedItem("Scegli una motivazione");
			reasonsDropdown.setEnabled(false);
			reasonsDropdownList.add(reasonsDropdown);
			itemAndReasonPanel.add(reasonsDropdown, gbc);

			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(10, 0, 0, 0); // Aggiungi spazio tra il JComboBox e la JLabel

			JLabel customReasonLabel=new JLabel("Descrivi il motivo della restituzione");
			itemAndReasonPanel.add(customReasonLabel, gbc);

			JTextArea customReasonArea = new JTextArea(4, 10); // 2 righe e 10 colonne
			customReasonArea.setColumns(10);
			customReasonArea.setMinimumSize(new Dimension(1, 1)); // Set a minimum size
			customReasonArea.setLineWrap(true); // Abilita l'andare a capo automatico
			customReasonArea.setWrapStyleWord(true); // Evita di spezzare le parole

			LineBorder border = new LineBorder(Color.BLACK); 
			customReasonArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Aggiungi spaziatura interna

			customReasonArea.setVisible(false);
			customReasonLabel.setVisible(false);
			customReasonAreaList.add(customReasonArea); 
			customReasonLabelList.add(customReasonLabel);
			gbc.gridy = GridBagConstraints.RELATIVE;

			itemAndReasonPanel.add(customReasonArea, gbc);
			orderDetailsPanel.add(itemAndReasonPanel);
		}

		//mainPanel.add(orderDetailsPanel, BorderLayout.CENTER);
		completeTheView();

	}

	private void completeTheView() {
		// TODO Auto-generated method stub
		confirmPanel = new JPanel();
		confirmPanel.repaint();
		nextButton = new JButton("Next");
		confirmPanel.add(nextButton);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);

		backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
		backPanel.repaint();
		backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
		backPanel.add(backButton);
		mainPanel.add(backPanel, BorderLayout.NORTH);


		refundPanel = new JPanel();
		refundPanel.repaint();
		refundPanel.setLayout(new GridLayout(0, 1));
		//refundPanel.setLayout(new BoxLayout(refundPanel, BoxLayout.Y_AXIS));
		JLabel refundLabel = new JLabel("Scegli la modalità con cui preferisci essere rimborsato:");
		JRadioButton voucherRadioButton = new JRadioButton(VOUCHER_RADIO_TEXT);
		JRadioButton bankTransferRadioButton = new JRadioButton(BANK_TRANSFER_RADIO_TEXT);
		voucherRadioButton.setActionCommand(VOUCHER_RADIO_TEXT);
		bankTransferRadioButton.setActionCommand(BANK_TRANSFER_RADIO_TEXT);
		refundButtonGroup = new ButtonGroup();
		refundButtonGroup.add(voucherRadioButton);
		refundButtonGroup.add(bankTransferRadioButton);
		refundPanel.add(refundLabel);
		refundPanel.add(voucherRadioButton);
		refundPanel.add(bankTransferRadioButton);

		// Aggiungi bordi evidenziati
		refundPanel.setBorder(BorderFactory.createTitledBorder("Modalità di Rimborso")); //   bordo titolato
		refundPanel.setBackground(Color.cyan); // colore di sfondo
		JPanel uselessPanel=new JPanel(new BorderLayout());
		uselessPanel.repaint();
		mainPanel.add(uselessPanel,BorderLayout.EAST);
		uselessPanel.add(refundPanel,BorderLayout.SOUTH);

		//pack();
		setVisible(true);

	}
	public int showConfirmPopUp(String recap) {
		return JOptionPane.showConfirmDialog(this, recap, "Conferma restituzione", JOptionPane.OK_CANCEL_OPTION);

	}
	public void showWarningMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.WARNING_MESSAGE);
	}
	public void showErrorMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	public void showSuccessDialog(String message) {
		String[] options = {"Continua a navigare nello shop", "chiudi"};
		int n = JOptionPane.showOptionDialog(this,message,
				"Operazione di Reso completata con successo",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]);
		if (n == JOptionPane.YES_OPTION) {
			this.dispose();
		} else if (n == JOptionPane.NO_OPTION) {
			// new ShopView ecc.
		}
	}


	public static String getVoucherRadioText() {
		return VOUCHER_RADIO_TEXT;
	}

	public static String getBankTransferRadioText() {
		return BANK_TRANSFER_RADIO_TEXT;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public ArrayList<JCheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public ArrayList<JComboBox<String>> getReasonsDropdownList() {
		return reasonsDropdownList;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public ArrayList<JTextArea> getCustomReasonAreaList() {
		return customReasonAreaList;
	}

	public ArrayList<JLabel> getCustomReasonLabelList() {
		return customReasonLabelList;
	}

	public JPanel getItemAndReasonsPanel() {
		return itemAndReasonPanel;
	}
	public ButtonGroup getRefundButtonGroup() {
		return refundButtonGroup;
	}

	public JLabel getSelectedOrderLabel() {
		return selectedOrderLabel;
	}


}

