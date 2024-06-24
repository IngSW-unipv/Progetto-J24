

//
package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.BackButtonListenerForReturnItemsAndReasonView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.CheckBoxListener;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.ComboBoxListener;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.DeselectAllButtonListener;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.InfoPointButtonListener;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.SelectAllButtonListener;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Listener.WindowClosingListenerRiarView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

public class ReturnItemsAndReasonsView extends JFrame{
	private JPanel mainPanel;
	private OrderDetailsPanel orderDetailsPanel;
	private ItemAndReasonPanel itemAndReasonPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private RefundPanel refundPanel;
	private ArrayList<JCheckBox> checkBoxList=new ArrayList<JCheckBox>();
	private ArrayList< JComboBox<String> > reasonsDropdownList=new ArrayList< JComboBox<String> >();
	private ArrayList<CustomReasonArea> customReasonAreaList=new ArrayList<CustomReasonArea>();
	private ArrayList<JLabel> customReasonLabelList=new ArrayList<JLabel>();
	private static final String MOTIVAZIONE_PERSONALIZZATA="Other";
	private JButton backButton;
	private JButton nextButton;
	private ReturnableOrdersView returnableOrdersView;
	private ShopFrame shopFrame;
	private JButton selectAllButton;
	private JButton deselectAllButton;
	private JButton infoPointButton;




	public ReturnItemsAndReasonsView(ReturnableOrdersView returnableOrdersView, ShopFrame shopFrame) {
		super();
		this.shopFrame=shopFrame;
		this.returnableOrdersView=returnableOrdersView;

		setTitle("Items and Reasons");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1500,800);

