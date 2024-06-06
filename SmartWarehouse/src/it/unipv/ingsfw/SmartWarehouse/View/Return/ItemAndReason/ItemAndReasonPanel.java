package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class ItemAndReasonPanel extends JPanel{
	private GridBagConstraints gbc;
	public ItemAndReasonPanel(){
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	}
	
	public void constraintsForCheckBox(){
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 20); // Aggiungi margini attorno ai componenti
	}

}
