package it.unipv.ingsfw.SmartWarehouse.View.Return.Orders;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectOrderPanel extends JPanel{
	
	private JScrollPane scrollPane;
	public SelectOrderPanel() {
		super();
		this.repaint();
		this.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
		JLabel selectOrderLabel = new JLabel("Select an order and confirm if you want to start a return procedure\n");
		selectOrderLabel.setFont(new Font(selectOrderLabel.getFont().getName(), Font.BOLD, selectOrderLabel.getFont().getSize()));
		this.add(selectOrderLabel);
	    scrollPane = new JScrollPane(this);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

}
