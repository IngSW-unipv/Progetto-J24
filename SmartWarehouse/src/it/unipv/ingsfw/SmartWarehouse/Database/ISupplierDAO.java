package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;

public interface ISupplierDAO {
	public List<Supplier> selectAllSupplier();
	public Supplier getSupplierByIds(String ids);
	public boolean insertSupplier(Supplier f);
	public boolean deleteSupplier(Supplier s);
}
