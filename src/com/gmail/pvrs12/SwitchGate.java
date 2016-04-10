package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class SwitchGate extends Gate {
	private boolean isOn;

	public SwitchGate(Wire out) {
		super(0, 1, "Switch");
		outputWires[0] = out;
		outputWires[0].setFrom(this);
		isOn = false;
	}

	public SwitchGate() {
		super(0, 1, "Switch");
	}

	public void implementation() {
		outputWires[0].setValue(isOn);
	}

	public void setIsOn(boolean val) {
		isOn = val;
	}

	public void flip() {
		isOn = !isOn;
	}
}
