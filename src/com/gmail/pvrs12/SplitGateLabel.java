package com.gmail.pvrs12;

import javax.swing.ImageIcon;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class SplitGateLabel extends GateLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779579652325882348L;

	public SplitGateLabel() {
		super(new ImageIcon(
				GateLabel.class.getResource("/resources/SmallSplitGate.PNG")),
				new SplitGate());
		setName("Split");
	}
}
