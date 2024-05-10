package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import it.unipv.ingsfw.SmartWarehouse.Controller.InventoryController;
import it.unipv.ingsfw.SmartWarehouse.Controller.SupplyController;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyManager;


public class InventoryView extends JFrame {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField skutf;
	private JLabel skul;
	private JButton posb;
	private JButton x;
	private JButton insert;
	private JButton orderItem;
	private FirstDialog firstDialog;
	private SupplyPanel supplyPanel;
	
	public InventoryView() {
		setTitle("Inventory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 800); 
		
		// Creazione del modello della tabella
	    tableModel = new DefaultTableModel();
	    tableModel.addColumn("Sku");
	    tableModel.addColumn("Description");
	    tableModel.addColumn("Price");
	    tableModel.addColumn("Qty");
	    tableModel.addColumn("StdLevel");
	    tableModel.addColumn("Line");
	    tableModel.addColumn("Pod");
	    tableModel.addColumn("Bin");
	    tableModel.addColumn("Fragility");
	    tableModel.addColumn("Dimension");
	    table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	     
	    JPanel filterPanel = new JPanel();
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        skul=new JLabel("Sku: ");
        filterPanel.add(skul);
        skutf=new JTextField(20);
        skutf.setBackground(Color.cyan);
        filterPanel.add(skutf);
        posb=new JButton("Filter by Position");
        filterPanel.add(posb);
        x=new JButton("X");
        filterPanel.add(x);
        
        insert=new JButton("New Item");
        buttonPanel.add(insert);
        orderItem=new JButton("order");
        buttonPanel.add(orderItem);
        
        supplyPanel= new SupplyPanel();
	    
	    getContentPane().add(scrollPane, BorderLayout.CENTER);
	    getContentPane().add(mainPanel, BorderLayout.NORTH);
	    getContentPane().add(supplyPanel, BorderLayout.SOUTH);
	    setVisible(true);    
	} 
	
	public Object[] showInsertDialog() {
        //String descr, double price, int std_level, pos
       
        JTextField descriptionField = new JTextField(50);
        JTextField priceField = new JTextField(10);
        JTextField stdlField = new JTextField(10);
        JTextField lineField = new JTextField(10);
        JTextField podField = new JTextField(10);
        JTextField binField = new JTextField(10);
        JTextField fragilityField = new JTextField(10);
        JTextField dimensionField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2)); //n°righe qualsiasi
        panel.setPreferredSize(new Dimension(300, 400));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Fragility:"));
        panel.add(fragilityField);
        panel.add(new JLabel("Dimension:"));
        panel.add(dimensionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Standard Level:"));
        panel.add(stdlField);
        panel.add(new JLabel("Line:"));
        panel.add(lineField);
        panel.add(new JLabel("Pod:"));
        panel.add(podField);
        panel.add(new JLabel("Bin:"));
        panel.add(binField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Insert new Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {   //gestire anche quando schiacci cancel
        	if (descriptionField.getText().isEmpty() ||
                     priceField.getText().isEmpty() ||
                     stdlField.getText().isEmpty() ||
                     lineField.getText().isEmpty() ||
                     podField.getText().isEmpty() ||
                     binField.getText().isEmpty() ||
                     fragilityField.getText().isEmpty() ||
                     dimensionField.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "empty fields" , "Error", JOptionPane.WARNING_MESSAGE);
                          return null;
            } 
        	  
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int std_level=Integer.parseInt(stdlField.getText());
            String line = lineField.getText();
            String pod = podField.getText();
            String bin = binField.getText();
            int fr=Integer.parseInt(fragilityField.getText());
            int dim=Integer.parseInt(dimensionField.getText());      

            return new Object[] { description, fr, dim, price, std_level, line, pod, bin }; 
       }
       return null;
    }
 
	//add an inventoryItem into the table
	public void addInventoryItem(String sku, String d, int fr, int dim, double price, int qty, int stdl, String line, String pod, String bin) { 
        tableModel.addRow(new Object[]{sku, d, price, qty, stdl, line, pod, bin, fr, dim});
    }
	 
	public void viewInventoryItemFound(InventoryItem i) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		if(i!=null) {
		tableModel.addRow(new Object[]{i.getSku(), i.getItem().getDescription(), i.getPrice(),i.getQty(), i.getStdLevel(), i.getPos().getLine(), i.getPos().getPod(), i.getPos().getBin(),
						i.getItem().getItemDetails().getFragility(), i.getItem().getItemDetails().getDimension()});
		}
	}
	
	public Object[] showFilterPosition() {
		JTextField lineField = new JTextField(10);
		JTextField podField = new JTextField(10);
		JTextField binField = new JTextField(10);
		JLabel lineLabel = new JLabel("Line: ");
		JLabel podLabel = new JLabel("Pod: ");
		JLabel binLabel = new JLabel("Bin: ");
		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.setPreferredSize(new Dimension(300, 400));
		panel.add(lineLabel);
		panel.add(lineField);
		panel.add(podLabel);
		panel.add(podField);
		panel.add(binLabel);
		panel.add(binField);
		int result = JOptionPane.showConfirmDialog(this, panel, "Filter Position", JOptionPane.OK_CANCEL_OPTION);
		if(result==JOptionPane.OK_OPTION) {
			String line = lineField.getText();
			String pod = podField.getText();
			String bin = binField.getText();
			return new String[] {line,pod,bin};
		}
		return null;
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

	public JTextField getSkutf() {
		return skutf;
	}

	public void setSkutf(JTextField skutf) {
		this.skutf = skutf;
	}

	public JLabel getSkul() {
		return skul;
	}

	public void setSkul(JLabel skul) {
		this.skul = skul;
	}

	public JButton getPosb() {
		return posb;
	}

	public void setPosb(JButton posb) {
		this.posb = posb;
	}

	public JButton getx() {
		return x;
	}

	public void setx(JButton x) {
		this.x = x;
	}

	public JButton getInsert() {
		return insert;
	}

	public void setInsert(JButton insert) {
		this.insert = insert;
	}

	public FirstDialog getFirstDialog() {
		return firstDialog;
	}

	public void setFirstDialog(FirstDialog firstDialog) {
		this.firstDialog = firstDialog;
	}
	
	
	public static void main(String[] args) {
		
		InventoryView iv=new InventoryView();
		System.out.println(iv.getSupplyPanel());
		InventoryManager w=new InventoryManager();
		InventoryController ic=new InventoryController(w, iv);
		SupplyManager sm=new SupplyManager();
		SupplyController sc=new SupplyController(sm, iv.getSupplyPanel());

	}

	public SupplyPanel getSupplyPanel() {
		return supplyPanel;
	}

	public void setSupplypPanel(SupplyPanel supplyPanel) {
		this.supplyPanel = supplyPanel;
	}

	public JButton getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(JButton orderItem) {
		this.orderItem = orderItem;
	}

	
}



