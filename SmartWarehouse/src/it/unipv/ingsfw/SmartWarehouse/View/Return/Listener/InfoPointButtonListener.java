package it.unipv.ingsfw.SmartWarehouse.View.Return.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoPointButtonListener implements ActionListener{
	private String returnServiceRecap;
	public InfoPointButtonListener(String returnServiceRecap) {
		super();
		this.returnServiceRecap=returnServiceRecap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String inputFilePath = "properties/SmartWarehouseCustomerInfo";
		StringBuilder fileContent = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append(System.lineSeparator());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        fileContent.append(returnServiceRecap);
        JTextArea textArea = new JTextArea(fileContent.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "InfoPoint", JOptionPane.INFORMATION_MESSAGE);
	}

}


