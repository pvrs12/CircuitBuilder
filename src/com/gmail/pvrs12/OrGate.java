package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class OrGate extends Gate {

	public OrGate(Wire in1, Wire in2, Wire out) {
		super(2, 1, "Or");
		inputWires[0] = in1;
		inputWires[1] = in2;
		outputWires[0] = out;
		in1.setTo(this);
		in2.setTo(this);
		out.setFrom(this);
	}

	public OrGate() {
		super(2, 1, "Or");
	}

	@Override
	public void implementation() {
		Wire in1 = inputWires[0];
		Wire in2 = inputWires[1];
		Wire out = outputWires[0];
		out.setValue(in1.getValue() | in2.getValue());
	}

}
