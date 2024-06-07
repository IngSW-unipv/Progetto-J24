package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class CheckBoxListener implements ItemListener{

	private ReturnItemsAndReasonsView riarView;

	public CheckBoxListener(ReturnItemsAndReasonsView returnItemsAndReasonsView) {
		super();
		this.riarView=returnItemsAndReasonsView;
	}

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

}
