package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OrderDetailsPanel extends JPanel{
	private JLabel selectedOrderLabel;
	private JLabel ItemAndReasonsLabel;
	private JScrollPane scrollPane;

	public OrderDetailsPanel() {
		super();
		this.repaint();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		selectedOrderLabel = new JLabel();
		ItemAndReasonsLabel = new JLabel("Scegli cosa restituire");

		this.add(selectedOrderLabel);
		this.add(ItemAndReasonsLabel);

		scrollPane = new JScrollPane(this);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	public JLabel getSelectedOrderLabel() {
		return selectedOrderLabel;
	}
	public JLabel getItemAndReasonsLabel() {
		return ItemAndReasonsLabel;
	}
}
