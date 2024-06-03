package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.FirstDialog;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.InventoryView;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.SecondDialog;

public class InventoryController {
	private InventoryManager w;
	private InventoryView iv;
	 
	public InventoryController(InventoryManager w, InventoryView iv) {
        this.w = w;
        this.iv = iv;
        updateInventory(w.getInventory());       
        find();
        findPosition();
        underLevel();
        back();
        insert();
        order();
        rowSelection();      
    }
	
	private void updateInventory(List<InventoryItem> items) {
        iv.cleanTable();
        for (InventoryItem i : items) {
            iv.addInventoryItem(i.getSku(), i.getDescription(), i.getPrice(), i.getQty(), i.getStdLevel(), i.getPos().getLine(), i.getPos().getPod(), i.getPos().getBin(),
            		i.getDetails().getFragility(), i.getDetails().getDimension(), i.getDetails().getCategory().getLabel());
        }
    }
	
	private void find() {
		ActionListener findLItemListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				String s = iv.getSkutf().getText();
				if (!s.isEmpty()) { 
					iv.cleanTable();
					InventoryItem i = w.findInventoryItem(s);
					if (i!= null) {
						iv.addInventoryItem(i.getSku(), i.getDescription(), i.getPrice(),i.getQty(), i.getStdLevel(), i.getPos().getLine(), i.getPos().getPod(), i.getPos().getBin(),
								i.getDetails().getFragility(), i.getDetails().getDimension(), i.getDetails().getCategory().getLabel());
					}
	            }
			}
		};
		iv.getSkutf().addActionListener(findLItemListener);
	} 
	
	private void findPosition() {
		ActionListener actionListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manage();
			}
			
			private void manage() {
				Object[] input=iv.showFilterPosition();
				if(input!=null) {
					iv.cleanTable();
					InventoryItem i = w.findInventoryItemByPosition(new Position((String)input[0], (String)input[1], (String)input[2]));
					if (i!=null) {
						iv.addInventoryItem (i.getSku(), i.getDescription(), i.getPrice(),i.getQty(), i.getStdLevel(), i.getPos().getLine(), i.getPos().getPod(), i.getPos().getBin(),
								i.getDetails().getFragility(), i.getDetails().getDimension(), i.getDetails().getCategory().getLabel());
					}
				}
			} 
		};
		iv.getPosb().addActionListener(actionListener);
	}
	
	private void underLevel() {
		ActionListener underLevelListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction(); 
			}

			private void manageAction() {
				updateInventory(w.getInventoryItemsUnderStdLevel()); 
			}
		};
		iv.getUnderLevel().addActionListener(underLevelListener);
	}
	
	private void back() {
		ActionListener backListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction(); 
			}

			private void manageAction() {
				iv.getSkutf().setText("");
				updateInventory(w.getInventory()); 
			}
		};
		iv.getx().addActionListener(backListener);
	}
	
	private void insert() {
		ActionListener insertListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}
			
			private void manageAction() {
				Object[] input=iv.showInsertDialog();
				if (input!=null) {   
					try {   
						InventoryItem i = new InventoryItem((String)input[0], new ItemDetails((int)input[1], (int)input[2], (Category)input[3]), 
								(double)input[4], (int)input[5], new Position((String)input[6], (String)input[7], (String)input[8]));
						i.addToInventory();
						updateInventory(w.getInventory());
					} catch(Exception e) {
			            JOptionPane.showMessageDialog(iv, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}	
			} 
		};
		iv.getInsert().addActionListener(insertListener);
	}
	
	private void order() {
		ActionListener orderListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}
			
			private void manageAction() {
				List<InventoryItem> items=w.getInventory();
				w.orderInventoryItems(items);
				updateInventory(items);
			} 
		};
		iv.getOrderItem().addActionListener(orderListener);
	}
	
	private void rowSelection() {
		ListSelectionListener listSelectionListener=new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { 
				int selectedRowIndex=iv.getTable().getSelectedRow();
					if(selectedRowIndex!=-1) {
						if (iv.getFirstDialog() == null || !iv.getFirstDialog().isVisible()) {
							String sku= (String) iv.getTable().getValueAt(selectedRowIndex, 0);
							iv.setFirstDialog(new FirstDialog(sku));
							iv.getFirstDialog().setVisible(true);
							init();
						}			
					}
				}
			}
		}; 
		iv.getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
	}
	
	private void init() {
		updateSupplier();
		plus();
		minus();
		delete();
		rowOrderSelection();
	}
	
	private void updateSupplier() {
		String sku=iv.getFirstDialog().getSku();
		try {
			List<Object[]> suppliersInfo=w.findInventoryItem(sku).getSuppliersInfo();
			 
			iv.getFirstDialog().getTableModel().setRowCount(0);
	        for (Object[] o : suppliersInfo) {
	        	iv.getFirstDialog().addSupplierToTable(((Supplier)o[0]).getIDS(), ((Supplier)o[0]).getFullName(), ((Supplier)o[0]).getEmail(), ((Supplier)o[0]).getAddress(), (double)o[1], (int)o[2]);
	        }
		}catch(Exception e) {
			JOptionPane.showMessageDialog(iv.getFirstDialog(), e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
    }
	
	private void plus() {
		ActionListener plusListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction(); 
			}

			private void manageAction() {
				try {
					w.findInventoryItem(iv.getFirstDialog().getSku()).increaseQty();
					updateInventory(w.getInventory()); 
				}catch(Exception e) {
					JOptionPane.showMessageDialog(iv.getFirstDialog(), e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}		
			}
		};
		iv.getFirstDialog().getPlus().addActionListener(plusListener);
	}
	
	private void minus() {
		ActionListener minusListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction(); 
			}

			private void manageAction() {
				try {
					w.findInventoryItem(iv.getFirstDialog().getSku()).decreaseQty();
					updateInventory(w.getInventory()); 
				}catch(Exception e) {
					JOptionPane.showMessageDialog(iv.getFirstDialog(), e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}		
			}
		};
		iv.getFirstDialog().getMinus().addActionListener(minusListener);
	}
	
	private void delete() {
		ActionListener deleteListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction(); 
			}

			private void manageAction() {
				try {
					int choice = JOptionPane.showConfirmDialog(iv.getFirstDialog(), "are you sure you want to delete this item?", "confirm delete", JOptionPane.YES_NO_OPTION);
					if (choice==JOptionPane.YES_OPTION) {
						w.findInventoryItem(iv.getFirstDialog().getSku()).delete();
						updateInventory(w.getInventory()); 
					}	
				} catch(Exception e) {
					JOptionPane.showMessageDialog(iv.getFirstDialog(), e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}		
			}
		};
		iv.getFirstDialog().getDelete().addActionListener(deleteListener);
	}
	
	private void rowOrderSelection() {
		ListSelectionListener listSelectionListener=new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { 
				int selectedRowIndex=iv.getFirstDialog().getTable().getSelectedRow();
					if(selectedRowIndex!=-1) {
						if (iv.getFirstDialog().getSecondDialog() == null || !iv.getFirstDialog().getSecondDialog().isVisible()) {
							String ids= (String) iv.getFirstDialog().getTable().getValueAt(selectedRowIndex, 0);
							iv.getFirstDialog().setSecondDialog(new SecondDialog(iv.getFirstDialog().getSku(),ids));
							iv.getFirstDialog().getSecondDialog().setVisible(true);
							ok_buy(); 
						}			
					}
				}
			}
		}; 
		iv.getFirstDialog().getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
	}
	
	private void ok_buy() {
		ActionListener buyListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}
			
			private void manageAction() {
				try {
					int qty=Integer.parseInt(iv.getFirstDialog().getSecondDialog().getQty().getText());
					SupplyDAOFacade.getInstance().findSupplyBySkuAndIds(iv.getFirstDialog().getSku(), iv.getFirstDialog().getSecondDialog().getIds()).buy(qty);
					JOptionPane.showMessageDialog(iv.getFirstDialog().getSecondDialog(), "successfull order", "Order", JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(iv.getFirstDialog().getSecondDialog(), e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}			
			}
		};
		iv.getFirstDialog().getSecondDialog().getOrder().addActionListener(buyListener);
	}
} 





