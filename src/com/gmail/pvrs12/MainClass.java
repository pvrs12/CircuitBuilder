package com.gmail.pvrs12;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class MainClass {

	public static ArrayList<Gate> gates;

	public static SwitchGate swi1;
	public static SwitchGate swi2;
	public static NotGate not1;
	public static NotGate not2;
	public static AndGate and1;
	public static AndGate and2;
	public static SplitGate split1;
	public static SplitGate split2;
	public static OrGate or;
	public static XorGate xor;
	public static OutputGate outG;

	/**
	 * A text based text method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		gates = new ArrayList<Gate>();
		Wire in1 = new Wire();
		Wire in2 = new Wire();
		Wire out1 = new Wire();
		Wire out2 = new Wire();
		Wire back1 = new Wire();
		Wire back2 = new Wire();
		Wire last1 = new Wire();
		Wire last2 = new Wire();
		Wire toNot1 = new Wire();
		Wire toNot2 = new Wire();
		swi1 = new SwitchGate(in1);
		swi2 = new SwitchGate(in2);
		// not = new NotGate(in1, out);
		// and = new AndGate(in1, in2, out);
		// or = new OrGate(in1, in2, out);
		// xor = new XorGate(in1, in2, out);
		and1 = new AndGate(in1, back2, toNot1);
		and2 = new AndGate(in1, back1, toNot1);
		not1 = new NotGate(toNot1, out1);
		not2 = new NotGate(toNot2, out2);
		split1 = new SplitGate(out1, last1, back1);
		split2 = new SplitGate(out2, last2, back2);
		outG = new OutputGate(last2);
		gates.add(swi1);
		gates.add(swi2);
		// gates.add(not);
		// gates.add(and);
		// gates.add(or);
		// gates.add(xor);
		gates.add(and1);
		gates.add(and2);
		gates.add(not1);
		gates.add(split1);
		gates.add(split2);
		gates.add(outG);

		System.out.print("Run how many times: ");
		Scanner in = new Scanner(System.in);
		int times = in.nextInt();
		run(times);
	}

	public static void run(int runtimes) {
		for (int i = 0; i < runtimes; i++) {
			changeStates();
			for (Gate g : gates) {
				g.implementation();
			}
		}
	}

	public static void changeStates() {
		Scanner in = new Scanner(System.in);
		int count = 0;
		System.out
				.println("Enter the state of the swich, Type \"no\" to leave it unchanged. Type \"flip\" to swap states. Type \"0\" to set it off. Type \"1\" to turn it on. Type \"quit\" to quit");
		for (Gate g : gates) {
			if (g instanceof SwitchGate) {
				System.out.println("Set switch " + count);
				String temp = in.next();
				if (temp.equalsIgnoreCase("no")) {

				} else if (temp.equalsIgnoreCase("filp")) {
					((SwitchGate) g).flip();
				} else if (temp.equalsIgnoreCase("0")) {
					((SwitchGate) g).setIsOn(false);
				} else if (temp.equalsIgnoreCase("1")) {
					((SwitchGate) g).setIsOn(true);
				} else if (temp.equalsIgnoreCase("quit")) {
					System.exit(0);
				} else {
					System.out
							.println("Invalid input, leaving switch unchanged");
				}
				count++;
			}
		}
		System.out.println("Type anything and press enter to advance a tick");
		in.next();
	}
}
