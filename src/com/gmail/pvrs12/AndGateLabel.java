package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class AndGateLabel extends GateLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779579652325882348L;

	public AndGateLabel() {
		// Gets the image of an AndGate
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallAndGate.PNG")),
				new AndGate());
		setName("And");
	}
}
