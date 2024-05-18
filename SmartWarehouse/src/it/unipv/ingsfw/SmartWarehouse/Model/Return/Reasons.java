package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.io.*;
import java.util.*;

public class Reasons {
	    private static Map<String, String> reasons; //la motivazione è una coppia nome_del_motivo+motivo
	    public static  void initializeReasons() {
	        reasons = new HashMap<>();
	        try {
	            BufferedReader reader = new BufferedReader(new FileReader("properties\\MotivazioniReso"));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("=", 2); // Divide la linea in due parti usando "=" come delimitatore
	                if (parts.length == 2) {
	                    reasons.put(parts[0], parts[1]); // La prima parte è la chiave, la seconda è il valore
	                }
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    public static String findReason(String input) {
	        /*if (input.equals("Altro")) {
	        	System.out.println("Inserisci la motivazione:");
	        	return InputDaTastiera.getInput();

	        }*/
	
	        if (reasons.containsValue(input)) {
	            return input;
	        }
	        return input;  //Gestire eccezione.
	    }
		public static Map<String, String> getReasons() {
			return reasons;
		}

	}



