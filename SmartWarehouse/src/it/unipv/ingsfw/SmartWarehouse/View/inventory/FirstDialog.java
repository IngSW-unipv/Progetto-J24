package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FirstDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private String sku;
	private JButton plus;
	private JButton minus;
	private JButton delete;
	private JTextField editQtyField;
	private JTable table;
	private DefaultTableModel tableModel;
	private SecondDialog secondDialog;
	
	public FirstDialog(String sku) {
		this.setSku(sku);
		this.setTitle(sku);
		setSize(700, 400);
		setResizable(true);
		setLocationRelativeTo(null);
		 
		JPanel editPanel= new JPanel();
		plus=new JButton("+");
		editPanel.add(plus);
		minus=new JButton("-");
		editPanel.add(minus);
		JLabel qtyLabel = new JLabel("Qty:");
		editPanel.add(qtyLabel);
		setEditQtyField(new JTextField(5));
		editPanel.add(editQtyField);
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
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.setLayout(new BorderLayout());
		this.add(editPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	 
	public void addSupplierToTable(String ids, String fullname, String email, String address, double price, int maxqty) { 
        tableModel.addRow(new Object[]{ids,fullname, email, address, price, maxqty});
    }
	
	public void showErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showConfirmMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean askConfirm(String mess) {
		boolean b = false;
		if (JOptionPane.showConfirmDialog(this, mess, "Confirm your choice", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			b = true;
		}
		return b;
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

	public JTextField getEditQtyField() {
		return editQtyField;
	}

	public void setEditQtyField(JTextField editQtyField) {
		this.editQtyField = editQtyField;
	}
}
