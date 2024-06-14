package it.unipv.ingsfw.SmartWarehouse.View.Return.ItemAndReason;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RefundPanel extends JPanel{
	public static final String VOUCHER_RADIO_TEXT = "VOUCHER (recommended): receive an instant refund on a non-expiring voucher that can be spent throughout the shop.";
	public static final String BANK_TRANSFER_RADIO_TEXT = "Bank Transfer: receive a transfer to the card you used to make the purchase. Estimated time 14 working days.";
	private ButtonGroup refundButtonGroup;

	public RefundPanel() {
		super();
		this.repaint();
		this.setLayout(new GridLayout(0, 1));
		//refundPanel.setLayout(new BoxLayout(refundPanel, BoxLayout.Y_AXIS));
		JLabel refundLabel = new JLabel("Choose the way you prefer to be reimbursed:");
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
		this.setBorder(BorderFactory.createTitledBorder("Refund method")); //   bordo titolato
		this.setBackground(Color.cyan); // colore di sfondo
	}
	public ButtonGroup getRefundButtonGroup() {
		return refundButtonGroup;
	}

}
