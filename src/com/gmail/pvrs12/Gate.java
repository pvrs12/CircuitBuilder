package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public abstract class Gate {
	protected int input;
	protected Wire[] inputWires;
	protected String name;
	protected int output;
	protected Wire[] outputWires;
	protected GateLabel listener;

	/**
	 * 
	 * @param input
	 *            the number of wires coming into this gate
	 * @param output
	 *            the number of wires going out of this gate
	 * @param name
	 *            the text name of this gate
	 */
	public Gate(int input, int output, String name) {
		this.input = input;
		this.output = output;
		this.name = name;
		inputWires = new Wire[input];
		outputWires = new Wire[output];
	}

	/**
	 * Specifies which GateLabel's onUpdate() method to call, used for
	 * OutputGates only
	 * 
	 * @param gl
	 */
	public void setListener(GateLabel gl) {
		listener = gl;
	}

	@SuppressWarnings("unused")
	/**
	 * Sets all the wires joined to the Gate to null so they aren't accidentally referenced again
	 */
	public void removeWires() {
		for (Wire w : inputWires) {
			w = null;
		}
		for (Wire w : outputWires) {
			w = null;
		}
	}

	public void wireIn(Wire w, int bit) {
		inputWires[bit] = w;
	}

	public void wireOut(Wire w, int bit) {
		outputWires[bit] = w;
	}

	/**
	 * This is what a Gate does to it's inputs and outputs
	 */
	public abstract void implementation();
}
