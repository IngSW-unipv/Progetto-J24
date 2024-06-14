package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class CheckBoxListener implements ItemListener{

	private ReturnItemsAndReasonsView riarView;

	public CheckBoxListener(ReturnItemsAndReasonsView returnItemsAndReasonsView) {
		super();
		this.riarView=returnItemsAndReasonsView;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		boolean result=true;
		if(e.getStateChange() == ItemEvent.SELECTED) {
			// Abilito la JComboBox corrispondente quando la casella di controllo viene selezionata
			int index = riarView.getCheckBoxList().indexOf(e.getSource());
			riarView.getReasonsDropdownList().get(index).setEnabled(true);
			result=true;
			for (JCheckBox checkBox : riarView.getCheckBoxList()) {
	            if(!checkBox.isSelected()) {
	            	result=false; //at least one is not selected
	            }
	        }
			if(result=true) { //all checkBox are selected
				riarView.getSelectAllButton().setEnabled(false);
				riarView.getDeselectAllButton().setEnabled(true);
			}
		} else if(e.getStateChange() == ItemEvent.DESELECTED) {
			// Disabilito la JComboBox corrispondente quando la casella di controllo viene deselezionata
			int index = riarView.getCheckBoxList().indexOf(e.getSource());
			riarView.getReasonsDropdownList().get(index).setSelectedItem("Choose a reason");
			riarView.getReasonsDropdownList().get(index).setEnabled(false);
			riarView.getCustomReasonAreaList().get(index).setVisible(false);
			riarView.getCustomReasonLabelList().get(index).setVisible(false);
			
			result=true;
			for (JCheckBox checkBox : riarView.getCheckBoxList()) {
	            if(checkBox.isSelected()) {
	            	result=false; //at least one is selected
	            }
	        }
			if(result=true) { //no one is selected
				riarView.getSelectAllButton().setEnabled(true);
				riarView.getDeselectAllButton().setEnabled(false);
			}
		}

	}

}
