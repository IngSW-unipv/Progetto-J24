package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import it.unipv.ingsfw.SmartWarehouse.Model.Return.Reasons;

public class ReturnItemsAndReasonsView extends JFrame{
	 private JPanel mainPanel;
	 private JPanel orderDetailsPanel;
	 private JPanel itemAndReasonsPanel;
	 private JPanel confirmPanel;
	 private JPanel backPanel;
	 private JPanel refundPanel;
	 private ArrayList<JCheckBox> checkBoxList=new ArrayList<JCheckBox>();
	 private ArrayList< JComboBox<String> > reasonsDropdownList=new ArrayList< JComboBox<String> >();
	 private ArrayList<JTextArea> customReasonAreaList=new ArrayList<JTextArea>();
	 private ArrayList<JLabel> customReasonLabelList=new ArrayList<JLabel>();
	 private ButtonGroup refundButtonGroup;
	 public static final String VOUCHER_RADIO_TEXT = "VOUCHER (consigliato): ricevi il rimborso all'istante su un voucher senza scadenza spendibile in tutto lo shop.";
	 public static final String BANK_TRANSFER_RADIO_TEXT = "Bonifico Bancario: ricevi un bonifico sulla carta che hai utilizzato per effettuare l'acquisto. Tempi stimati 14 giorni lavorativi.";

	private JButton backButton;
	 private JButton nextButton;
	 private Map<String, String> reasons;
	 


	 public ReturnItemsAndReasonsView(String order) {
	    setTitle("Items and Reasons");
        setSize(600,400);
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Imposta le dimensioni della finestra in base alle dimensioni dello schermo
        int windowWidth = (int) (screenWidth*0.9); // ad esempio, 80% della larghezza dello schermo
        int windowHeight = (int) (screenHeight*0.9); // ad esempio, 80% dell'altezza dello schermo
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra
        setResizable(true);
        
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);
        
        orderDetailsPanel=new JPanel();
        orderDetailsPanel.setLayout(new BoxLayout(orderDetailsPanel, BoxLayout.Y_AXIS));
        JLabel selectedOrderLabel = new JLabel("Ordine selezionato: " +order);
        JLabel ItemAndReasonsLabel = new JLabel("Seleziona gli item che vuoi restituire");

        orderDetailsPanel.add(selectedOrderLabel);

        orderDetailsPanel.add(ItemAndReasonsLabel);
   
        
        //ArrayList<String> items = SelectItemsWhereOrder(order); //vorrei avere un arrayList di sku.
        ArrayList<String> items= new ArrayList();
        items.add("p1");
        items.add("p1");
        items.add("p2");
        items.add("p1");
        items.add("p1");
        items.add("p2");
        items.add("p1");
        items.add("p1");
        items.add("p3");
        
        Reasons.initializeReasons();
        reasons=Reasons.getReasons();
        for (String i : items) {
        	
        	itemAndReasonsPanel = new JPanel();
        	itemAndReasonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        	
        	JCheckBox checkBox=new JCheckBox(i);
            checkBoxList.add(checkBox);
            itemAndReasonsPanel.add(checkBox);
            
            JComboBox<String> reasonsDropdown = new JComboBox<String>(reasons.values().toArray(new String[0]));
            reasonsDropdown.addItem("Scegli una motivazione");
            reasonsDropdown.setSelectedItem("Scegli una motivazione");
            reasonsDropdown.setEnabled(false);
            reasonsDropdownList.add(reasonsDropdown);
            itemAndReasonsPanel.add(reasonsDropdown);
            
            JTextArea customReasonArea=new JTextArea(4, 50);
            JLabel customReasonLabel=new JLabel("Descrivi il motivo della restituzione");
            LineBorder border = new LineBorder(Color.BLACK); // Puoi scegliere il colore del bordo
            customReasonArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Aggiungi spaziatura interna

            customReasonArea.setVisible(false);
            customReasonLabel.setVisible(false);
            customReasonAreaList.add(customReasonArea); 
            customReasonLabelList.add(customReasonLabel);
            itemAndReasonsPanel.add(customReasonLabel); 
            itemAndReasonsPanel.add(customReasonArea); 
            
            orderDetailsPanel.add(itemAndReasonsPanel);
        }

        mainPanel.add(orderDetailsPanel, BorderLayout.CENTER);
        
        confirmPanel = new JPanel();
        nextButton = new JButton("Next");
        confirmPanel.add(nextButton);
        mainPanel.add(confirmPanel, BorderLayout.SOUTH);
        
        backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout per posizionare il pulsante a sinistra
        backButton = new JButton("Back",UIManager.getIcon("FileView.directoryIcon"));
        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.NORTH);
       
        
        refundPanel = new JPanel();
        refundPanel.setLayout(new GridLayout(0, 1));
        //refundPanel.setLayout(new BoxLayout(refundPanel, BoxLayout.Y_AXIS));
        JLabel refundLabel = new JLabel("Scegli la modalità con cui preferisci essere rimborsato:");
        JRadioButton voucherRadioButton = new JRadioButton(VOUCHER_RADIO_TEXT);
        JRadioButton bankTransferRadioButton = new JRadioButton(BANK_TRANSFER_RADIO_TEXT);
        refundButtonGroup = new ButtonGroup();
        refundButtonGroup.add(voucherRadioButton);
        refundButtonGroup.add(bankTransferRadioButton);
        refundPanel.add(refundLabel);
        refundPanel.add(voucherRadioButton);
        refundPanel.add(bankTransferRadioButton);

        // Aggiungi bordi evidenziati o colori se necessario
        refundPanel.setBorder(BorderFactory.createTitledBorder("Modalità di Rimborso")); // Aggiunge un bordo titolato
        refundPanel.setBackground(Color.cyan); // Cambia colore di sfondo se necessario
        JPanel uselessPanel=new JPanel(new BorderLayout());
        mainPanel.add(uselessPanel,BorderLayout.EAST);
        uselessPanel.add(refundPanel,BorderLayout.SOUTH);

        //pack();
        setVisible(true);
        
   
	}
	 
	 public int showConfirmPopUp(String recap) {
		 return JOptionPane.showConfirmDialog(this, recap, "Conferma restituzione", JOptionPane.OK_CANCEL_OPTION);

	 }
	 public void showAlert(String message) {
		    JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.WARNING_MESSAGE);
		}
	
	public static String getVoucherRadioText() {
		return VOUCHER_RADIO_TEXT;
	}

	public static String getBankTransferRadioText() {
		return BANK_TRANSFER_RADIO_TEXT;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public ArrayList<JCheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public ArrayList<JComboBox<String>> getReasonsDropdownList() {
		return reasonsDropdownList;
	}

	public JButton getBackButton() {
		return backButton;
	}


	public ArrayList<JTextArea> getCustomReasonAreaList() {
		return customReasonAreaList;
	}

	public ArrayList<JLabel> getCustomReasonLabelList() {
		return customReasonLabelList;
	}

	public JPanel getItemAndReasonsPanel() {
		return itemAndReasonsPanel;
	}
	public ButtonGroup getRefundButtonGroup() {
		return refundButtonGroup;
	} 
}

