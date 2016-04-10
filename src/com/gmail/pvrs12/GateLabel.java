package com.gmail.pvrs12;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public abstract class GateLabel extends JLabel {
	private static final long serialVersionUID = -1850106755750853878L;
	private Gate gate;

	public GateLabel(ImageIcon imageIcon, Gate g) {
		super(imageIcon);
		gate = g;
		gate.setListener(this);
	}

	public void setGate(Gate g) {
		gate = g;
		gate.setListener(this);
	}

	public Gate getGate() {
		return gate;
	}

	/**
	 * Called only if a Gate has specified a listener whenever the gates value
	 * changes
	 */
	public void onUpdate() {
	}

	public void addWireIn(Wire w, int pin) {
		gate.wireIn(w, pin);
	}

	public void addWireOut(Wire w, int pin) {
		gate.wireOut(w, pin);
	}
}
