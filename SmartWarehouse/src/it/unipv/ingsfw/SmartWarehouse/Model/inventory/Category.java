package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public enum Category {
	ELECTRONICS("Electronics"),
    GROCERIES("Groceries"),
    CLOTHING("Clothing"),
    TOYS("Toys");
	
	private final String label;
	
	/**
	 * Private constructor
	 * @param label
	 */
    Category(String label) {
		this.label=label;
	}

	public String getLabel() {
		return label;
	}
	
	/**
	 * Static method to obtain the Category corresponding to a given label
	 */
	public static Category fromString(String label) {
		for(Category cat : Category.values()) {
			if(cat.getLabel().equals(label)) {
				return cat;
			}
		}
		return null;
	}

}
 