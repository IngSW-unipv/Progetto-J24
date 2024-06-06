package it.unipv.ingsfw.SmartWarehouse.View.Return.Orders;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectOrderPanel extends JPanel{
	
	private JScrollPane scrollPane;
	public SelectOrderPanel() {
		this.repaint();
		this.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
		JLabel selectOrderLabel = new JLabel("Scegli un ordine e conferma se vuoi cominciare una procedura di reso\n");
		this.add(selectOrderLabel);
	    scrollPane = new JScrollPane(this);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

}