		mainPanel = new JPanel();
		mainPanel.repaint();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		orderDetailsPanel=new OrderDetailsPanel();
		mainPanel.add(orderDetailsPanel.getScrollPane(), BorderLayout.WEST);
		addWindowClosingListener();
	}

	public void initWithItemOfTheOrder(ArrayList<String> itemsDescriptionsForButton, String[] skuForActionCommand,Map<String, String> mapOfReasons) {
		int count3=0;
		for (String i : itemsDescriptionsForButton) {
			itemAndReasonPanel= new ItemAndReasonPanel();
			itemAndReasonPanel.constraintsForCheckBox();


			JCheckBox checkBox=new JCheckBox(i);
			checkBox.setActionCommand(skuForActionCommand[count3]);
			count3++;
			checkBoxList.add(checkBox);
			itemAndReasonPanel.add(checkBox,itemAndReasonPanel.getGbc());

			itemAndReasonPanel.constraintsForComboBox();
			JComboBox<String> reasonsDropdown = new JComboBox<String>();
			reasonsDropdown.addItem("Choose a reason");
			for (String reason : mapOfReasons.values()) {
				reasonsDropdown.addItem(reason);
			}
			reasonsDropdown.setSelectedItem("Choose a reason");
			reasonsDropdown.setEnabled(false);
			reasonsDropdownList.add(reasonsDropdown);
			itemAndReasonPanel.add(reasonsDropdown, itemAndReasonPanel.getGbc());


			itemAndReasonPanel.constraintsForTextArea();
			JLabel customReasonLabel=new JLabel("Describe the reason for the return");
			itemAndReasonPanel.add(customReasonLabel, itemAndReasonPanel.getGbc());

			CustomReasonArea customReasonArea = new CustomReasonArea(); // 2 righe e 10 colonne
			customReasonLabel.setVisible(false);
			customReasonAreaList.add(customReasonArea); 
			customReasonLabelList.add(customReasonLabel);
			itemAndReasonPanel.getGbc().gridy = GridBagConstraints.RELATIVE;

			itemAndReasonPanel.add(customReasonArea, itemAndReasonPanel.getGbc());
			orderDetailsPanel.add(itemAndReasonPanel);
		}
		addCheckBoxListener();
		addComboBoxListener();
		//mainPanel.add(orderDetailsPanel, BorderLayout.CENTER);
		completeTheView();

	}

	private void completeTheView() {
		confirmPanel = new JPanel();
		confirmPanel.repaint();
		nextButton = new JButton("Next");
		confirmPanel.add(nextButton);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);

		backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
		backPanel.repaint();
		backButton = new JButton("< Back");
		deselectAllButton=new JButton("Deselect All");
		deselectAllButton.setEnabled(false);
		selectAllButton=new JButton("Select All");
		infoPointButton=new JButton("help");
		backPanel.add(backButton);
		backPanel.add(deselectAllButton);
		backPanel.add(selectAllButton);
		backPanel.add(infoPointButton);
		mainPanel.add(backPanel, BorderLayout.NORTH);


		refundPanel = new RefundPanel();
		JPanel uselessPanel=new JPanel(new BorderLayout());
		uselessPanel.repaint();
		mainPanel.add(uselessPanel,BorderLayout.EAST);
		uselessPanel.add(refundPanel,BorderLayout.SOUTH);

		addBackButtonListener();
		addDeselectAllButtonListener();
		addSelectAllButtonListener();
		setVisible(true);
	}
	
	/** Adding Listener method
	 * 
	 */
	private void addWindowClosingListener() {
		WindowClosingListenerRiarView wcl=new WindowClosingListenerRiarView(this);
		this.addWindowListener(wcl);
	}
	private void addCheckBoxListener() {
		CheckBoxListener checkBoxListener=new CheckBoxListener(this);
		for(int i=0;i<this.getCheckBoxList().size();i++) {
			this.getCheckBoxList().get(i).addItemListener(checkBoxListener);
		}
	}
	private void addComboBoxListener() {
		ComboBoxListener comboBoxListener=new ComboBoxListener(this);
		for(int i=0;i<this.getReasonsDropdownList().size();i++) {
			this.getReasonsDropdownList().get(i).addActionListener(comboBoxListener);
		}
	}
	private void addSelectAllButtonListener() {
		SelectAllButtonListener sabl=new SelectAllButtonListener(this);
		this.selectAllButton.addActionListener(sabl);

	}
	private void addDeselectAllButtonListener() {
		DeselectAllButtonListener dabl=new DeselectAllButtonListener(this);
		this.deselectAllButton.addActionListener(dabl);
	}

	private void addBackButtonListener() {
		BackButtonListenerForReturnItemsAndReasonView bb=new BackButtonListenerForReturnItemsAndReasonView(this, this.returnableOrdersView);
		this.getBackButton().addActionListener(bb);
	}
	public void addInfoPointButtonListener(String returnServiceRecap) {
		InfoPointButtonListener ipbl=new InfoPointButtonListener(returnServiceRecap);
		this.getInfoPointButton().addActionListener(ipbl);		
	}
	
	
	
	public StringBuilder getRecapMessage() {
		StringBuilder message = new StringBuilder("The items you are about to return are:");
		for (int i = 0; i < checkBoxList.size(); i++) {
			JCheckBox checkBox = checkBoxList.get(i);
			JComboBox<String> comboBox = reasonsDropdownList.get(i);
			if(checkBox.isSelected()) {
				message.append("\n").append(checkBox.getText());

				String reason = comboBox.getSelectedItem().toString();
				if(reason.equals(MOTIVAZIONE_PERSONALIZZATA)) {
					reason = this.getCustomReasonAreaList().get(i).getText();
					message.append(". The Reason is: ").append(reason);
				}
				else {
					message.append(". The Reason is: ").append(reason);
				}

			}
		}
		message.append("\nSelected refund method:\n");
		return message;
	}
	public int showConfirmPanel(String refundMethod) {
		String recap=getRecapMessage().append(refundMethod).toString();
		return JOptionPane.showConfirmDialog(this, recap, "Confirm Panel", JOptionPane.OK_CANCEL_OPTION);
	}
	public void showWarningMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.WARNING_MESSAGE);
	}
	public void showErrorMessagge(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	public void showSuccessDialog(String message) {
		String[] options = {"Continue browsing the shop", "close"};
		int n = JOptionPane.showOptionDialog(this,message,
				"Return operation completed successfully",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]);
		if (n == JOptionPane.YES_OPTION) {
			this.shopFrame.setVisible(true);
		} else if (n == JOptionPane.NO_OPTION) {
		}
		this.setVisible(false);
	}


	public static String getVoucherRadioText() {
		return RefundPanel.VOUCHER_RADIO_TEXT;
	}

	public static String getBankTransferRadioText() {
		return RefundPanel.BANK_TRANSFER_RADIO_TEXT;
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

	public ArrayList<CustomReasonArea> getCustomReasonAreaList() {
		return customReasonAreaList;
	}

	public ArrayList<JLabel> getCustomReasonLabelList() {
		return customReasonLabelList;
	}

	public ItemAndReasonPanel getItemAndReasonsPanel() {
		return itemAndReasonPanel;
	}
	public ButtonGroup getRefundButtonGroup() {
		return refundPanel.getRefundButtonGroup();
	}

	public JLabel getSelectedOrderLabel() {
		return orderDetailsPanel.getSelectedOrderLabel();
	}

	public void setTextOfSelectedOrderLabel(int selectedOrderId) {
		orderDetailsPanel.setTextOfSelectedOrderLabel(selectedOrderId);
	}
	public JButton getSelectAllButton() {
		return selectAllButton;
	}

	public JButton getDeselectAllButton() {
		return deselectAllButton;
	}

	public JButton getInfoPointButton() {
		return infoPointButton;
	}

	public void InfoPointButtonManageAction(String string) {

	}
}

