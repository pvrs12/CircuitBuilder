package com.gmail.pvrs12;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class DelayGate extends Gate {
	private boolean lastInput;
	private boolean running = false;
	// private static boolean hasRun=false;
	private int delay;
	Thread th;

	/**
	 * Creates a Delay gate designed to wait a specific amount of time
	 * 
	 * @param delay
	 *            the time to wait
	 */
	public DelayGate(final double delay) {
		super(1, 1, "Delay");
		this.delay = (int) delay;
		th = new Thread(new Runnable() {
			public void run() {
				// System.out.println("HERE");
				// This saves system resources
				if (outputWires[0] == null || inputWires[0] == null
						|| inputWires[0].getValue() == lastInput || running) {
					if (outputWires[0] != null)
						outputWires[0].setValue(lastInput);
					return;
				}

				long end = System.currentTimeMillis() + (int) delay;
				running = true;
				while (System.currentTimeMillis() < end)
					;
				outputWires[0].setValue(inputWires[0].getValue());
				lastInput = inputWires[0].getValue();
				running = false;
			}
		}, "Delay Gate Timer");
	}

	public int getDelay() {
		return delay;
	}

	public double getAdjustedDelay() {
		return delay / 1000.;
	}

	public DelayGate(Wire in, Wire out, int delay) {
		this(delay);
		inputWires[0] = in;
		outputWires[0] = out;
		in.setTo(this);
		out.setFrom(this);
		// this.delay=delay;
	}

	@Override
	public void implementation() {
		// System.out.println("here");
		// if the thread hasn't started start it, otherwise, just re-run it
		if (th.getState() != Thread.State.NEW) {
			th.start();
		} else {
			th.run();
		}
	}
}
