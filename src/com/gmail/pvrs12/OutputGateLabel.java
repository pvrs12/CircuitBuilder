package com.gmail.pvrs12;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class OutputGateLabel extends GateLabel {
	/**
	 * 
	 */
	private JLabel onMode;
	private static final long serialVersionUID = -4779579652325882348L;

	public OutputGateLabel(JLabel on) {
		super(new ImageIcon(
				GateLabel.class
						.getResource("/resources/SmallOutputGateOff.PNG")),
				new OutputGate());
		onMode = on;
		onMode.setVisible(false);
		setName("OutputOff");
	}

	public void turnOn() {
		onMode.setVisible(true);
		setName("OutputOn");
	}

	public JLabel getOnMode() {
		return onMode;
	}

	public void turnOff() {
		onMode.setVisible(false);
		setName("OutputOff");
	}

	/**
	 * Whenever an OutputGate's value changes, this is called It then sets the
	 * appropriate image for on or off
	 */
	public void onUpdate() {
		if (((OutputGate) getGate()).isValue() && !getName().equals("OutputOn"))
			turnOn();
		else if (!((OutputGate) getGate()).isValue()
				&& !getName().equals("OutputOff"))
			turnOff();
	}
}
