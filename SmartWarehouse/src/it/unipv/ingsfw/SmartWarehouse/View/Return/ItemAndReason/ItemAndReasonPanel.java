package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class ItemAndReasonPanel extends JPanel{
	private GridBagConstraints gbc;
	public ItemAndReasonPanel(){
		super();
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
	public void constraintsForTextArea(){
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 0, 0, 0); // Aggiungi spazio tra il JComboBox e la JLabel
	}
	public void constraintsForComboBox(){
		gbc.gridx = 1;
	}

	public GridBagConstraints getGbc() {
		return gbc;
	}

}
