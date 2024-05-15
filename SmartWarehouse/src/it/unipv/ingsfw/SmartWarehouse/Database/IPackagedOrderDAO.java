package DAO;

import java.util.ArrayList;

import order.Order;
import order.Orderline;

public interface IPackagedOrderDAO {
	 public void convertToOrderLine(Order order);
}
