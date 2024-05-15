package DAO;

import java.sql.*;
import java.util.*;
import inventory.*;
import order.*;
import packagefactory.*;
import packagestrategy.*;

public class PackagedOrderDAO implements IPackagedOrderDAO{
	private String schema;
	private Connection conn;
	
	public PackagedOrderDAO() {
		super();
		this.schema = "smartwarehouse";
	}

	
	 public void convertToOrderLine(Order order) {
	        try {
	            // Prepara la query SQL per l'inserimento dei dati
	            String query = "INSERT INTO orderline (sku, date) VALUES (?, ?)";
	            PreparedStatement statement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	            // Ottieni i dettagli dell'ordine
	            HashMap<InventoryItem, Integer> skuQty = order.getSkuqty();
	            int date = order.getDate();

	            // Itera attraverso gli elementi dell'ordine e inserisci ciascun elemento come riga nella tabella orderline
	            for (Map.Entry<InventoryItem, Integer> entry : skuQty.entrySet()) {
	                InventoryItem item = entry.getKey();

	                // Imposta i valori per la query SQL
	                statement.setString(1, item.getSku()); // Assumi che ci sia un metodo getSku() in InventoryItem
	                statement.setInt(2, date);

	                // Esegui l'operazione di inserimento
	                int affectedRows = statement.executeUpdate();

	                if (affectedRows == 0) {
	                    throw new SQLException("Creating orderline failed, no rows affected.");
	                }

	                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        int orderId = generatedKeys.getInt(1);
	                       // return new Orderline(orderId, item.getSku(), entry.getValue(), order.getEmail(), date);
	                    } else {
	                        throw new SQLException("Creating orderline failed, no ID obtained.");
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	       
	    }
	
	}
    

