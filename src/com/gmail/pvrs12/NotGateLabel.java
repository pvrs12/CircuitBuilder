package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class NotGateLabel extends GateLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779579652325882348L;

	public NotGateLabel() {
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallNotGate.PNG")),
				new NotGate());
		setName("Not");
	}

}
