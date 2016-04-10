package com.gmail.pvrs12;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class WiringDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145179575027090550L;
	private final JPanel contentPanel = new JPanel();
	private WiringDialog top;
	private Wire wire;
	private JLabel fromGateName, toGateName;
	private JPanel toPanel, fromPanel;
	private Gate gate1, gate2;
	private int heightIn, heightOut;
	int outPinFilled, inPinFilled;
	private ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
	private final ButtonGroup fromButtonGroup = new ButtonGroup();
	private final ButtonGroup toButtonGroup = new ButtonGroup();

	/**
	 * Creates a dialog asking the user to select an output pin and an input pin
	 * to wire
	 */
	public WiringDialog(final Gate gate1, final Gate gate2, JFrame parent) {
		super(parent, "Wiring", true);
		wire = new Wire();
		this.gate1 = gate1;
		this.gate2 = gate2;
		setAlwaysOnTop(true);
		top = this;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 160);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblFrom = new JLabel("From: ");
		lblFrom.setBounds(7, 11, 48, 14);
		contentPanel.add(lblFrom);

		fromGateName = new JLabel("");
		fromGateName.setBounds(65, 11, 46, 14);
		contentPanel.add(fromGateName);

		JLabel lblTo = new JLabel("To: ");
		lblTo.setBounds(193, 11, 28, 14);
		contentPanel.add(lblTo);

		toGateName = new JLabel("");
		toGateName.setBounds(242, 11, 46, 14);
		contentPanel.add(toGateName);

		fromPanel = new JPanel();
		fromPanel.setBounds(7, 32, 161, 134);
		contentPanel.add(fromPanel);
		fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));

		JLabel lblOutput = new JLabel("Select an Output Pin");
		fromPanel.add(lblOutput);

		toPanel = new JPanel();
		toPanel.setBounds(193, 32, 161, 134);
		contentPanel.add(toPanel);
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));

		JLabel lblSelectAnInput = new JLabel("Select an Input Pin");
		toPanel.add(lblSelectAnInput);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ButtonModel from = fromButtonGroup.getSelection();
						ButtonModel to = toButtonGroup.getSelection();
						if (from == null || to == null) {
							JOptionPane
									.showMessageDialog(
											top,
											"Please select a Pin for both Input and Output",
											"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						for (JRadioButton b : buttons) {
							if (b.getModel() == from) {
								outPinFilled = Integer.parseInt(b.getText()
										.substring(4));
								gate1.outputWires[outPinFilled] = wire;
								wire.setFrom(gate1);
							}
							if (b.getModel() == to) {
								inPinFilled = Integer.parseInt(b.getText()
										.substring(4));
								gate2.inputWires[inPinFilled] = wire;
								wire.setTo(gate2);
							}
							top.dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						inPinFilled = -1;
						outPinFilled = -1;
						top.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		addRadioButtons();
		setSize(getWidth(), getHeight() + Math.max(heightIn, heightOut));
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	/**
	 * Adds a button for each input or output of each of the gates being wired
	 */
	private void addRadioButtons() {
		fromGateName.setText(gate1.name);
		toGateName.setText(gate2.name);
		for (int i = 0; i < gate1.outputWires.length; i++) {
			JRadioButton temp = new JRadioButton("Pin " + i);
			fromButtonGroup.add(temp);
			fromPanel.add(temp);
			heightIn += temp.getHeight() + 5;
			buttons.add(temp);
			if (i == 0) {
				temp.setSelected(true);
			}
		}
		for (int i = 0; i < gate2.inputWires.length; i++) {
			JRadioButton temp = new JRadioButton("Pin " + i);
			toButtonGroup.add(temp);
			toPanel.add(temp);
			heightOut += temp.getHeight() + 5;
			buttons.add(temp);
			if (i == 0) {
				temp.setSelected(true);
			}
		}
	}
}
