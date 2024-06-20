package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SuppliesDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;

	public SuppliesDialog() {
		setSize(700,400);
		setResizable(true);
		setLocationRelativeTo(null);
		tableModel=new DefaultTableModel();
		tableModel.addColumn("idSupply");
		tableModel.addColumn("sku");
		tableModel.addColumn("idSupplier");
		tableModel.addColumn("price");
		tableModel.addColumn("maxqty");
		table=new JTable(tableModel);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollpane);
	}
	
	public void addSupplyToTable(String idSupply, String sku, String idSupplier, double price, int maxqty) { 
        tableModel.addRow(new Object[]{idSupply,sku,idSupplier, price, maxqty});
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
}
