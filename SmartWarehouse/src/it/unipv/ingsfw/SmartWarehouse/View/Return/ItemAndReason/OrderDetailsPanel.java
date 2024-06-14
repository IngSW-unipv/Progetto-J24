package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.Font;

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
		selectedOrderLabel.setFont(new Font(selectedOrderLabel.getFont().getName(), Font.BOLD, selectedOrderLabel.getFont().getSize()));
		ItemAndReasonsLabel = new JLabel("Choose what to return");
		ItemAndReasonsLabel.setFont(new Font(ItemAndReasonsLabel.getFont().getName(), Font.BOLD, ItemAndReasonsLabel.getFont().getSize()));
	

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
	public void setTextOfSelectedOrderLabel(int selectedOrder) {
		selectedOrderLabel.setText("Selected Order: "+selectedOrder);
	}
}
