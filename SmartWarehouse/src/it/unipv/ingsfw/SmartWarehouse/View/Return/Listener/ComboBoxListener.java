package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class ComboBoxListener implements ActionListener{

	private ReturnItemsAndReasonsView riarView;

	public ComboBoxListener(ReturnItemsAndReasonsView returnItemsAndReasonsView) {
		this.riarView=returnItemsAndReasonsView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<JComboBox<String>> reasonsDropdownList = riarView.getReasonsDropdownList();

		JComboBox<String> combo = (JComboBox<String>) e.getSource();
		String selectedReason = (String) combo.getSelectedItem();
		int index = reasonsDropdownList.indexOf(combo);
		riarView.getCustomReasonLabelList().get(index).setVisible(selectedReason.equals("Altro"));
		riarView.getCustomReasonAreaList().get(index).setVisible(selectedReason.equals("Altro"));

	}

}
