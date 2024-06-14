package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class SelectAllButtonListener implements ActionListener{
	private ReturnItemsAndReasonsView riarView;
	public SelectAllButtonListener(ReturnItemsAndReasonsView returnItemsAndReasonsView) {
		super();
		this.riarView=returnItemsAndReasonsView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		riarView.getSelectAllButton().setEnabled(false);
		riarView.getDeselectAllButton().setEnabled(true);
		   for (JCheckBox checkBox : riarView.getCheckBoxList()) {
	            checkBox.setSelected(true);
	        }
	}

}
