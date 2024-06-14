package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason.ReturnItemsAndReasonsView;

public class InfoPointButtonListener implements ActionListener{
	private ReturnItemsAndReasonsView riarView;
	private String returnServiceRecap;
	public InfoPointButtonListener(ReturnItemsAndReasonsView returnItemsAndReasonsView,String returnServiceRecap) {
		super();
		this.riarView=returnItemsAndReasonsView;
		this.returnServiceRecap=returnServiceRecap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String inputFilePath = "properties/SmartWarehouseCustomerInfo";
		String outputFilePath = "outputFile.txt";
		StringBuilder fileContent = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append(System.lineSeparator());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			writer.write(fileContent.toString());
			writer.newLine();
			writer.write(returnServiceRecap);
			writer.newLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			File outputFile = new File(outputFilePath);
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(outputFile);
			} else {
				System.err.println("Desktop is not supported. Cannot open the file.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}


