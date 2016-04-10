package com.gmail.pvrs12;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author psv001 3/23/12 This class is the main application window, it handles
 *         all gate placement and wiring
 * 
 */
public class CircuitBuilder {

	// These comments below got all jumbled with the auto-format, it's just
	// personal notes about saving the gate configurations

	// TODO Add in Some way to save thoughts on the topic
	/*
	 * SAVING ====== 1) The gate name followed by the number of inputs and
	 * outputs and what the connect to Example ======= SWITCH: 0 1 input:
	 * output: AND SWITCH: 0 1 input: output: AND AND: 2 1 input: SWITCH SWITCH
	 * output: OUTPUT OUTPUT: 1 0 input: AND output:
	 * 
	 * The flaw with this technique (YAML) is distinguishing one gate from
	 * another (look at the switches)
	 * 
	 * Unfortunately thats all i have for now...
	 */
	private JFrame frmCircuitBuilder;
	private ArrayList<Gate> gates;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButtonMenuItem rdbtnmntmNot, rdbtnmntmAnd, rdbtnmntmOr,
			rdbtnmntmXor, rdbtnmntmSplit, rdbtnmntmSwitch, rdbtnmntmOutput,
			rdbtnmntmLabel, rdbtnmntmDelay;
	private String selected = "None";
	private ComponentPanel viewPort;
	private JRadioButtonMenuItem rdbtnmntmNone;
	private JLabel lblNone, lblNewLabel, label;
	private String general = "", status = "Ok";
	// private Point first, second;
	private GateLabel firstGateLabel, secondGateLabel;
	private Component horizontalStrut_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Sets the Look and Feel to Nimbus, because it's pretty
					for (LookAndFeelInfo info : UIManager
							.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					CircuitBuilder window = new CircuitBuilder();
					window.frmCircuitBuilder.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public CircuitBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				int temp = -1;
				while (true) {
					if (gates != null && gates.size() != temp) {
						System.out.println(gates.size());
						// if this code isn't here the for loop gets skipped,
						// don't ask me why
						temp = gates.size();
					}
					// }
					for (int i = 0; gates != null && i < gates.size(); i++) {
						if (i < gates.size()) {
							Gate g = gates.get(i);
							try {
								g.implementation();
							} catch (NullPointerException e) {

							} finally {
								updateStatusBar();
							}
						}
					}
				}
			}
		}, "Circuit Runner");
		// starts a thread called Circuit Runner which updates all of the logic
		// gates
		th.start();
		gates = new ArrayList<Gate>();
		frmCircuitBuilder = new JFrame();
		frmCircuitBuilder.setTitle("Circuit Builder");
		frmCircuitBuilder.setBounds(100, 100, 450, 300);
		frmCircuitBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// viewPort stores all the gates and the wires
		viewPort = new ComponentPanel();
		viewPort.setBackground(Color.WHITE);
		frmCircuitBuilder.getContentPane().add(viewPort, BorderLayout.CENTER);
		viewPort.setLayout(null);
		viewPort.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				placeElement(arg0);
			}
		});
		viewPort.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));

		JPanel panel = new JPanel();
		frmCircuitBuilder.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel lblStatus = new JLabel("Status: ");
		panel.add(lblStatus);

		lblNewLabel = new JLabel("Ok");
		panel.add(lblNewLabel);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);

		JLabel lblSelection = new JLabel("Selection: ");
		panel.add(lblSelection);

		lblNone = new JLabel("None");
		panel.add(lblNone);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);

		label = new JLabel("");
		panel.add(label);

		horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);

		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(frmCircuitBuilder,
						"Are you sure you want to clear ALL components?",
						"Confirmation", JOptionPane.YES_NO_OPTION);
				if (n != 0) {
					return;
				}
				// does the deletion method for each component c
				for (Component c : viewPort.getComponents()) {
					// Component c = viewPort.getComponent(i);
					if (c instanceof GateLabel) {
						GateLabel clicked = (GateLabel) c;
						try {
							for (Wire w : clicked.getGate().inputWires) {
								viewPort.getPoint1().remove(w.getPoint1());
								viewPort.getPoint2().remove(w.getPoint2());
							}
						} catch (NullPointerException r) {
						}
						try {
							for (Wire w : clicked.getGate().outputWires) {
								viewPort.getPoint1().remove(w.getPoint1());
								viewPort.getPoint2().remove(w.getPoint2());
							}
						} catch (NullPointerException r) {
						}
						clicked.getGate().removeWires();
						gates.remove(clicked.getGate());
					}
					JLabel clicked = (JLabel) c;
					viewPort.remove(clicked);
					// System.out.println("Removed " + c.getName() + "Gate");
					// viewPort.repaint();
				}
				viewPort.repaint();
			}
		});
		panel.add(btnClearAll);

		JMenuBar menuBar = new JMenuBar();
		frmCircuitBuilder.setJMenuBar(menuBar);

		JMenu mnComponent = new JMenu("Component");
		menuBar.add(mnComponent);

		rdbtnmntmNone = new JRadioButtonMenuItem("None");
		rdbtnmntmNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "None";
				updateStatusBar();
			}
		});
		rdbtnmntmNone.setSelected(true);
		buttonGroup.add(rdbtnmntmNone);
		mnComponent.add(rdbtnmntmNone);

		rdbtnmntmNot = new JRadioButtonMenuItem("Not");
		rdbtnmntmNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Not";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmNot);
		mnComponent.add(rdbtnmntmNot);

		rdbtnmntmDelay = new JRadioButtonMenuItem("Delay");
		rdbtnmntmDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Delay";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmDelay);
		mnComponent.add(rdbtnmntmDelay);

		rdbtnmntmAnd = new JRadioButtonMenuItem("And");
		rdbtnmntmAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "And";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmAnd);
		mnComponent.add(rdbtnmntmAnd);

		rdbtnmntmOr = new JRadioButtonMenuItem("Or");
		rdbtnmntmOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Or";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmOr);
		mnComponent.add(rdbtnmntmOr);

		rdbtnmntmXor = new JRadioButtonMenuItem("Xor");
		rdbtnmntmXor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Xor";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmXor);
		mnComponent.add(rdbtnmntmXor);

		rdbtnmntmSplit = new JRadioButtonMenuItem("Split");
		rdbtnmntmSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Split";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmSplit);
		mnComponent.add(rdbtnmntmSplit);

		rdbtnmntmSwitch = new JRadioButtonMenuItem("Switch");
		rdbtnmntmSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Switch";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmSwitch);
		mnComponent.add(rdbtnmntmSwitch);

		rdbtnmntmOutput = new JRadioButtonMenuItem("Output");
		rdbtnmntmOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Output";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmOutput);
		mnComponent.add(rdbtnmntmOutput);

		rdbtnmntmLabel = new JRadioButtonMenuItem("Label");
		rdbtnmntmLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = "Label";
				updateStatusBar();
			}
		});
		buttonGroup.add(rdbtnmntmLabel);
		mnComponent.add(rdbtnmntmLabel);

	}

	/**
	 * The program goes here when any thing that was added is clicked
	 * 
	 * @param arg0
	 *            The MouseEvent passed when the Component was clicked
	 */
	private void elementClicked(MouseEvent arg0) {
		if (!(arg0.getSource() instanceof GateLabel)) {
			if (arg0.getButton() == MouseEvent.BUTTON2) {
				deleteComponent((JLabel) arg0.getSource(),
						((JLabel) arg0.getSource()).getText(), arg0);
			}
			return;
		}
		GateLabel clicked = (GateLabel) arg0.getSource();
		String name = clicked.getName();
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			wireComponent(clicked, name, arg0);
		} else if (arg0.getButton() == MouseEvent.BUTTON2) {
			deleteComponent(clicked, name, arg0);
		} else {
			activateComponent(clicked, name, arg0);
		}

	}

	/**
	 * Wires one component to another
	 * 
	 * @param clicked
	 *            the component that was clicked
	 * @param name
	 *            the text name of the clicked component
	 * @param e
	 *            the same MouseEvent from the original click
	 */
	private void wireComponent(GateLabel clicked, String name, MouseEvent e) {
		// If nothing has been clicked
		if (firstGateLabel == null) {
			// set the first gate
			firstGateLabel = clicked;
			// tell the user to select another
			general = "Select a second Gate";
			updateStatusBar();
		} else {
			// if a first gate has already been picked
			secondGateLabel = clicked;
			general = "";
			updateStatusBar();
			// second=e.getPoint();
			Gate firstGate = firstGateLabel.getGate();

			Gate secondGate = clicked.getGate();
			int inPinFilled, outPinFilled;
			// put up a dialog to take in 2 wires
			WiringDialog wd = new WiringDialog(firstGate, secondGate,
					frmCircuitBuilder);
			wd.setVisible(true);
			// store the input and output wires from the users input in the
			// dialog
			inPinFilled = wd.inPinFilled;
			outPinFilled = wd.outPinFilled;
			if (inPinFilled < 0 || outPinFilled < 0) {
				// if either are negative the user clicked cancel
				firstGateLabel = null;
				secondGateLabel = null;
				return;
			}
			Point point1, point2;
			// This section of code is unfortunately very static to the project
			// It directly paints the wires to the pins that are drawn on the
			// gates
			// this disables using any more than 2 pin gates
			// or switching the orientation of the pins on the gate
			if (firstGate.outputWires.length == 1) {
				point1 = new Point(firstGateLabel.getX()
						+ firstGateLabel.getWidth(), firstGateLabel.getHeight()
						/ 2 + firstGateLabel.getY());
			} else {
				point1 = new Point(firstGateLabel.getX()
						+ firstGateLabel.getWidth(), firstGateLabel.getHeight()
						/ 2 + (outPinFilled == 0 ? -5 : 5)
						+ firstGateLabel.getY());
			}
			if (secondGate.inputWires.length == 1) {
				point2 = new Point(secondGateLabel.getX(),
						secondGateLabel.getHeight() / 2
								+ secondGateLabel.getY());
			} else {
				point2 = new Point(secondGateLabel.getX(),
						secondGateLabel.getHeight() / 2
								+ (inPinFilled == 0 ? -5 : 5)
								+ secondGateLabel.getY());
			}
			// set the points on the wire so that they are removed correctly on
			// deletion
			firstGate.outputWires[outPinFilled].setPoints(point1, point2);
			viewPort.getPoint1().add(point1);
			viewPort.getPoint2().add(point2);
			firstGateLabel = null;
			secondGateLabel = null;
			viewPort.repaint();
		}
	}

	private void deleteComponent(JLabel clicked, String name, MouseEvent e) {
		viewPort.remove(clicked);
		viewPort.repaint();
	}

	/**
	 * Removes a Gate and it's Label from both the list of gates and the screen
	 * Also removes any wires attached to the gate
	 * 
	 * @param clicked
	 *            the gate to be removed
	 * @param name
	 *            the text name of the gate
	 * @param e
	 *            the original MouseEvent from the click
	 */
	private void deleteComponent(GateLabel clicked, String name, MouseEvent e) {
		viewPort.remove(clicked);
		// System.out.println("Removed " + name + "Gate");
		try {
			for (Wire w : clicked.getGate().inputWires) {
				viewPort.getPoint1().remove(w.getPoint1());
				viewPort.getPoint2().remove(w.getPoint2());
			}
		} catch (NullPointerException r) {
		}
		try {
			for (Wire w : clicked.getGate().outputWires) {
				viewPort.getPoint1().remove(w.getPoint1());
				viewPort.getPoint2().remove(w.getPoint2());
			}
		} catch (NullPointerException r) {
		}
		// if it is a SwitchGate or an OutputGate remove the on indicator as
		// well
		if (clicked instanceof SwitchGateLabel) {
			viewPort.remove(((SwitchGateLabel) clicked).getOnMode());
		}
		if (clicked instanceof OutputGateLabel) {
			viewPort.remove(((OutputGateLabel) clicked).getOnMode());
		}
		clicked.getGate().removeWires();
		gates.remove(clicked.getGate());
		viewPort.repaint();
	}

	/**
	 * Called after a standard click to a component, turns on or off switches
	 * only right now
	 * 
	 * @param clicked
	 *            the Gate to activate
	 * @param name
	 *            the text name of that gate
	 * @param e
	 *            the original MouseEvent from the click
	 */
	private void activateComponent(GateLabel clicked, String name, MouseEvent e) {
		if (name.equals("SwitchOff")) {
			((SwitchGateLabel) clicked).turnOn();
		} else if (name.equals("SwitchOn")) {
			((SwitchGateLabel) clicked).turnOff();
		}
		// System.out.println(clicked.getName() + "Gate clicked");
	}

	private void updateStatusBar() {
		lblNone.setText(selected);
		lblNewLabel.setText(status);
		label.setText(general);
	}

	/**
	 * Called when the viewPort is clicked without a component
	 * 
	 * @param arg0
	 *            the MouseEvent from the click
	 */
	private void placeElement(MouseEvent arg0) {
		// if the anything other than the Left button is clicked i don't care
		if (arg0.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		ButtonModel bm = buttonGroup.getSelection();
		JLabel temp;
		// The On label to indicate for switches and output gates
		JLabel extra = new JLabel(new ImageIcon(
				GateLabel.class.getResource("/resources/On.PNG")));
		if (bm == rdbtnmntmAnd.getModel()) {
			temp = new AndGateLabel();
		} else if (bm == rdbtnmntmOr.getModel()) {
			temp = new OrGateLabel();
		} else if (bm == rdbtnmntmNot.getModel()) {
			temp = new NotGateLabel();
		} else if (bm == rdbtnmntmDelay.getModel()) {
			// delay gates require an extra parameter for the time of delay
			// currently the program only accepts 100 because of poor
			// programming
			double delay = .1;
			temp = new DelayGateLabel(delay * 1000);
		} else if (bm == rdbtnmntmXor.getModel()) {
			temp = new XorGateLabel();
		} else if (bm == rdbtnmntmSplit.getModel()) {
			temp = new SplitGateLabel();
		} else if (bm == rdbtnmntmSwitch.getModel()) {
			temp = new SwitchGateLabel(extra);
		} else if (bm == rdbtnmntmOutput.getModel()) {
			temp = new OutputGateLabel(extra);
		} else if (bm == rdbtnmntmLabel.getModel()) {
			// if it were a Label then display a dialog asking the user for the
			// text they want to display
			String s = (String) JOptionPane.showInputDialog(frmCircuitBuilder,
					"Enter the text for your label", "Enter a Label",
					JOptionPane.PLAIN_MESSAGE);
			if (s == null || s.equals(""))
				return;
			temp = new JLabel(s);
			// a label has a special tooltip
			temp.setToolTipText(s);
		} else {
			// if somehow the user managed to select nothing then leave
			return;
		}
		int x = arg0.getX();
		int y = arg0.getY();
		// System.out.println(x + "," + y);
		if (!(temp instanceof GateLabel)) {
			// if it's a
			temp.setBounds(x, y, 40, 10);
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					elementClicked(e);
				}
			});
			viewPort.add(temp);
			viewPort.repaint();
			return;
		}
		temp.setToolTipText(((GateLabel) temp).getGate().name);
		if (temp instanceof DelayGateLabel) {
			temp.setToolTipText(temp.getToolTipText()
					+ " - "
					+ ((DelayGate) ((DelayGateLabel) temp).getGate())
							.getAdjustedDelay());
		}
		// temp.getIcon().paintIcon(viewPort, viewPort.getGraphics(), x, y);
		int height = temp.getIcon().getIconHeight();
		int width = temp.getIcon().getIconWidth();
		int half = height / 2;
		// if it is a switch or an output then add the on indicator
		if (temp instanceof SwitchGateLabel) {
			extra.setBounds(x + 1, y + 1 - half,
					extra.getIcon().getIconWidth(), extra.getIcon()
							.getIconWidth());
			viewPort.add(extra);
		}
		if (temp instanceof OutputGateLabel) {
			extra.setBounds(x + 22, y + 1 - half, extra.getIcon()
					.getIconWidth(), extra.getIcon().getIconWidth());
			viewPort.add(extra);
		}
		// don't show the indicator
		extra.setVisible(false);
		temp.setBounds(x, y - half, width, height);
		temp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// elementClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				elementClicked(arg0);
			}
		});
		gates.add(((GateLabel) temp).getGate());
		viewPort.add(temp);
		viewPort.repaint();
	}
}
