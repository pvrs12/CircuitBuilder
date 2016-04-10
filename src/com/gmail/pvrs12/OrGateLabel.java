package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class OrGateLabel extends GateLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779579652325882348L;

	public OrGateLabel() {
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallOrGate.PNG")),
				new OrGate());
		setName("Or");
	}
}
