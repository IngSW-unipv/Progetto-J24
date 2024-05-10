package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.ArrayList;

// Mettere i vari contorrni alla label ecc.
// Fare GUI pagamenti 
//Fare colori GUI
// dividere costruttore GUI dalle init
// Mttere privati i metodi non usabili all'esterno
import it.unipv.ingsfw.SmartWarehouse.Controller.ReturnController;
import it.unipv.ingsfw.SmartWarehouse.Model.Item.Item;
import it.unipv.ingsfw.SmartWarehouse.Model.Ordine.Ordine;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.*;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.View.ReturnView;

// 1) // Gestire passaggio sku additemtobereturned(passagli item). 
// 2)!!Gestire bene nel controllore la creazione di voucher e bonifico. considerare coordinate come parametri del metodo setRufundMode
     //Una coordinata può essere returnableOrder.getEmail, l'altra getWarehouseIban() che legge da file ad esempio.
// 3) creare eccezioni.
// 4) gestire ReturnFACADE come viene istanziato.


public class ZMainReso {
	public static void main(String[] args) {
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
		//-----------------------------------------------------------
		ReturnView rv= new ReturnView();
		ReturnFACADE rf=new ReturnFACADE(ordine1);
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
