package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public class ItemDetails {
	private int dimension;
	private int fragility;
	private Category category; 

	public ItemDetails(int fragility, int dimension, Category category) throws IllegalArgumentException {
		super();
		setFragility(fragility);
		setDimension(dimension);
		this.category = category; 
	}
	
	public int getFragility() {
		return fragility;
	}

	public void setFragility(int fragility) throws IllegalArgumentException {
		if (!check(fragility)) {
			throw new IllegalArgumentException("Fragility is not valid (it must be in the range [1/9])");
		}
		this.fragility = fragility;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) throws IllegalArgumentException {
		if (!check(dimension)) {
			throw new IllegalArgumentException("Dimension is not valid (it must be in the range [1/9])");
		}
		this.dimension = dimension;
	}

	public Category getCategory() {
		return category;
	}
 
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Used to check if dimension and fragility are valid (in range [0/9]).
	 * @param x
	 * @return
	 */
	private boolean check(int x) {
		return x>0 && x<10;
	}
	
}
