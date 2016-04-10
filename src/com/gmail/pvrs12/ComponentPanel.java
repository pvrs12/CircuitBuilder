package com.gmail.pvrs12;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author psv001 3/23/12
 * 
 */
public class ComponentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 891311897607565225L;
	private ArrayList<Point> point1 = new ArrayList<Point>();
	private ArrayList<Point> point2 = new ArrayList<Point>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<Point> getPoint1() {
		return point1;
	}

	public ArrayList<Point> getPoint2() {
		return point2;
	}

	public ComponentPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentPanel(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ComponentPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ComponentPanel(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Paints wires onto the screen as needed the wires are stored in the
	 * ArrayLists point1 and point2
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		for (int i = 0; i < point1.size() && i < point2.size(); i++) {
			g.drawLine(point1.get(i).x, point1.get(i).y, point2.get(i).x,
					point2.get(i).y);
			g.fillOval(point1.get(i).x - 2, point1.get(i).y - 2, 5, 5);
			g.fillOval(point2.get(i).x - 2, point2.get(i).y - 2, 5, 5);
		}
	}

}
