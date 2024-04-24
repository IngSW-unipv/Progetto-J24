package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.Database.ReturnServiceFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.IReturnable;

public class ReturnView extends JFrame{

	private JPanel mainPanel;
	private JPanel selectOrderPanel;
	private JPanel confirmPanel;
	private JPanel backPanel;
	private ButtonGroup orderButtonGroup;
    private JButton confirmButton;
    private JButton backButton;
    

    public ReturnView() {
        setTitle("Return Service");
        setSize(400, 200);
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
       
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        // Imposta le dimensioni della finestra in base alle dimensioni dello schermo
        int windowWidth = (int) (screenWidth*0.9); 
        int windowHeight = (int) (screenHeight*0.9); 
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null); // Centra la finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);
        
        selectOrderPanel = new JPanel();
        selectOrderPanel.setLayout(new GridLayout(0, 1)); // 0 righe per una colonna dinamica
        JLabel selectOrderLabel = new JLabel("Seleziona un ordine:");
        selectOrderPanel.add(selectOrderLabel);
  
        //Popola la ComboBox con gli ID degli ordini dal database
        //ArrayList<int> orders = SelectAllFromOrder //vorrei avere un arrayList di ordini o un arrayList di ID
        ArrayList<String> orders = new ArrayList<>();
        orders.add("Ordine 1");
        orders.add("Ordine 2");
        orders.add("<html> Ordine 3 <br> Ordine con una descrizione molto lunga e <br> dettagliata <br> Elenco Item comprati e data");
        orders.add("Ordine 4");
        orders.add("Ordine 5");
        orders.add("Ordine 6");
        orders.add("Ordine 7");
        orders.add("Ordine 8");
        orderButtonGroup = new ButtonGroup();
        for (String i : orders) {
            JRadioButton radioButton = new JRadioButton(i);
            orderButtonGroup.add(radioButton);
            selectOrderPanel.add(radioButton, BorderLayout.LINE_START);
        }
        mainPanel.add(selectOrderPanel, BorderLayout.CENTER);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Conferma Scelta");
        //confirmButton.setIcon(new ImageIcon("check_icon.png")); // Aggiungo un'icona al pulsante
        confirmPanel.add(confirmButton);
        mainPanel.add(confirmPanel, BorderLayout.SOUTH);
        
     
        backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
        backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.NORTH);
        //pack();
        setVisible(true);
	
    }


	public ButtonGroup getOrderButtonGroup() {
		return orderButtonGroup;
	}
	public JButton getConfirmButton() {
		return confirmButton;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	
	
	
	