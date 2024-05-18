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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnFACADE;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnItemsAndReasonsView;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnView;

public class ReturnController {
	private ReturnFACADE returnFacade;
	private ReturnView returnView;
	private ReturnItemsAndReasonsView riarView;
	private static final String NESSUNA_MOTIVAZIONE = "Scegli una motivazione";

	public ReturnController(ReturnFACADE returnFacade,ReturnView returnView) {
		this.returnFacade=returnFacade;
		this.returnView=returnView;
		initComponents();
	}
	
	/*
	 * Controller for ReturnView: it manages the choice of order to return.
	 */

	private void initComponents() {
		// TODO Auto-generated method stub
		
		/*
		 * Listener for confirmButton: choose the order
		 */
		ActionListener confirmButtonLister=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manageAction();
			}
			
			private void manageAction() {
				
				AbstractButton button = null;
			    Enumeration<AbstractButton> en=returnView.getOrderButtonGroup().getElements();
			    boolean flag=false;
			    while (en.hasMoreElements()) {
		            button = en.nextElement();
		            if (button.isSelected()) {
		            	flag=true;
		                returnView.setVisible(false);
		                Reasons.initializeReasons();
		                Map<String, String> reasons = Reasons.getReasons();
						riarView= new ReturnItemsAndReasonsView(Integer.parseInt(button.getActionCommand()),reasons); 
		            }
		        }
			    if (!flag) {
			        JOptionPane.showMessageDialog(returnView, "Selezionare un ordine per continuare", "Avviso", JOptionPane.WARNING_MESSAGE);
			        return;
			    }
			    
			    addItems();
			}

		};
		returnView.getConfirmButton().addActionListener(confirmButtonLister);
	}
	
	
	/*
	 * Controller for ReturnItemsAndReasonsView: it manages the choice of items to return, the reasons and the refund method
	 */
	private void addItems() {
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
				ArrayList<JCheckBox> checkBoxList = riarView.getCheckBoxList();
				ArrayList<JComboBox<String>> reasonsDropdownList = riarView.getReasonsDropdownList();
				boolean itemSelected = false;

				for (int i = 0; i < checkBoxList.size(); i++) {
					JCheckBox checkBox = checkBoxList.get(i);
					JComboBox<String> comboBox = reasonsDropdownList.get(i);

					if (checkBox.isSelected()) {
						itemSelected=true;
						String sku = checkBox.getActionCommand();
						String reason = comboBox.getSelectedItem().toString();
						if(reason.equals("Altro")) {
							 reason = riarView.getCustomReasonAreaList().get(i).getText();
						}
						if (reason.isEmpty()) {
		                    // Se la motivazione personalizzata non è stata inserita, mostra un avviso
							riarView.setVisible(true);
		                    riarView.showAlert("Scrivere un motivo personalizzato laddove richiesto");
		                    returnFacade.removeAllFromReturn();
		                    return;
		                }
						if(reason.equals(NESSUNA_MOTIVAZIONE)) {
							riarView.setVisible(true);
					        riarView.showAlert("Selezionare una motivazione valida per ogni prodotto che si intende restituire");
					        returnFacade.removeAllFromReturn();
					        return; 
						}
						returnFacade.addItemToReturn(sku, reason); //returnFacade.addItemToReturn(InventoryDAOFacade.getInstance().findInventoryItemBySku(sku), reason);
					}
				}
				if (!itemSelected) {
			        // Se nessun prodotto è stato selezionato, mostra un avviso
					riarView.setVisible(true);
			        riarView.showAlert("Seleziona almeno un prodotto da restituire.");
			        return; 
			    }
				
				AbstractButton button = null;
				boolean refundSelected=false;
				StringBuilder message = new StringBuilder(returnFacade.toString()).append("Metodo di rimborso selezionato:\n");
			    Enumeration<AbstractButton> en=riarView.getRefundButtonGroup().getElements();
			    while (en.hasMoreElements()) {
		            button = en.nextElement();
		            if (button.isSelected()) {
		            	refundSelected=true;
		                message.append(button.getText());
		                break;
		            }
		        }
			    if(!refundSelected) {
			    	riarView.setVisible(true);
			        riarView.showAlert("Indicare la modalità di rimborso");
			        returnFacade.removeAllFromReturn();
			        return; 
			    }
			    // Recap popup to confirm or cancel the return.
			    int popup = riarView.showConfirmPopUp(message.toString());
			   
			    if(popup==JOptionPane.OK_OPTION) {
			        if (button.getText().equals(riarView.getBankTransferRadioText())) {
			            BankTransfer br = new BankTransfer(returnFacade.getMoneyToBeReturned(),"EMAIL MAGAZZINO DA DEFINIRE","EMAIL CLIENTE DA PASSARE COME PARAMETRO "); //chi istanzia bonifico?
			            try {
							returnFacade.setRefundMode(RefundFactory.getBankTransferAdapter(br));
						} catch (PaymentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        } else {
			            VoucherRefund vr = new VoucherRefund(returnFacade.getMoneyToBeReturned());
			            try {
							returnFacade.setRefundMode(RefundFactory.getVoucherAdapter(vr));
						} catch (PaymentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    }
			    else {
			        returnFacade.removeAllFromReturn();
			        riarView.setVisible(true);
			}
			    System.out.println(returnFacade); //Qui deve apparire su GUI con ok e continua a navigare sullo shop
				
			}
		};
			riarView.getNextButton().addActionListener(NextButtonLister);
			
			
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
					returnView.setVisible(true);
				}

			};
			riarView.getBackButton().addActionListener(backButtonLister);
	}
}