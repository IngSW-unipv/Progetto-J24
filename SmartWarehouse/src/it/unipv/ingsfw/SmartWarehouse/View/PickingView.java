package it.unipv.ingsfw.SmartWarehouse.View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PickingView {
    private JFrame frame;
    private JButton calculatePack;
    private JButton packed;
    private JButton itemButton;
    private JTextArea text;
    private JTextArea packageSummaryTextArea;
    private JPanel ListId;
    private JPanel orderItemsPanel;
    private JTextArea selected;
    private int selectedOrderId = -1;
    private JTextArea packageInfoArea;

    public PickingView() {
        frame = new JFrame("Order");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        itemButton = new JButton("Item");
    

        calculatePack = new JButton("Calculate Package");
        packed = new JButton("Packed");

        text = new JTextArea();
        text.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(text);

        packageSummaryTextArea = new JTextArea();
        packageSummaryTextArea.setEditable(false);
        JScrollPane summaryScrollPane = new JScrollPane(packageSummaryTextArea);
        packageInfoArea = new JTextArea();

        selected = new JTextArea();
        selected.setEditable(false);
        JScrollPane selectedScrollPane = new JScrollPane(selected);

        ListId = new JPanel();
        ListId.setLayout(new BoxLayout(ListId, BoxLayout.Y_AXIS));
        ListId.add(new JLabel("Order IDs"));
   

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(ListId, BorderLayout.CENTER);

        JPanel packPanel = new JPanel();
        packPanel.setLayout(new BoxLayout(packPanel, BoxLayout.Y_AXIS));
       
        packPanel.add(itemButton);
        packPanel.add(calculatePack);
        packPanel.add(packed);
        

        JPanel orderDetailsPanel = new JPanel(new BorderLayout());
        orderDetailsPanel.add(new JLabel("Selected Order Details:"), BorderLayout.NORTH);
        orderDetailsPanel.add(textScrollPane, BorderLayout.CENTER);

        JPanel packageSummaryPanel = new JPanel(new BorderLayout());
        packageSummaryPanel.add(new JLabel("Package Summary:"), BorderLayout.NORTH);
        packageSummaryPanel.add(summaryScrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(new JLabel("Messages:"), BorderLayout.NORTH);
        messagePanel.add(selectedScrollPane, BorderLayout.CENTER);

        Dimension panelSize = new Dimension(250, 200);
        orderDetailsPanel.setPreferredSize(panelSize);
        packageSummaryPanel.setPreferredSize(panelSize);
        messagePanel.setPreferredSize(panelSize);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(orderDetailsPanel);
        centerPanel.add(packageSummaryPanel);
        centerPanel.add(messagePanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(packPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    public JButton displayOrderIds(int id) {
        JButton b = new JButton();
        b.setText("" + id);
        b.setActionCommand("" + id);
        ListId.add(b);
        ListId.revalidate();
        ListId.repaint();
        return b;
    }

    public void displayOrderItems(String orderItems) {
        text.setText(orderItems);
    }

    public void displayOrderItemsPanel(String orderItems) {
        orderItemsPanel.removeAll();
        JTextArea orderItemsTextArea = new JTextArea(orderItems);
        orderItemsTextArea.setEditable(false);
        orderItemsPanel.add(orderItemsTextArea, BorderLayout.WEST);
        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }

    public int getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(int selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JButton getCalculatePack() {
        return calculatePack;
    }

    public void setCalculatePack(JButton calculatePack) {
        this.calculatePack = calculatePack;
    }

    public JButton getPacked() {
        return packed;
    }

    public void setPacked(JButton packed) {
        this.packed = packed;
    }

    public JButton getItemButton() {
        return itemButton;
    }

    public void setItemButton(JButton itemButton) {
        this.itemButton = itemButton;
    }

    public JTextArea getText() {
        return text;
    }

    public void setText(JTextArea text) {
        this.text = text;
    }

    public JTextArea getPackageSummaryTextArea() {
        return packageSummaryTextArea;
    }

    public void setPackageSummaryTextArea(JTextArea packageSummaryTextArea) {
        this.packageSummaryTextArea = packageSummaryTextArea;
    }

    public JTextArea getSelected() {
        return selected;
    }

    public void setSelected(JTextArea selected) {
        this.selected = selected;
    }

    public JPanel getListId() {
        return ListId;
    }

    public void setListId(JPanel listId) {
        ListId = listId;
    }

    public JPanel getOrderItemsPanel() {
        return orderItemsPanel;
    }

    public void setOrderItemsPanel(JPanel orderItemsPanel) {
        this.orderItemsPanel = orderItemsPanel;
    }

    public void displayPackageInfo(String packageInfo) {
        packageSummaryTextArea.setText(packageInfo);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showConfirmationMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPackageInfo(Object packageInfo) {
        JOptionPane.showMessageDialog(frame, packageInfo, "Package Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public String[] getPackageDetails() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JCheckBox fragilityCheckBox = new JCheckBox("Fragile");
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(new JLabel("Fragile:"));
        panel.add(fragilityCheckBox);
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Package Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{quantityField.getText(), Boolean.toString(fragilityCheckBox.isSelected())};
        } else {
            return null;
        }
    }

    public HashMap<String, Integer> getItemDetails() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel itemLabel = new JLabel("Item:");
        JTextField itemField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        panel.add(itemLabel);
        panel.add(itemField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Item Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String itemText = itemField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (itemText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "there are some empty filed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            try {
                int quantity = Integer.parseInt(quantityText);
                HashMap<String, Integer> itemDetails = new HashMap<>();
                itemDetails.put(itemText, quantity);
                return itemDetails;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }

   
   
    public void clearInsertedItemsDisplay() {
        text.setText("");
    }
    
    public String getInsertedPackageInfo() {
        String packageInfo = selected.getText().trim(); 
        String[] lines = packageInfo.split("\n");
        StringBuilder insertedPackages = new StringBuilder();
        for (String line : lines) {
            if (line.contains("Selected package type") || line.contains("Quantity")) {
                insertedPackages.append(line.trim()).append("\n");
            }
        }
        return insertedPackages.toString().trim();
    }


    public void setPackageInfo(String packageInfo) {
        selected.setText(packageInfo); 
    }
    
    public void clearPackageSummary() {
        packageSummaryTextArea.setText(""); 
    }

    public void clearMessages() {
        selected.setText(""); 
    }
    

}
