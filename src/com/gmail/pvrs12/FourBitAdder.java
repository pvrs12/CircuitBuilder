package com.gmail.pvrs12;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FourBitAdder {

	private JFrame frmFourBitAdder;
	private int NUM_ADDERS = 4;
	private JTextField textField;
	private SwitchGate[] switches = new SwitchGate[NUM_ADDERS * 2];
	private FullAdder[] adders = new FullAdder[NUM_ADDERS];
	private boolean[] outputs = new boolean[NUM_ADDERS];
	// private boolean overflow;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FourBitAdder window = new FourBitAdder();
					window.frmFourBitAdder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FourBitAdder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFourBitAdder = new JFrame();
		frmFourBitAdder.setTitle("Four Bit Adder");
		frmFourBitAdder.setBounds(100, 100, 340, 125);
		frmFourBitAdder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFourBitAdder.getContentPane().setLayout(null);
		frmFourBitAdder.setResizable(false);

		JLabel lblInput = new JLabel("Input A");
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput.setBounds(10, 19, 78, 14);
		frmFourBitAdder.getContentPane().add(lblInput);

		JLabel lblOutput = new JLabel("Input B");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(98, 19, 78, 14);
		frmFourBitAdder.getContentPane().add(lblOutput);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(186, 19, 131, 67);
		frmFourBitAdder.getContentPane().add(panel_1);

		JButton btnAddEmUp = new JButton("Add em' up");
		btnAddEmUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				evaluate();
			}
		});
		panel_1.add(btnAddEmUp);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(20, 44, 68, 20);
		frmFourBitAdder.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(108, 44, 68, 20);
		frmFourBitAdder.getContentPane().add(textField_2);
		addGates();
	}

	public void evaluate() {
		boolean[] inputa = new boolean[NUM_ADDERS];
		boolean[] inputb = new boolean[NUM_ADDERS];
		String ina = textField_1.getText();
		String inb = textField_2.getText();
		ina = ina.trim();
		inb = inb.trim();
		// StringBuilder temp=new StringBuilder(ina);
		// temp.reverse();
		// ina=temp.toString();
		// temp=new StringBuilder(inb);
		// temp.reverse();
		// inb=temp.toString();
		if (ina.length() < NUM_ADDERS) {
			int zeros = NUM_ADDERS - ina.length();
			for (int i = 0; i < zeros; i++) {
				ina = "0" + ina;
			}
		}
		if (ina.length() > NUM_ADDERS) {
			ina = ina.substring(ina.length() - NUM_ADDERS);
		}
		if (inb.length() < NUM_ADDERS) {
			int zeros = NUM_ADDERS - inb.length();
			for (int i = 0; i < zeros; i++) {
				inb = "0" + inb;
			}
		}
		if (inb.length() > NUM_ADDERS) {
			inb = inb.substring(inb.length() - NUM_ADDERS);
		}
		textField_1.setText(ina);
		textField_2.setText(inb);
		for (int i = 0; i < NUM_ADDERS; i++) {
			int input = NUM_ADDERS - i - 1;
			inputa[input] = ((ina.substring(i, i + 1)).equals("1"));
			inputb[input] = ((inb.substring(i, i + 1)).equals("1"));
		}
		for (int i = 0; i < NUM_ADDERS; i++) {
			// System.out.println("Input A Bit "+i+"= "+inputa[i]+" Input B Bit "+i+"= "+inputb[i]);
			switches[i + NUM_ADDERS].setIsOn(inputb[i]);
			switches[i].setIsOn(inputa[i]);
		}
		for (int i = 0; i < NUM_ADDERS; i++) {
			switches[i].implementation();
			switches[i + NUM_ADDERS].implementation();
			adders[i].implementation();
			// System.out.println("Input A= "+switches[i].outputWires[0].getValue()+" Input B= "+switches[i].outputWires[0].getValue());
			// System.out.println("Sum= "+adders[i].getOutput()+" Carry= "+adders[i].getCarry().getValue());
			outputs[i] = adders[i].getOutput();
		}
		output();
	}

	private void addGates() {
		Wire[] wires = new Wire[NUM_ADDERS * 2];
		Wire[] couts = new Wire[NUM_ADDERS];
		Wire cin1 = new Wire();
		for (int i = 0; i < wires.length; i++) {
			wires[i] = new Wire();
			switches[i] = new SwitchGate(wires[i]);
		}
		for (int i = 0; i < adders.length; i++) {
			couts[i] = new Wire();
			Wire cin;
			if (i == 0) {
				cin = cin1;
			} else {
				cin = couts[i - 1];
			}
			adders[i] = new FullAdder(wires[i], wires[i + NUM_ADDERS], cin,
					couts[i]);
		}

	}

	private void output() {
		String temp = "";
		for (int i = 0; i < outputs.length; i++) {
			if (outputs[i]) {
				temp += "1";
			} else {
				temp += "0";
			}
		}
		StringBuilder temp1 = new StringBuilder(temp);
		temp1.reverse();
		temp = temp1.toString();
		if (adders[NUM_ADDERS - 1].getCarry().getValue()) {
			temp = "1    " + temp;
		} else {
			temp = "0    " + temp;
		}
		textField.setText(temp);
	}
}
