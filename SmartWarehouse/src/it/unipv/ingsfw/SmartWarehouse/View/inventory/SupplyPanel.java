package it.unipv.ingsfw.SmartWarehouse.View.inventory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.CategoryStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.IReplenishmentStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.ThresholdStrategy;

public class SupplyPanel extends JPanel{
	 private JButton newSupplier;
	 private JButton newSupply;
	 private JButton allSuppliers;
	 private JButton allSupplies;
	 private JButton allSupplyOrders;
	 private JButton replenish;
	 private JComboBox<String> strategy;
	 private SuppliersDialog suppliersDialog;
	 private SuppliesDialog suppliesDialog;
	 private SupplyOrderDialog supplyOrderDialog;
	 
	 public SupplyPanel() {
		 
		 JPanel centerPanel=new JPanel();
		 newSupplier=new JButton("new Supplier");
		 newSupply=new JButton("new Supply");
		 centerPanel.add(newSupplier);
		 centerPanel.add(newSupply);
		 
		 JPanel upperPanel=new JPanel();
		 allSuppliers=new JButton("all Suppliers");
		 allSupplies=new JButton("all Supplies");
		 allSupplyOrders=new JButton("all Supply Orders");
		 upperPanel.add(allSuppliers);
		 upperPanel.add(allSupplies);  
		 upperPanel.add(allSupplyOrders);  
		 
		 JPanel thirdPanel=new JPanel();
		 replenish=new JButton("replenish");
		 strategy=new JComboBox<>();
		 strategy.addItem(new CategoryStrategy(null).getName());
		 strategy.addItem(new ThresholdStrategy().getName());

		 thirdPanel.add(strategy);
		 thirdPanel.add(replenish);
		 
		 this.setLayout(new BorderLayout());
		 this.add(upperPanel, BorderLayout.NORTH);
		 this.add(centerPanel, BorderLayout.CENTER);
		 this.add(thirdPanel, BorderLayout.SOUTH);
	}
	 
	public Object[] showSupplierInsert() {
		 JTextField idsField = new JTextField(30);
		 JTextField fullnameField = new JTextField(40);
	     JTextField emailField = new JTextField(30);
	     JTextField addressField = new JTextField(30);

	     JPanel panel = new JPanel(new GridLayout(0, 2)); 
	     panel.setPreferredSize(new Dimension(300, 400));
	     panel.add(new JLabel("Id:"));
	     panel.add(idsField);
	     panel.add(new JLabel("Fullname:"));
	     panel.add(fullnameField);
	     panel.add(new JLabel("Email:"));
	     panel.add(emailField);
	     panel.add(new JLabel("Address:"));
	     panel.add(addressField);
	        
	     int result = JOptionPane.showConfirmDialog(this, panel, "Insert new Supplier", JOptionPane.OK_CANCEL_OPTION);
	     if (result == JOptionPane.OK_OPTION) {
	    	 if(idsField.getText().isEmpty() || fullnameField.getText().isEmpty() || emailField.getText().isEmpty() || addressField.getText().isEmpty()) {
	    		 JOptionPane.showMessageDialog(this, "empty fields" , "Error", JOptionPane.WARNING_MESSAGE);
	    		 return null;
	    	 }
	    	 
	    	 String ids = idsField.getText();
	    	 String fullname = fullnameField.getText();
	    	 String email = emailField.getText();
	    	 String address = addressField.getText();

	         return new String[] { ids, fullname, email, address }; 
	     }
	     return null;
	 }
	
	public Object[] showSupplyInsert() {
		JTextField skuField = new JTextField(30);
		JTextField idsField = new JTextField(30);
		JTextField priceField = new JTextField(30);
	    JTextField maxqtyField = new JTextField(30);

	    JPanel panel = new JPanel(new GridLayout(0, 2)); //n°righe qualsiasi
	    panel.setPreferredSize(new Dimension(300, 400));
	    panel.add(new JLabel("Sku:"));
	    panel.add(skuField);
	    panel.add(new JLabel("Ids:"));
	    panel.add(idsField);
	    panel.add(new JLabel("Price:"));
	    panel.add(priceField);
	    panel.add(new JLabel("MaxQty:"));
	    panel.add(maxqtyField);
	        
	    int result = JOptionPane.showConfirmDialog(this, panel, "Insert new Supply", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {   
	    	if(skuField.getText().isEmpty() || idsField.getText().isEmpty() || priceField.getText().isEmpty() || maxqtyField.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(this, "empty fields" , "Error", JOptionPane.WARNING_MESSAGE);
	    		return null;
	    	} 
	    		
	    	 String sku = skuField.getText();
	    	 String ids = idsField.getText();
	    	 double price;
	    	 int maxqty;
	    	 try { 
	 	        price = Double.parseDouble(priceField.getText());
	 	        maxqty = Integer.parseInt(maxqtyField.getText());
	 	    } catch (NumberFormatException ex) {
	 	        JOptionPane.showMessageDialog(this, "Insert values valid for price and maximum quantity", "Errore", JOptionPane.ERROR_MESSAGE);
	 	        return null;
	 	    }
	        return new Object[] { sku, ids, price, maxqty }; 
	    } 
	    return null;
	 }
	
	public Category askCategory() {
		JPanel panel=new JPanel();
		JComboBox<Category> category = new JComboBox<>(Category.values());
		panel.add(category);
		int result=JOptionPane.showConfirmDialog(this, panel, "select category", JOptionPane.OK_CANCEL_OPTION);
		if(result==JOptionPane.OK_OPTION) {
			return (Category)category.getSelectedItem();
		}
		return null;
	}

	public JButton getNewSupplier() {
		return newSupplier;
	}

	public void setNewSupplier(JButton newSupplier) {
		this.newSupplier = newSupplier;
	}

	public JButton getNewSupply() {
		return newSupply;
	}

	public void setNewSupply(JButton newSupply) {
		this.newSupply = newSupply;
	}

	public JButton getAllSuppliers() {
		return allSuppliers;
	}

	public void setAllSuppliers(JButton allSuppliers) {
		this.allSuppliers = allSuppliers;
	}

	public JButton getAllSupplies() {
		return allSupplies;
	}

	public void setAllSupplies(JButton allSupplies) {
		this.allSupplies = allSupplies;
	}

	public SuppliersDialog getSuppliersDialog() {
		return suppliersDialog;
	}

	public void setSuppliersDialog(SuppliersDialog suppliersDialog) {
		this.suppliersDialog = suppliersDialog;
	}

	public SuppliesDialog getSuppliesDialog() {
		return suppliesDialog;
	}

	public void setSuppliesDialog(SuppliesDialog suppliesDialog) {
		this.suppliesDialog = suppliesDialog;
	}

	public JButton getAllSupplyOrders() {
		return allSupplyOrders;
	}

	public void setAllSupplyOrders(JButton allSupplyOrders) {
		this.allSupplyOrders = allSupplyOrders;
	}

	public SupplyOrderDialog getSupplyOrderDialog() {
		return supplyOrderDialog;
	}

	public void setSupplyOrderDialog(SupplyOrderDialog supplyOrderDialog) {
		this.supplyOrderDialog = supplyOrderDialog;
	}

	public JButton getReplenish() {
		return replenish;
	}

	public void setReplenish(JButton replenish) {
		this.replenish = replenish;
	}

	public JComboBox<String> getStrategy() {
		return strategy;
	}

	public void setStrategy(JComboBox<String> strategy) {
		this.strategy = strategy;
	}
	 
	
	 
}
