package it.unipv.ingsfw.SmartWarehouse.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supplier.SupplierDoesNotExistException;
import it.unipv.ingsfw.SmartWarehouse.Exception.supply.SupplyAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;

public class AddSupplyTest {
	private Supply s; 
	
	//try to insert a supply already present, verify that the correct exception is thrown
	@Test
	public void testKO1() {
		s = new Supply("LKJZC", "S02", 14, 40);
		
		assertThrows(SupplyAlreadyExistsException.class, () -> {
	            s.add();
	    });
	}
	
	//try to insert a supply of an absent inventoryItem 
	@Test
	public void testKO2() {
		s = new Supply("NNJZC77", "S02", 23.1, 30);
		
		assertThrows(ItemNotFoundException.class, () -> {
            s.add();
		});
	}
	
	//try to insert a supply of an absent supplier
	@Test 
	public void testKO3() {
		s = new Supply("LKJZC", "8491", 23.1, 30);
		
		assertThrows(SupplierDoesNotExistException.class, () -> {
            s.add();
		});
	}
	
	//try to insert a valid supply apart from the price
	@Test 
	public void testKO4() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			s = new Supply("XB7OHW4JBX", "4562", -6.9, 30);
            s.add();
		});
		assertEquals("Price must be positive", thrown.getMessage());
		
	}
}
