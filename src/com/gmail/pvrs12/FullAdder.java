package com.gmail.pvrs12;

import java.util.ArrayList;

public class FullAdder {
	private Wire inA, inB, cIn, cOut;
	private SplitGate split1,split2,split3,split4;
	private AndGate and1,and2;
	private XorGate xor1,xor2;
	private OrGate or;
	private OutputGate out1;
	private ArrayList<Gate>gates=new ArrayList<Gate>();
	
	public boolean getOutput(){
		return out1.isValue();
	}
	
	public Wire getCarry(){
		return cOut;
	}
	
	public FullAdder(Wire in1,Wire in2,Wire cIn1,Wire cOut1){
		inA=in1;
		inB=in2;
		cIn=cIn1;
		cOut=cOut1;
		Wire[] wires=new Wire[12];//generate the wires in bulk
		for(int i=0;i<wires.length;i++){
			wires[i]=new Wire();
		}
		split1=new SplitGate(inA,wires[0],wires[1]);
		split2=new SplitGate(inB,wires[2],wires[3]);
		split3=new SplitGate(cIn,wires[4],wires[5]);
		xor1=new XorGate(wires[0],wires[2],wires[6]);
		and1=new AndGate(wires[1],wires[3],wires[7]);
		split4=new SplitGate(wires[6],wires[8],wires[9]);
		xor2=new XorGate(wires[4],wires[8],wires[10]);
		and2=new AndGate(wires[5],wires[9],wires[11]);
		or=new OrGate(wires[7],wires[11],cOut);
		out1=new OutputGate(wires[10]);
		gates.add(split1);
		gates.add(split2);
		gates.add(split3);
		gates.add(xor1);
		gates.add(and1);
		gates.add(split4);
		gates.add(xor2);
		gates.add(and2);
		gates.add(or);
		gates.add(out1);
	}
	
	public void implementation(){
		for(Gate g:gates){
			g.implementation();
		}
		//System.out.println(getOutput()+" =output\t"+getCarry().getValue()+" =carry");
	}

}
