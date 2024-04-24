package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class ReturnViewTest extends JFrame {

	private JPanel mainPanel;
    private JPanel selectOrderPanel;
    private JPanel confirmPanel;
    private JPanel backPanel;
    private ButtonGroup orderButtonGroup;
    private JButton confirmButton;
    private JButton backButton;

    public ReturnViewTest() {
        setTitle("Return Service");
        setSize(400, 200);

        // Utilizzo del look and feel Nimbus
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);

        selectOrderPanel = new JPanel();
        selectOrderPanel.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
        JLabel selectOrderLabel = new JLabel("Seleziona un ordine:");
        selectOrderPanel.add(selectOrderLabel);

        // Simulazione degli ordini presi dal database
        ArrayList<String> orders = new ArrayList<>();
        orders.add("<html> Ordine 1 Ordine con una descrizione molto lunga e <br> dettagliata");
        orders.add("Ordine 2");
        orders.add("Ordine 3");
        orders.add("Ordine 4");
        orders.add("Ordine 5");
        orders.add("Ordine 6");
        orders.add("Ordine 7");
        orders.add("Ordine 8");

        orderButtonGroup = new ButtonGroup();
        for (String order : orders) {
            JRadioButton radioButton = new JRadioButton(order);
            orderButtonGroup.add(radioButton);
            selectOrderPanel.add(radioButton, BorderLayout.LINE_START);
        }
        mainPanel.add(selectOrderPanel, BorderLayout.CENTER);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Conferma Scelta");
        confirmButton.setIcon(new ImageIcon("check_icon.png")); // Aggiungo un'icona al pulsante
        confirmPanel.add(confirmButton);
        mainPanel.add(confirmPanel, BorderLayout.SOUTH);

        backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
        backButton = new JButton("Back");
        backButton.setIcon(new ImageIcon("back_icon.png")); // Aggiungo un'icona al pulsante
        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Adatta la dimensione della finestra ai suoi componenti
        setLocationRelativeTo(null); // Centra la finestra
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReturnViewTest::new);
    }
}
