//
package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.io.*;
import java.util.*;

import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;

public class Reasons {
	private static final String NO_REASONS_ENTERED_EXCEPTION_MESSAGE ="Motivazione mancante";
	private static final String NO_PERSONALIZED_REASONS_ENTERED_EXCEPTION_MESSAGE ="Motivo personalizzato mancante";
	private static final String NO_REASON ="MOTIVO1";
	private static final String REASONS_NOT_INITIALIZED_EXCEPTION_MESSAGE="Reasons not initialized. Call initializeReasons() first.";
	private static final String REASONS_FILE_PATH = "properties\\MotivazioniReso";
	private static Map<String, String> reasons; //la motivazione è una coppia: nome_del_motivo+motivo
	private Reasons() {
	}
	public static  void initializeReasons() {
		reasons = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(REASONS_FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("=", 2); 
				if (parts.length == 2) {
					reasons.put(parts[0], parts[1]); // La prima parte è la chiave, la seconda è il valore
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to initialize reasons from file: " + REASONS_FILE_PATH, e);
		}
	}
	public static String findReason(String input) throws MissingReasonException {
		if (reasons == null) {
			throw new IllegalStateException(REASONS_NOT_INITIALIZED_EXCEPTION_MESSAGE);
		}
		if (reasons.containsValue(input)) {
			if(input.equals(reasons.get(NO_REASON))) {
				throw new MissingReasonException(NO_REASONS_ENTERED_EXCEPTION_MESSAGE);
			}
			return input;
		}
		if(input.isEmpty()||input.isBlank()) {
			throw new MissingReasonException(NO_PERSONALIZED_REASONS_ENTERED_EXCEPTION_MESSAGE);
		}
		return input;

	}
	public static Map<String, String> getReasons() {
		if (reasons == null) {
			throw new IllegalStateException(REASONS_NOT_INITIALIZED_EXCEPTION_MESSAGE);
		}
		return reasons;
	}

}



