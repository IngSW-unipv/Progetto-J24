package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.io.*;
import java.util.*;

import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;

public class Reasons {
	private static final String NESSUNA_MOTIVAZIONE_EXCEPTION_MESSAGE ="Selezionare una motivazione valida per ogni prodotto che si intende restituire";
	private static final String MOTIVO_PERSONALIZZATO_EXCEPTION_MESSAGE ="Scrivere un motivo personalizzato laddove richiesto";
	private static final String NESSUN_MOTIVO ="MOTIVO1";
	
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
	    public static String findReason(String input) throws MissingReasonException {
	        if (reasons.containsValue(input)) {
	        	if(input.equals(reasons.get(NESSUN_MOTIVO))) {
	        		 throw new MissingReasonException(NESSUNA_MOTIVAZIONE_EXCEPTION_MESSAGE);
	        	}
	        	return input;
	        }
	    	if(input.isEmpty()||input.isBlank()) {
        		throw new MissingReasonException(MOTIVO_PERSONALIZZATO_EXCEPTION_MESSAGE);
        	}
	        return input;
	        
	    }
		public static Map<String, String> getReasons() {
			return reasons;
		}

	}



