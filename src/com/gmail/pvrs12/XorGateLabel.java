package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class XorGateLabel extends GateLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -47795325882348L;

	public XorGateLabel() {
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallXorGate.PNG")),
				new XorGate());
		setName("Xor");
	}
}
