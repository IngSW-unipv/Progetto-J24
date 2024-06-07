//
package it.unipv.ingsfw.SmartWarehouse.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnServiceDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.RegisterFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.Return.Orders.ReturnableOrdersView;

public class ReturnController {
	private ReturnService returnService;
	private ReturnItemsAndReasonsView riarView;
	private static final String MOTIVAZIONE_PERSONALIZZATA ="Altro";

	/*
	 * Controller for ReturnItemsAndReasonsView: it manages the choice of items to return, the reasons and the refund method
	 */
	public ReturnController(ReturnService returnService,ReturnItemsAndReasonsView riarView) {
		this.returnService=returnService;
		this.riarView=riarView;
		initWithItemOfTheOrder();
		addItemsAndReasonsToReturnService();
		initActionAndStateOfTheComponent();
	}

	private void initWithItemOfTheOrder() {
		int selectedOrderId=returnService.getReturnableOrder().getId();
		riarView.getSelectedOrderLabel().setText("Ordine selezionato: " +selectedOrderId);
		Order order = RegisterFacade.getIstance().selectOrder(selectedOrderId);
		String skuForActionCommand[]=new String[order.getQtyTotal()];
		ArrayList<IInventoryItem> inventoryItem_keyOfOrderMap= new ArrayList<>(order.getMap().keySet());
		ArrayList<String> itemsDescriptionsForButton= new ArrayList<>();
		int count=0;
		for(IInventoryItem i:inventoryItem_keyOfOrderMap) {
			for(int count2=0;count2<order.getQtyOfItem((InventoryItem) i);count2++) {
				itemsDescriptionsForButton.add(i.getDescription());
				skuForActionCommand[count]=i.getSku();
				count++;
			}
		}
		Reasons.initializeReasons();
		riarView.initWithItemOfTheOrder(itemsDescriptionsForButton,skuForActionCommand,Reasons.getReasons());
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
				IInventoryItem inventoryItem = null;
				for (int i = 0; i < checkBoxList.size(); i++) {
					JCheckBox checkBox = checkBoxList.get(i);
					JComboBox<String> comboBox = reasonsDropdownList.get(i);

					if (checkBox.isSelected()) {
						itemSelected=true;
						String reason = comboBox.getSelectedItem().toString();
						String sku = checkBox.getActionCommand();
						inventoryItem=InventoryDAOFacade.getInstance().findInventoryItemBySku(sku);
						if(reason.equals(MOTIVAZIONE_PERSONALIZZATA)) {
							reason = riarView.getCustomReasonAreaList().get(i).getText();
						}
						/* add item to return e then increase qty in warehouse */  
						try {
							returnService.addItemToReturn(inventoryItem, reason);
						} catch (MissingReasonException | UnableToReturnException e) {
							removeItemsNotActuallyReturned();
							riarView.setVisible(true);
							riarView.showErrorMessagge(e.getMessage());
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
				StringBuilder message = new StringBuilder(returnService.toString()).append("Metodo di rimborso selezionato:\n");
				ButtonModel button=riarView.getRefundButtonGroup().getSelection();
				if(button==null) {
					removeItemsNotActuallyReturned();
					riarView.setVisible(true);
					riarView.showWarningMessagge("Indicare la modalità di rimborso");

					return;
				}
				message.append(button.getActionCommand());

				// Recap popup to confirm or cancel the return.
				int recapPopup = riarView.showConfirmPopUp(message.toString());
				if(recapPopup==JOptionPane.OK_OPTION) {
					IRefund refundMode;
					if (button.getActionCommand().equals(ReturnItemsAndReasonsView.getBankTransferRadioText())) {
						BankTransfer br = new BankTransfer(returnService.getMoneyToBeReturned(),"EMAIL MAGAZZINO DA DEFINIRE",SingletonManager.getInstance().getLoggedUser().getEmail());
						refundMode=RefundFactory.getBankTransferAdapter(br);
					}
					else {
						VoucherRefund vr = new VoucherRefund(returnService.getMoneyToBeReturned());
						refundMode=RefundFactory.getVoucherAdapter(vr);
					}
					try {
						returnService.issueRefund(refundMode);
					} catch (PaymentException e) {
						removeItemsNotActuallyReturned();
						riarView.setVisible(true);
						riarView.showErrorMessagge(e.getMessage());
						return;
					}
					returnService.setMoneyAlreadyReturned(returnService.getMoneyAlreadyReturned()+refundMode.getValue());
					returnService.updateWarehouseQty();
					returnService.AddReturnToDB(refundMode);
					riarView.showSuccessDialog("Pagamento andato a buon fine");
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
		returnService.removeAllFromReturn();
		returnService.setReturnedItems(ReturnServiceDAOFacade.getIstance().readItemAndReason(returnService.getReturnableOrder()));
	}











	private void initActionAndStateOfTheComponent() {
		/*
		 * Listener for the checkBox: If you don't select an item you won't be able to choose the reason
		 */
		/*
		ItemListener addItemListener=new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					// Abilito la JComboBox corrispondente quando la casella di controllo viene selezionata
					int index = riarView.getCheckBoxList().indexOf(e.getSource());
					riarView.getReasonsDropdownList().get(index).setEnabled(true);
				} else if(e.getStateChange() == ItemEvent.DESELECTED) {
					// Disabilito la JComboBox corrispondente quando la casella di controllo viene deselezionata
					int index = riarView.getCheckBoxList().indexOf(e.getSource());
					riarView.getReasonsDropdownList().get(index).setSelectedItem("Scegli una motivazione");
					riarView.getReasonsDropdownList().get(index).setEnabled(false);
				}
			}
		};
		for(int i=0;i<riarView.getCheckBoxList().size();i++) {
			riarView.getCheckBoxList().get(i).addItemListener(addItemListener);
		} /*


		/*
		 * Listener for the reasons.
		 */
		/*
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
		 */
		
		
		
		
		
		/*
		 * Listener for the backButton
		 */
		/*
		ActionListener backButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}
			private void manageAction() {
				riarView.setVisible(false);
				returnableOrdersView.setVisible(true);
			}

		};
		riarView.getBackButton().addActionListener(backButtonLister);
		*/
	}
}