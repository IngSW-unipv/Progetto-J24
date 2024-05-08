package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SuppliersDialog extends JDialog {
	private DefaultTableModel tableModel;
	private JTable table;
	private JDialog deleteSupplierDialog;
	private JButton delete;
	private String ids;
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public SuppliersDialog() {
		setSize(800,500);
		setResizable(true);
		tableModel=new DefaultTableModel();
		tableModel.addColumn("ids");
		tableModel.addColumn("fullname");
		tableModel.addColumn("email");
		tableModel.addColumn("address");
		table=new JTable(tableModel);
		JScrollPane scrollpane=new JScrollPane(table);
		add(scrollpane);
	}
	
	public void addSupplierToTable(String ids, String fullname, String email, String address) { 
        tableModel.addRow(new Object[]{ids,fullname, email, address});
    }

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JDialog getDeleteSupplierDialog() {
		return deleteSupplierDialog;
	}

	public void setDeleteSupplierDialog(String ids) {
		setIds(ids);
		deleteSupplierDialog=new JDialog();
		deleteSupplierDialog.setTitle(ids);
		delete=new JButton("Delete");
		deleteSupplierDialog.add(delete);
		deleteSupplierDialog.setSize(100,500);
	}

	public JButton getDelete() {
		return delete;
	}

	public void setDelete(JButton delete) {
		this.delete = delete;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
