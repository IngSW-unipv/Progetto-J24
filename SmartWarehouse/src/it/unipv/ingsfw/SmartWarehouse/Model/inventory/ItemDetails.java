package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public class ItemDetails {
	private int dimension;
	private int fragility;
	private Category category; 

	public ItemDetails(int fragility, int dimension, Category category) {
		super();
		this.fragility = fragility;
		this.dimension = dimension;
		this.category = category; 
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

	public Category getCategory() {
		return category;
	}
 
	public void setCategory(Category category) {
		this.category = category;
	}

	
}
