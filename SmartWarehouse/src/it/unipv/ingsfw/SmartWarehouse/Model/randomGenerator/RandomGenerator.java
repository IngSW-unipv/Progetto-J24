package it.unipv.ingsfw.SmartWarehouse.Model.randomGenerator;

import java.util.Random;

public class RandomGenerator implements IRandomGenerator{

	public String generateRandomString(int lenght) {
		StringBuilder sb = new StringBuilder();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";

		String alphaNumeric = alphabet + numbers;
		Random random = new Random();
		for(int i=0; i<lenght; i++) { 
			// generate random index number
		    int index = random.nextInt(alphaNumeric.length());
			//Returns the char value at the specified index.
			char randomChar = alphaNumeric.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}
}
