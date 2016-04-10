package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class SplitGate extends Gate {

	public SplitGate(Wire in, Wire out1, Wire out2) {
		super(1, 2, "Splitter");
		inputWires[0] = in;
		outputWires[0] = out1;
		outputWires[1] = out2;
		in.setTo(this);
		out1.setFrom(this);
		out2.setFrom(this);
	}

	public SplitGate() {
		super(1, 2, "Split");
	}

	@Override
	public void implementation() {
		boolean val = inputWires[0].getValue();
		outputWires[0].setValue(val);
		outputWires[1].setValue(val);

	}

}
