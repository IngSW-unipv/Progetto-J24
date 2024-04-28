package view.inventory;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//estendela prima????
public class SecondDialog extends JDialog{
	private String ids;
	private JTextField qty;
	private JPanel panel;
	private JButton order;
	
	
	public SecondDialog(String sku, String ids) {
		setIds(ids);
		setSize(700, 500);
		setResizable(true);
		setTitle("Sku: "+sku+", IdS: "+ids);
		panel = new JPanel(new GridLayout(0, 2));
		JLabel qtyLabel=new JLabel("qty: ");
		panel.add(qtyLabel);
		qty=new JTextField(10);
		panel.add(qty);
		order=new JButton("Order");
		panel.add(order);
		add(panel);
		
	}

	public JTextField getQty() {
		return qty;
	}

	public void setQty(JTextField qty) {
		this.qty = qty;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public JButton getOrder() {
		return order;
	}

	public void setOrder(JButton order) {
		this.order = order;
	}
	
	
}
