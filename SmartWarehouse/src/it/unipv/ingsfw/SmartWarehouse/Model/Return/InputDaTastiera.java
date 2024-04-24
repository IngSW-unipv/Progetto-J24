package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.Scanner;

public class InputDaTastiera {
	
		private static Scanner scanner=new Scanner(System.in);
	    public static String getInput() {
	    	scanner=new Scanner(System.in);
	        String s = scanner.nextLine();
		    return s;
	    }
   

}
