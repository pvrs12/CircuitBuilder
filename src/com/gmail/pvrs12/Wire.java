package com.gmail.pvrs12;

import java.awt.Point;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class Wire {
	private boolean value;
	private Gate from;
	private Gate to;
	private boolean highEdge;
	private boolean lowEdge;
	private Point point1, point2;

	public Wire(Gate to, Gate from) {
		highEdge = false;
		lowEdge = false;
		this.to = to;
		this.from = from;
		value = false;
	}

	public Wire() {
		highEdge = false;
		lowEdge = false;
		value = false;
	}

	public Wire(boolean val) {
		highEdge = false;
		lowEdge = false;
		value = val;
	}

	public Wire(Gate to, Gate from, boolean val) {
		highEdge = false;
		lowEdge = false;
		this.to = to;
		this.from = from;
		value = val;
	}

	public boolean getValue() {
		highEdge = false;
		lowEdge = false;
		return value;
	}

	public void setValue(boolean value) {
		highEdge = false;
		lowEdge = false;
		this.value = value;
		if (value) {
			highEdge = true;
		} else {
			lowEdge = true;
		}
	}

	public Gate getFrom() {
		highEdge = false;
		lowEdge = false;
		return from;
	}

	public void setFrom(Gate from) {
		highEdge = false;
		lowEdge = false;
		this.from = from;
	}

	public Gate getTo() {
		highEdge = false;
		lowEdge = false;
		return to;
	}

	public void setTo(Gate to) {
		highEdge = false;
		lowEdge = false;
		this.to = to;
	}

	public boolean isHighEdge() {
		return highEdge;
	}

	public boolean isLowEdge() {
		return lowEdge;
	}

	public void setPoints(Point a, Point b) {
		point1 = a;
		point2 = b;
	}

	public Point getPoint1() {
		return point1;
	}

	public Point getPoint2() {
		return point2;
	}

}
