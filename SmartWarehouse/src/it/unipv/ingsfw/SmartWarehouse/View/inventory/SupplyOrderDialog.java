package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SupplyOrderDialog extends JDialog {
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton order; 
	
	public SupplyOrderDialog() {
		setSize(800,500);
		setResizable(true);
		tableModel=new DefaultTableModel();
		tableModel.addColumn("n_order");
		tableModel.addColumn("idSupply");
		tableModel.addColumn("qty");
		tableModel.addColumn("price");
		tableModel.addColumn("date");
		table=new JTable(tableModel);
		JScrollPane scrollpane=new JScrollPane(table);
		
		JPanel panel=new JPanel();
		order=new JButton("Newest Before");
		panel.add(order);
		
		this.setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
	}

	public void addSupplyOrderToTable(int n_order, String idSupply, int qty, double price, LocalDateTime date) {
		tableModel.addRow(new Object[] {n_order,idSupply,qty,price,date});
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getOrder() {
		return order;
	}

	
}
