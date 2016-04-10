package com.gmail.pvrs12;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class SwitchGateLabel extends GateLabel {
	/**
	 * 
	 */
	private JLabel onMode;
	private static final long serialVersionUID = -4779579652325882348L;

	public SwitchGateLabel(JLabel on) {
		super(new ImageIcon(
				GateLabel.class
						.getResource("/resources/SmallSwitchGateOff.PNG")),
				new SwitchGate());
		onMode = on;
		onMode.setVisible(false);
		setName("SwitchOff");
	}

	public JLabel getOnMode() {
		return onMode;
	}

	public void turnOn() {
		// if it is on, use the on image
		onMode.setVisible(true);
		((SwitchGate) getGate()).setIsOn(true);
		setName("SwitchOn");
	}

	public void turnOff() {
		// if it is off, use the off image
		onMode.setVisible(false);
		((SwitchGate) getGate()).setIsOn(false);
		setName("SwitchOff");
	}
}
