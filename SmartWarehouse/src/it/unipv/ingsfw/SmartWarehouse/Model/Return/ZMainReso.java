package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.ArrayList;
import java.util.HashMap;

// Mettere i vari contorrni alla label ecc.
// Fare GUI pagamenti 
//Fare colori GUI
// dividere costruttore GUI dalle init
// Mttere privati i metodi non usabili all'esterno
import it.unipv.ingsfw.SmartWarehouse.Controller.ReturnController;
import it.unipv.ingsfw.SmartWarehouse.Model.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.Ordine.Ordine;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.*;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Order;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Item;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnView;
// 0) // mettere a posto interfaccia item.
       // in returnView tutto ok, provare se funziona. ovviamente implementare i metodi commentati in RgisterFacade e RegisterDAO
       // e valutare se al posto del singleton manager con IDAO usare le facade--> fare un FacadeDAO Manager.
       // e in ReturnItemsAndReasonsView (fatto solo da vedere se funziona). Valuta metodo alternativo che ho scritto lì.
// 1) // Gestire passaggio sku additemtobereturned(passagli item). 
// 2)!!Gestire bene nel controllore la creazione di voucher e bonifico. considerare coordinate come parametri del metodo setRufundMode
     //Una coordinata può essere returnableOrder.getEmail, l'altra getWarehouseIban() che legge da file ad esempio.
// 3) creare eccezioni.
// 4) gestire ReturnFACADE come viene istanziato.
// 5) usare versione compatta if-else.


public class ZMainReso {
	public static void main(String[] args) {
		/*
		Item item1=new Item("p1","prodotto1",1.0);
		Item item2=new Item("p2","prodotto2",1.0);
		Item item3=new Item("p3","prodotto3",9.0);
		ArrayList<Item> itemslist = new ArrayList<>();
		itemslist.add(item1);
		itemslist.add(item1);
		itemslist.add(item2);
		itemslist.add(item3);
		Ordine ordine1=new Ordine(1,itemslist);
		Ordine ordine2=new Ordine(2,itemslist);
		IReturnable[] listaOrdini=new IReturnable[2];
		listaOrdini[0]=ordine1;
		listaOrdini[1]=ordine2;
		*/
		//-----------------------------------------------------------
		
		Client client1= new Client("John", "Doe", "john.doe@example.com", "123 Main St, Anytown, USA", "password123");
		
		Position pos1=new Position("linea1","pod1","bin1");
		ItemDetails itemDetails1=new ItemDetails(1, 1);
		Item item1=new Item("Smartphone",itemDetails1);
		InventoryItem inventoryItem1=new InventoryItem(item1,"SKU001",599.99,2,1000,pos1);
		
		HashMap<InventoryItem, Integer> skuqty=new HashMap<InventoryItem, Integer>();
		skuqty.put(inventoryItem1, 5);
		Order order1_client1=new Order(skuqty,client1.getEmail());
		
		System.out.println("ciao");
		ReturnView rv= new ReturnView(client1);
		ReturnFACADE rf=new ReturnFACADE(order1_client1);
		ReturnController rc=new ReturnController(rf,rv);
		
		//-------------------------------------
		 /*ReturnService reso1=ResoManager.getIstance().getReturnService(ordine1);
		 ReturnService reso1=rf.getRs();
		 reso1.addItemToReturn("p1","Articolo difettoso");
		 reso1.addItemToReturn("p1", "Altro");
		 reso1.addItemToReturn("p2", "Ho cambiato idea");
	
		VoucherRefund vr=new VoucherRefund(reso1.getMoneyToBeReturned());
		reso1.setRefundMode(RefundFactory.getVoucherAdapter(vr));
		BankTransfer br=new BankTransfer(reso1.getMoneyToBeReturned());
		reso1.setRefundMode(RefundFactory.getBankTransferAdapter(br));
		System.out.println(reso1);*/
		
		/*---------------------------------------------
		ReturnService reso2=ResoManager.getIstance().getReturnService(ordine1);
		//Reso reso2=new Reso(ordine1);
		reso2.addItemToReturn("p3", "Il prodotto non è come descritto");
		reso2.addItemToReturn("p1","Articolo difettoso");
		reso2.addItemToReturn("p2", "Articolo difettoso");
		
		VoucherRefund vr2=new VoucherRefund(reso2.getMoneyToBeReturned()); //Valutare un meccanismo di reflection
		reso2.setRefundMode(RefundFactory.getVoucherAdapter(vr2));
		//BankTransfer br2=new BankTransfer(reso2.getMoneyToBeReturned());
		//reso2.setRefundMode(RefundFactory.getBankTransferAdapter(br2));
		System.out.println(reso2);*/
		
	}
	
	
}
