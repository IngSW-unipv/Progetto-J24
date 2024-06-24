package it.unipv.ingsfw.SmartWarehouse.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
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
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class ReturnController {
	private ReturnService returnService;
	private ReturnItemsAndReasonsView riarView;
	private static final String PERSONALIZED_REASON ="Other";
	private static final String NO_REASON ="Choose a reason";
	private static final String MISSING_REASON="Missing reason";
	private static final String NO_ITEM_SELECTED = "Select at least one item to return";
	private static final String NO_REFUND_METHOD = "Missing refund method";
	private static final String PAYMENT_CONFIRMED = "Payment confirmed";

	/**
	 * Controller for ReturnItemsAndReasonsView: it manages the choice of items to return, the reasons and the refund method
	 */
	public ReturnController(ReturnService returnService,ReturnItemsAndReasonsView riarView) {
		this.returnService=returnService;
		this.riarView=riarView;
		initWithItemOfTheOrder();
		addItemsAndReasonsToReturnService();
		infoPointButtonHandlerMethod();
	}

	private void initWithItemOfTheOrder() {
		int selectedOrderId=returnService.getIdOfReturnableOrder();
		riarView.setTextOfSelectedOrderLabel(selectedOrderId);
		Order order = RegisterFacade.getIstance().selectOrder(selectedOrderId);
		String skuForActionCommand[]=new String[order.getQtyTotal()];
		ArrayList<IInventoryItem> inventoryItem_keyOfOrderMap= new ArrayList<>(order.getMap().keySet());
		ArrayList<String> itemsDescriptionsForButton= new ArrayList<>();
		int count=0;
		for(IInventoryItem i:inventoryItem_keyOfOrderMap) {
			for(int count2=0;count2<order.getQtyOfItem(i);count2++) {
				itemsDescriptionsForButton.add(i.getDescription());
				skuForActionCommand[count]=i.getSku();
				count++;
			}
		}
		Reasons.init();
		riarView.initWithItemOfTheOrder(itemsDescriptionsForButton,skuForActionCommand,Reasons.getReasons());
	}

	private void addItemsAndReasonsToReturnService() {
		/**
		 * Listener for NextButton: addItemToReturn
		 */
		ActionListener NextButtonLister=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}
		    private void manageAction() {
		        if (!handleItemSelection()) return;
		        if (!handleRefundSelection()) return;

		        String refundMethod = riarView.getRefundButtonGroup().getSelection().getActionCommand();
		        if (riarView.showConfirmPanel(refundMethod) == JOptionPane.OK_OPTION) {
		            processRefund(refundMethod);
		        } else {
		            riarView.setVisible(true);
		            removeItemsNotActuallyReturned();
		        }
		    }

		    private boolean handleItemSelection() {
		        boolean itemSelected = false;
		        List<JCheckBox> checkBoxList = riarView.getCheckBoxList();
		        List<JComboBox<String>> reasonsDropdownList = riarView.getReasonsDropdownList();

		        for (int i = 0; i < checkBoxList.size(); i++) {
		            JCheckBox checkBox = checkBoxList.get(i);
		            JComboBox<String> comboBox = reasonsDropdownList.get(i);

		            if (checkBox.isSelected()) {
		                itemSelected = true;
		                String reason = comboBox.getSelectedItem().toString();
		                String sku = checkBox.getActionCommand();
		                IInventoryItem inventoryItem = InventoryDAOFacade.getInstance().findInventoryItemBySku(sku);

		                if (reason.equals(PERSONALIZED_REASON)) {
		                    reason = riarView.getCustomReasonAreaList().get(i).getText();
		                }

		                if (reason.equals(NO_REASON)) {
		                    showErrorMessage(MISSING_REASON);
		                    removeItemsNotActuallyReturned();
		                    return false;
		                }

		                try {
		                    returnService.addItemToReturn(inventoryItem, reason);
		                } catch (MissingReasonException | UnableToReturnException e) {
		                    showErrorMessage(e.getMessage());
		                    removeItemsNotActuallyReturned();
		                    return false;
		                }
		            }
		        }

		        if (!itemSelected) {
		            showWarningMessage(NO_ITEM_SELECTED);
		            return false;
		        }

		        return true;
		    }

		    private boolean handleRefundSelection() {
		        ButtonModel selectedButton = riarView.getRefundButtonGroup().getSelection();
		        if (selectedButton == null) {
		            showWarningMessage(NO_REFUND_METHOD);
		            removeItemsNotActuallyReturned();
		            return false;
		        }
		        return true;
		    }

		    private void processRefund(String refundMethod) {
		        IRefund refundMode;
		        String senderEmail = SmartWarehouseInfoPoint.getEmail();
		        String receiverEmail = returnService.getEmailOfReturnableOrder();

		        if (refundMethod.equals(ReturnItemsAndReasonsView.getBankTransferRadioText())) {
		            BankTransfer bankTransfer = new BankTransfer(returnService.getMoneyToBeReturned(), senderEmail, receiverEmail);
		            refundMode = RefundFactory.getBankTransferAdapter(bankTransfer);
		        } else {
		            VoucherRefund voucherRefund = new VoucherRefund(returnService.getMoneyToBeReturned(), senderEmail, receiverEmail);
		            refundMode = RefundFactory.getVoucherAdapter(voucherRefund);
		        }

		        try {
		            returnService.issueRefund(refundMode);
		            returnService.setMoneyAlreadyReturned(returnService.getMoneyAlreadyReturned() + refundMode.getValue());
		            returnService.updateWarehouseQty();
		            returnService.AddReturnToDB(refundMode);
		            riarView.showSuccessDialog(PAYMENT_CONFIRMED);
		        } catch (PaymentException e) {
		            showErrorMessage(e.getMessage());
		            removeItemsNotActuallyReturned();
		        }
		    }
		};
		riarView.getNextButton().addActionListener(NextButtonLister);
	}
    private void showErrorMessage(String message) {
        riarView.setVisible(true);
        riarView.showErrorMessagge(message);
    }
    private void showWarningMessage(String message) {
        riarView.setVisible(true);
        riarView.showWarningMessagge(message);
    }
	private void removeItemsNotActuallyReturned() {
		returnService.removeAllFromReturn();
		returnService.setReturnedItems(ReturnServiceDAOFacade.getIstance().readItemAndReason(returnService.getReturnableOrder()));
	}

	private void infoPointButtonHandlerMethod() {
		/*
		 * Listener for helpButton
		 */
		riarView.addInfoPointButtonListener(returnService.toString());
	}
}
