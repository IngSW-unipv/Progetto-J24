package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.ArrayList;

import it.unipv.ingsfw.SmartWarehouse.Model.Item.InterfacciaItem;
import it.unipv.ingsfw.SmartWarehouse.Model.Item.Item;

public interface IReturnable{

	int getId(); // Ritorna un identificatore univoco dell'oggetto rendibile
	ArrayList<Item> getItems(); //set
	InterfacciaItem getItem(String sku);
	int getQta(String sku);
    //boolean isRendibile(); // Ritorna true se l'oggetto è rendibile, false altrimenti
	String findNomeBySku(String sku);
}
