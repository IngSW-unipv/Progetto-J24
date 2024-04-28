package view.inventory;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SuppliesDialog extends JDialog{
	private DefaultTableModel tableModel;
	private JTable table;

	public SuppliesDialog() {
		setSize(800,500);
		setResizable(true);
		tableModel=new DefaultTableModel();
		tableModel.addColumn("idSupply");
		tableModel.addColumn("sku");
		tableModel.addColumn("idSupplier");
		tableModel.addColumn("price");
		tableModel.addColumn("maxqty");
		table=new JTable(tableModel);
		JScrollPane scrollpane=new JScrollPane(table);
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
