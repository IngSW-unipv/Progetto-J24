package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public enum Category {
	ELECTRONICS("Elettronica"),
    GROCERIES("Alimentari"),
    CLOTHING("Abbigliamento"),
    TOYS("Giochi");
	
	private final String label;

	//only private constructor
    Category(String label) {
		this.label=label;
	}

	public String getLabel() {
		return label;
	}

	public static Category fromString(String text) {
		for(Category cat : Category.values()) {
			if(cat.getLabel().equals(text)) {
				return cat;
			}
		}
		return null;
	} 

}
 