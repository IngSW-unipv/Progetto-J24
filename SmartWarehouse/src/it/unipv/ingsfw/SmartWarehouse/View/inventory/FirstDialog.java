package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FirstDialog extends JDialog {
	private String sku;
	private JButton plus;
	private JButton minus;
	private JButton delete;
	private JTable table;
	private DefaultTableModel tableModel;
	private SecondDialog secondDialog;
	
	public FirstDialog(String sku) {
		this.setSku(sku);
		this.setTitle(sku);
		setSize(700, 500);
		setResizable(true);
		 
		JPanel editPanel= new JPanel();
		plus=new JButton("+");
		editPanel.add(plus);
		minus=new JButton("-");
		editPanel.add(minus);
        delete=new JButton("Delete Item");
        editPanel.add(delete);
		
		tableModel=new DefaultTableModel();
		tableModel.addColumn("ids");
		tableModel.addColumn("nome cognome");
		tableModel.addColumn("address");
		tableModel.addColumn("email");
		tableModel.addColumn("price");
		tableModel.addColumn("maxQty");
		
		table =new JTable(tableModel);
		JScrollPane scrollPane=new JScrollPane(table);
		
		this.setLayout(new BorderLayout());
		this.add(editPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	 
	public void addSupplierToTable(String ids, String fullname, String email, String address, double price, int maxqty) { 
        tableModel.addRow(new Object[]{ids,fullname, email, address, price, maxqty});
    }

	public JButton getDelete() {
		return delete;
	}

	public void setDelete(JButton delete) {
		this.delete = delete;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public JButton getPlus() {
		return plus;
	}

	public void setPlus(JButton plus) {
		this.plus = plus;
	}

	public JButton getMinus() {
		return minus;
	}

	public void setMinus(JButton minus) {
		this.minus = minus;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public SecondDialog getSecondDialog() {
		return secondDialog;
	}

	public void setSecondDialog(SecondDialog secondDialog) {
		this.secondDialog = secondDialog;
	}
}
