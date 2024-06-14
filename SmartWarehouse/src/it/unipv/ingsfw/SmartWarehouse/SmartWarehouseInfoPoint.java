package it.unipv.ingsfw.SmartWarehouse;

import java.io.FileInputStream;
import java.util.Properties;

public class SmartWarehouseInfoPoint {
	private static final String DEADLINE_FOR_MAKING_RETURN="DEADLINE_FOR_MAKING_RETURN"; 
	private static final String SMARTWAREHOUSE_EMAIL="SMARTWAREHOUSE_EMAIL";
	private static int deadline;
	private static String email;
	
	private SmartWarehouseInfoPoint() {
	}
	public static int getDeadlineForMakingReturn() {
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/SmartWarehouseInfoPoint"));
			deadline=Integer.parseInt(p.getProperty(DEADLINE_FOR_MAKING_RETURN));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deadline;
	}
	
	public static String getEmail() {
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/SmartWarehouseInfoPoint"));
			email=p.getProperty(SMARTWAREHOUSE_EMAIL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}
}
