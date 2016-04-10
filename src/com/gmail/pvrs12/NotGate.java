package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class NotGate extends Gate {
	public NotGate(Wire in, Wire out) {
		super(1, 1, "Not");
		inputWires[0] = in;
		outputWires[0] = out;
		in.setTo(this);
		out.setFrom(this);
	}

	public NotGate() {
		super(1, 1, "Not");
	}

	public void implementation() {
		outputWires[0].setValue(!inputWires[0].getValue());
		// System.out.println("HERE");
	}
}
