package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class DelayGateLabel extends GateLabel {
	private static final long serialVersionUID = -4779579652325882348L;

	/**
	 * Create a new DelayGateLabel with the specified delay
	 * 
	 * @param delay
	 *            the time to wait
	 */
	public DelayGateLabel(double delay) {
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallDelayGate.PNG")),
				new DelayGate(delay));
		setName("Delay");
	}

}
