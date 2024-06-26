package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyManager;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyOrder;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.CategoryStrategy;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy.ThresholdStrategy;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.SuppliersDialog;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.SuppliesDialog;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.SupplyOrderDialog;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.SupplyPanel;

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
		replenish();
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
						s.add();
					} catch(Exception e) {
						supplyPanel.showErrorMessage(e);
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
						s.add();						
					} catch(Exception e) {
						supplyPanel.showErrorMessage(e);
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

						if(supplyPanel.getSuppliersDialog().askDelete(ids)) {
							try {
								supplyManager.findSupplier(ids).delete();
								updateSuppliers();
							} catch(Exception ex) {
								supplyPanel.getSuppliersDialog().showErrorMessage(ex);
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

						if(supplyPanel.getSuppliesDialog().askDelete(idSupply)) {
							try {
								supplyManager.findSupply(idSupply).delete();
								updateSupplies();
							} catch(Exception ex) {
								supplyPanel.getSuppliesDialog().showErrorMessage(ex);
							}
						}
					}
				}
			}
		}; 
		supplyPanel.getSuppliesDialog().getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
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
					updateSupplyOrders(supplyManager.getSupplyOrders());
					orderTheOrders();
				}
			}
		};
		supplyPanel.getAllSupplyOrders().addActionListener(orderListener);
	}

	public void orderTheOrders() {
		ActionListener orderTheOrdersListener=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();		
			}

			private void manageAction() {
				List<SupplyOrder> orders=supplyManager.getSupplyOrders();
				supplyManager.getOrderedSupplyOrders(orders);
				updateSupplyOrders(orders);
			}
		};
		supplyPanel.getSupplyOrderDialog().getOrder().addActionListener(orderTheOrdersListener);
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

	private void updateSupplyOrders(List<SupplyOrder> orders) {
		supplyPanel.getSupplyOrderDialog().getTableModel().setRowCount(0);
		for (SupplyOrder s : orders) {
			supplyPanel.getSupplyOrderDialog().addSupplyOrderToTable(s.getN_order(), s.getSupply().getID_Supply(), s.getQty(), s.getPrice(), s.getDate());
		}	
	}

	public void replenish() {
		ActionListener replenishListener=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();		
			}

			private void manageAction() {
				try {
					String strategy = (String)supplyPanel.getStrategy().getSelectedItem();

					switch (strategy) {
					case "Threshold Strategy": {
						supplyManager.setReplenishmentStrategy(new ThresholdStrategy());
						break;
					}
					case "Category Strategy": {
						Category cat=supplyPanel.askCategory(); 
						if (cat==null) {
							return;
						}
						supplyManager.setReplenishmentStrategy(new CategoryStrategy(cat));
						break;
					}
					}
					supplyManager.replenishAll(InventoryDAOFacade.getInstance().viewInventory());
					supplyPanel.showConfirmMessage("successfull replenishment");
				} catch(Exception e) {
					supplyPanel.showErrorMessage(e);
				}

			}
		};
		supplyPanel.getReplenish().addActionListener(replenishListener);
	}

}
