package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnFACADE;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnServiceDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnView;

public class ReturnController {
	private ReturnFACADE returnFacade;
	private ReturnItemsAndReasonsView riarView;
	private static final String MOTIVAZIONE_PERSONALIZZATA ="Altro";

	/*
	 * Controller for ReturnItemsAndReasonsView: it manages the choice of items to return, the reasons and the refund method
	 */
	public ReturnController(ReturnFACADE returnFacade,ReturnItemsAndReasonsView riarView) {
		this.returnFacade=returnFacade;
		this.riarView=riarView;
		addItemsAndReasonsToReturnService();
		initActionAndStateOfTheComponent();
	}

	private void addItemsAndReasonsToReturnService() {
		/*
		 * Listener for NextButton: addItemToReturn
		 */
		ActionListener NextButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}
			private void manageAction() {
				riarView.setVisible(false);
				
				/* 1) Management of product choice and reasons */
				ArrayList<JCheckBox> checkBoxList = riarView.getCheckBoxList();
				ArrayList<JComboBox<String>> reasonsDropdownList = riarView.getReasonsDropdownList();

				boolean itemSelected = false;
				for (int i = 0; i < checkBoxList.size(); i++) {
					JCheckBox checkBox = checkBoxList.get(i);
					JComboBox<String> comboBox = reasonsDropdownList.get(i);

					if (checkBox.isSelected()) {
						itemSelected=true;
						String reason = comboBox.getSelectedItem().toString();
						String sku = checkBox.getActionCommand();
						InventoryItem inventoryItem=InventoryDAOFacade.getInstance().findInventoryItemBySku(sku);
						if(reason.equals(MOTIVAZIONE_PERSONALIZZATA)) {
							reason = riarView.getCustomReasonAreaList().get(i).getText();
						}
						try {
							returnFacade.addItemToReturn(inventoryItem, reason);
						} catch (UnableToReturnException e) {
							// TODO Auto-generated catch block
							riarView.setVisible(true);
							riarView.showErrorMessagge(e.getMessage());
							removeItemsNotActuallyReturned();
							return;
						} 
						catch (MissingReasonException e) {
							// TODO Auto-generated catch block
							riarView.setVisible(true);
							riarView.showErrorMessagge(e.getMessage());
							removeItemsNotActuallyReturned();
							return;
						}
					}
				}
				if (!itemSelected) {
					riarView.setVisible(true);
					riarView.showWarningMessagge("Selezionare almeno un prodotto da restituire");
					return;
				}
				
				
				
				
				/* 2) Refund management */
				StringBuilder message = new StringBuilder(returnFacade.toString()).append("Metodo di rimborso selezionato:\n");
				ButtonModel button=riarView.getRefundButtonGroup().getSelection();
				if(button==null) {
					riarView.setVisible(true);
					riarView.showWarningMessagge("Indicare la modalità di rimborso");
					removeItemsNotActuallyReturned();
					return;
				}
				message.append(button.getActionCommand());
				
				// Recap popup to confirm or cancel the return.
				int recapPopup = riarView.showConfirmPopUp(message.toString());
				if(recapPopup==JOptionPane.OK_OPTION) {
					//String clientEmail=SingletonManager.getInstance().getLoggedUser().getEmail();
					double moneyToBeReturned=returnFacade.getMoneyToBeReturned();
					if (button.getActionCommand().equals(ReturnItemsAndReasonsView.getBankTransferRadioText())) {
						BankTransfer br = new BankTransfer(moneyToBeReturned,"EMAIL MAGAZZINO DA DEFINIRE","QUI METTERE: SingletonManager.getInstance().getLoggedUser().getEmail(); "); //chi istanzia bonifico?
						try {
							IRefund refundMode=RefundFactory.getBankTransferAdapter(br);
							returnFacade.setRefundMode(refundMode);
							returnFacade.setMoneyAlreadyReturned(returnFacade.getMoneyAlreadyReturned()+br.getValue());
							returnFacade.AddReturnToDB(refundMode);
							riarView.showSuccessDialog("Pagamento andato a buon fine");
						} catch (PaymentException e) {
							// TODO Auto-generated catch block
							riarView.setVisible(true);
							riarView.showErrorMessagge("Operazione fallita, verificare i dati di pagamento");
							removeItemsNotActuallyReturned();
							return;
						}
					} else {
						VoucherRefund vr = new VoucherRefund(moneyToBeReturned);
						try {
							IRefund refundMode=RefundFactory.getVoucherAdapter(vr);
							returnFacade.setRefundMode(refundMode);
							returnFacade.setMoneyAlreadyReturned(returnFacade.getMoneyAlreadyReturned()+vr.getValue());
							returnFacade.AddReturnToDB(refundMode);
							riarView.showSuccessDialog("Pagamento andato a buon fine");
						} catch (PaymentException e) {
							// TODO Auto-generated catch block
							riarView.setVisible(true);
							riarView.showErrorMessagge("Operazione fallita, verificare i dati di pagamento");
							removeItemsNotActuallyReturned();
							return;
						}
					}
				}
				else {
					riarView.setVisible(true);
					removeItemsNotActuallyReturned();
				}
			}
		};
		riarView.getNextButton().addActionListener(NextButtonLister);
	}
	
	private void removeItemsNotActuallyReturned() {
		// TODO Auto-generated method stub
		returnFacade.removeAllFromReturn();
		ReturnService returnService=returnFacade.getRs();
		returnService.setReturnedItems(ReturnServiceDAOFacade.getIstance().readItemAndReason(returnService.getReturnableOrder()));
	}
	
	
	
	
	
	
	
	
	


	private void initActionAndStateOfTheComponent() {
		/*
		 * Listener for the checkBox: If you don't select an item you won't be able to choose the reason
		 */
		ItemListener addItemListener=new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					// Abilita la JComboBox corrispondente quando la casella di controllo viene selezionata
					int index = riarView.getCheckBoxList().indexOf(e.getSource());
					riarView.getReasonsDropdownList().get(index).setEnabled(true);
				} else if(e.getStateChange() == ItemEvent.DESELECTED) {
					// Disabilita la JComboBox corrispondente quando la casella di controllo viene deselezionata
					int index = riarView.getCheckBoxList().indexOf(e.getSource());
					riarView.getReasonsDropdownList().get(index).setSelectedItem("Scegli una motivazione");
					riarView.getReasonsDropdownList().get(index).setEnabled(false);
				}
			}
		};
		for(int i=0;i<riarView.getCheckBoxList().size();i++) {
			riarView.getCheckBoxList().get(i).addItemListener(addItemListener);
		}


		/*
		 * Listener for the reasons.
		 */
		ActionListener comboListener=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction(e);
			}
			private void manageAction(ActionEvent e) {
				ArrayList<JComboBox<String>> reasonsDropdownList = riarView.getReasonsDropdownList();

				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				String selectedReason = (String) combo.getSelectedItem();
				int index = reasonsDropdownList.indexOf(combo);
				riarView.getCustomReasonLabelList().get(index).setVisible(selectedReason.equals("Altro"));
				riarView.getCustomReasonAreaList().get(index).setVisible(selectedReason.equals("Altro"));
			}

		};
		for(int i=0;i<riarView.getReasonsDropdownList().size();i++) {
			riarView.getReasonsDropdownList().get(i).addActionListener(comboListener);
		}
		/*
		 * Listener for the backButton
		 */
		ActionListener backButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}
			private void manageAction() {
				riarView.setVisible(false);
				//new ReturnView(SingletonManager.getInstance().getLoggedUser());
			}

		};
		riarView.getBackButton().addActionListener(backButtonLister);
	}
}