package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.SingletonUser;
import model.inventory.InventoryItem;
import model.inventory.InventoryManager;
import model.operator.SupplyOperator;
import model.supply.Supplier;
import model.supply.Supply;
import model.supply.SupplyManager;
import model.supply.SupplyOrder;
import view.inventory.FirstDialog;
import view.inventory.InventoryView;
import view.inventory.SuppliersDialog;
import view.inventory.SuppliesDialog;
import view.inventory.SupplyOrderDialog;
import view.inventory.SupplyPanel;

public class SupplyController {
	private SupplyPanel supplyPanel;
	private SupplyManager supplyManager; 
	
	public SupplyController(SupplyManager supplyManager, SupplyPanel supplyPanel) {
		this.supplyPanel = supplyPanel;
		this.supplyManager = supplyManager;
		newSupplier();
		newSupply();
		allSuppliers();
		allSupplies();
		allOrders();
	}
	
	public void newSupplier() {
		ActionListener newSupplierListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();	
			}
			
			private void manageAction() {
				
					Object[] input= supplyPanel.showSupplierInsert();
					if(input!=null) {
						try {
							Supplier s=new Supplier((String)input[0], (String)input[1], (String)input[2], (String)input[3]);
							s.add(SingletonUser.getInstance().getOp());
						} catch(Exception e) {
							JOptionPane.showMessageDialog(supplyPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}	
					}
			}
		}; 
		supplyPanel.getNewSupplier().addActionListener(newSupplierListener);
	}
    
	public void newSupply() {
		ActionListener newSupplyListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();	
			}
			
			private void manageAction() {			
				Object[] input= supplyPanel.showSupplyInsert();
				if(input!=null) {
					try { 
						Supply s=new Supply((String)input[0], (String)input[1], (Double)input[2], (Integer)input[3]);
						s.add(SingletonUser.getInstance().getOp());
						
					} catch(Exception e) {
						JOptionPane.showMessageDialog(supplyPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}	
				}
			} 
		}; 
		supplyPanel.getNewSupply().addActionListener(newSupplyListener);
	}
	
    public void allSuppliers() {
    	ActionListener allSuppliersListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();	
			}
			
			private void manageAction() {
				if(supplyPanel.getSuppliersDialog()==null || !supplyPanel.getSuppliersDialog().isVisible()) {
					supplyPanel.setSuppliersDialog(new SuppliersDialog());
					supplyPanel.getSuppliersDialog().setVisible(true);
					init1();
				}
			}
		}; 
		supplyPanel.getAllSuppliers().addActionListener(allSuppliersListener);
    }
    
    private void init1() {
    	updateSuppliers();
    	rowSupplierSelection();   	
    }
    
    private void rowSupplierSelection() {
		ListSelectionListener listSelectionListener=new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { 
				int selectedRowIndex=supplyPanel.getSuppliersDialog().getTable().getSelectedRow();
					if(selectedRowIndex!=-1) {
						String ids= (String) supplyPanel.getSuppliersDialog().getTable().getValueAt(selectedRowIndex, 0);
						int choice=JOptionPane.showConfirmDialog(supplyPanel.getSuppliersDialog(), "Do you want to remove the seleted supplier?",
								ids+" remove option", JOptionPane.YES_NO_OPTION);
						if(choice==JOptionPane.YES_OPTION) {
							try {
								supplyManager.findSupplier(ids).delete(SingletonUser.getInstance().getOp());
								updateSuppliers();
							} catch(Exception ex) {
								JOptionPane.showMessageDialog(supplyPanel.getSuppliersDialog(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		}; 
		supplyPanel.getSuppliersDialog().getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
	}
    
    public void allSupplies() {
    	ActionListener allSuppliesListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();	
			}
			
			private void manageAction() {
				if(supplyPanel.getSuppliesDialog()==null || !supplyPanel.getSuppliesDialog().isVisible()) {
					supplyPanel.setSuppliesDialog(new SuppliesDialog());
					supplyPanel.getSuppliesDialog().setVisible(true);
					init2();
				}
			}
		}; 
		supplyPanel.getAllSupplies().addActionListener(allSuppliesListener);
    }
    
    private void init2() {
    	updateSupplies();
    	rowSupplySelection();   	
    }
    
    private void rowSupplySelection() {
		ListSelectionListener listSelectionListener=new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { 
				int selectedRowIndex=supplyPanel.getSuppliesDialog().getTable().getSelectedRow();
					if(selectedRowIndex!=-1) {
						String idSupply= (String) supplyPanel.getSuppliesDialog().getTable().getValueAt(selectedRowIndex, 0);
						int choice=JOptionPane.showConfirmDialog(supplyPanel.getSuppliesDialog(), "Do you want to remove the seleted supply?",
								idSupply+" remove option", JOptionPane.YES_NO_OPTION);
						if(choice==JOptionPane.YES_OPTION) {
							try {
								supplyManager.findSupply(idSupply).delete(SingletonUser.getInstance().getOp());
								updateSupplies();
							} catch(Exception ex) {
								JOptionPane.showMessageDialog(supplyPanel.getSuppliesDialog(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		}; 
		supplyPanel.getSuppliesDialog().getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
    }
    
    private void updateSuppliers() {
		List<Supplier> suppliers=supplyManager.getSuppliers(); 
		supplyPanel.getSuppliersDialog().getTableModel().setRowCount(0);
        for (Supplier s : suppliers) {
        	supplyPanel.getSuppliersDialog().addSupplierToTable(s.getIDS(), s.getFullName(), s.getEmail(), s.getAddress());
        }	
    }
	
    private void updateSupplies() {
		List<Supply> supplies=supplyManager.getSupplies();	 
		supplyPanel.getSuppliesDialog().getTableModel().setRowCount(0);
        for (Supply s : supplies) {
        	supplyPanel.getSuppliesDialog().addSupplyToTable(s.getID_Supply(), s.getInventoryItem().getSku(), s.getSupplier().getIDS(), s.getPrice(), s.getMaxqty());
        }	
    }
    
    private void updateSupplyOrders() {
    	List<SupplyOrder> orders=supplyManager.getSupplyOrders();
    	supplyPanel.getSupplyOrderDialog().getTableModel().setRowCount(0);
        for (SupplyOrder s : orders) {
        	supplyPanel.getSupplyOrderDialog().addSupplyOrderToTable(s.getN_order(), s.getSupply().getID_Supply(), s.getQty(), s.getPrice(), s.getDate());
        }	
    }
    
    public void allOrders() {
    	ActionListener orderListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();		
			}
			
			private void manageAction() {
				if(supplyPanel.getSupplyOrderDialog()==null || !supplyPanel.getSupplyOrderDialog().isVisible()) {
					supplyPanel.setSupplyOrderDialog(new SupplyOrderDialog());
					supplyPanel.getSupplyOrderDialog().setVisible(true);
					updateSupplyOrders();
				}
			}
		};
		supplyPanel.getAllSupplyOrders().addActionListener(orderListener);
    }
    
    /*
	if (supplyPanel.getSuppliersDialog().getDeleteSupplierDialog() == null || !supplyPanel.getSuppliersDialog().getDeleteSupplierDialog().isVisible()) {
		String ids= (String) supplyPanel.getSuppliersDialog().getTable().getValueAt(selectedRowIndex, 0);
		supplyPanel.getSuppliersDialog().setDeleteSupplierDialog(ids);
		supplyPanel.getSuppliersDialog().getDeleteSupplierDialog().setVisible(true);
		deleteSupplier();
	}
	*/
    /*
    public void deleteSupplier() {
    	ActionListener deleteSupplierListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();	
			}
			
			private void manageAction() {
				String ids=supplyPanel.getSuppliersDialog().getIds();
				try{
					supplyManager.findSupplier(ids).delete(SingletonUser.getInstance().getOp());
					updateSuppliers();
				} catch(Exception e) {
					JOptionPane.showMessageDialog(supplyPanel.getSuppliersDialog().getDeleteSupplierDialog(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}; 
		supplyPanel.getSuppliersDialog().getDelete().addActionListener(deleteSupplierListener);
    }
    */
}
