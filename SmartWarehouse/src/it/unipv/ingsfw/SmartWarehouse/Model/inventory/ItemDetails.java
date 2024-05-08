package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public class ItemDetails {
	private int fragility, dimension;

	public ItemDetails(int fragility, int dimension) {
		super();
		this.fragility = fragility;
		this.dimension = dimension;
	}
	
	public int getFragility() {
		return fragility;
	}

	public void setFragility(int fragility) {
		this.fragility = fragility;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	//to try
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Fragility: "+fragility+", Dimension: "+dimension;
	}
	
}
