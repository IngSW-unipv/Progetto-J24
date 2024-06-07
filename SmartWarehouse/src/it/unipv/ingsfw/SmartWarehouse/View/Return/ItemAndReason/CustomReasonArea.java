package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class CustomReasonArea extends JTextArea{
	public CustomReasonArea() {
		super(4,10);
		this.setColumns(10);
		this.setMinimumSize(new Dimension(1, 1)); // Set a minimum size
		this.setLineWrap(true); // Abilita l'andare a capo automatico
		this.setWrapStyleWord(true); // Evita di spezzare le parole

		LineBorder border = new LineBorder(Color.BLACK); 
		this.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Aggiungi spaziatura interna
		this.setVisible(false);
	}

}
