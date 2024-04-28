package db;

import java.util.List;
import java.util.Set;

import model.supply.Supplier;
import model.supply.Supply;

public interface ISupplierDAO {
	public List<Supplier> selectAllSupplier();
	public Supplier getSupplierByIds(String ids);
	public boolean insertSupplier(Supplier f);
	public boolean deleteSupplier(Supplier s);
}
