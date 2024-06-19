package it.unipv.ingsfw.SmartWarehouse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SmartWarehouseInfoPoint {
	private static final String DEADLINE_FOR_MAKING_RETURN="DEADLINE_FOR_MAKING_RETURN"; 
	private static final String SMARTWAREHOUSE_EMAIL="SMARTWAREHOUSE_EMAIL";
	private static final String MINSTANDARDLEVEL = "MINSTANDARD_LEVEL";
	private static final String SKUSIZE = "SKUSIZE";
	private static int deadline;
	private static String email;
	private static int min_std_level;
	private static int MIN_STD_LEVEL_DEFAULT = 30;
	private static int skuSize;
	private static int skuSizeDefault = 5;
	
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
	
	public static int getMin_Std_Level() {
		FileInputStream f = null;
		try {
			Properties p = new Properties(System.getProperties());
			f = new FileInputStream("properties/SmartWarehouseInfoPoint");
			p.load(f);
			min_std_level = Integer.parseInt(p.getProperty(MINSTANDARDLEVEL));
		} catch (Exception e) {
			e.printStackTrace();
			return MIN_STD_LEVEL_DEFAULT;
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
		return min_std_level;
	}
	
	public static int getSkuSize() {
		FileInputStream f = null;
		try {
			Properties p = new Properties(System.getProperties());
			f = new FileInputStream("properties/SmartWarehouseInfoPoint");
			p.load(f);
			skuSize = Integer.parseInt(p.getProperty(SKUSIZE));
		} catch (Exception e) {
			e.printStackTrace();
			return skuSizeDefault;
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
		return skuSize;
	}
}
