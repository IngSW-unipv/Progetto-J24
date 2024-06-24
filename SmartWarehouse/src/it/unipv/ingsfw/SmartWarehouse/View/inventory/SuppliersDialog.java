package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SuppliersDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;

	public SuppliersDialog() {
		setSize(700,400);
		setResizable(true);
		setLocationRelativeTo(null);
		tableModel=new DefaultTableModel();
		tableModel.addColumn("ids");
		tableModel.addColumn("fullname");
		tableModel.addColumn("email");
		tableModel.addColumn("address");
		table=new JTable(tableModel);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollpane);
	}
	
	public void addSupplierToTable(String ids, String fullname, String email, String address) { 
        tableModel.addRow(new Object[]{ids,fullname, email, address});
    }
	
	public void showErrorMessage(Exception e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * It returns true if the user pressed yes
	 * @param ids
	 * @return
	 */
	public boolean askDelete(String ids) {
		boolean b = false;
		if (JOptionPane.showConfirmDialog(this, "Do you want to remove the selected supplier?",
				ids+" remove option", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			b = true;
		}
		return b;
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
