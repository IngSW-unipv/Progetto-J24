package it.unipv.ingsfw.SmartWarehouse.Model.Ordine;
import java.util.ArrayList;

import it.unipv.ingsfw.SmartWarehouse.Model.Return.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Item.InterfacciaItem;
import it.unipv.ingsfw.SmartWarehouse.Model.Item.Item;

public class Ordine implements IReturnable{
    private int id;
    private ArrayList<Item> items;

    public Ordine(int id, ArrayList<Item> itemslist) {
        this.id = id;
        this.items = itemslist;
    }

    @Override
    public int getId() {
        return id;
    }

    public ArrayList<Item> getItems(){
    	return items;
    }

	@Override
	public InterfacciaItem getItem(String sku) {
		// TODO Auto-generated method stub
		for(InterfacciaItem i:items)
		{
			if(i.getSku().equals(sku)) {
				return i;
			}
		}
		return null;
	}
	public String findNomeBySku(String sku) {
		for(InterfacciaItem i:items)
		{
			if(i.getSku().equals(sku)) {
				return i.getNome();
			}
		}
		return "ERRRRRR NESSUN NOME";
	}

	@Override
	public int getQta(String sku) {
		// TODO Auto-generated method stub
		int count=0;
		for(Item i:items) {
			if(i.getSku().equals(sku))
				count++;
		}
		return count;
	}
	public String toString() {
		return ""+id;
		
	}
}