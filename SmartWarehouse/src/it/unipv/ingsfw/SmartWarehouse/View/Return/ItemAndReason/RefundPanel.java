package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RefundPanel extends JPanel{
	public static final String VOUCHER_RADIO_TEXT = "VOUCHER (consigliato): ricevi il rimborso all'istante su un voucher senza scadenza spendibile in tutto lo shop.";
	public static final String BANK_TRANSFER_RADIO_TEXT = "Bonifico Bancario: ricevi un bonifico sulla carta che hai utilizzato per effettuare l'acquisto. Tempi stimati 14 giorni lavorativi.";
	private ButtonGroup refundButtonGroup;

	public RefundPanel() {
		super();
		this.repaint();
		this.setLayout(new GridLayout(0, 1));
		//refundPanel.setLayout(new BoxLayout(refundPanel, BoxLayout.Y_AXIS));
		JLabel refundLabel = new JLabel("Scegli la modalità con cui preferisci essere rimborsato:");
		JRadioButton voucherRadioButton = new JRadioButton(VOUCHER_RADIO_TEXT);
		JRadioButton bankTransferRadioButton = new JRadioButton(BANK_TRANSFER_RADIO_TEXT);
		voucherRadioButton.setActionCommand(VOUCHER_RADIO_TEXT);
		bankTransferRadioButton.setActionCommand(BANK_TRANSFER_RADIO_TEXT);
		refundButtonGroup = new ButtonGroup();
		refundButtonGroup.add(voucherRadioButton);
		refundButtonGroup.add(bankTransferRadioButton);
		this.add(refundLabel);
		this.add(voucherRadioButton);
		this.add(bankTransferRadioButton);

		// Aggiungi bordi evidenziati
		this.setBorder(BorderFactory.createTitledBorder("Modalità di Rimborso")); //   bordo titolato
		this.setBackground(Color.cyan); // colore di sfondo
	}
	public ButtonGroup getRefundButtonGroup() {
		return refundButtonGroup;
	}

}
