package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class OutputGate extends Gate {
	private boolean value;

	public OutputGate(Wire in) {
		super(1, 0, "Output");
		inputWires[0] = in;
		inputWires[0].setTo(this);
	}

	public OutputGate() {
		super(1, 0, "Output");
	}

	public void implementation() {
		value = inputWires[0].getValue();
		if(listener!=null)
			listener.onUpdate();
	}

	public boolean isValue() {
		return value;
	}

}
