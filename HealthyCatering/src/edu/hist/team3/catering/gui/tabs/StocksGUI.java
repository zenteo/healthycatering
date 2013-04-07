package edu.hist.team3.catering.gui.tabs;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * Stocks GUI
 --
 --
 + getResources()
 + getTodaysResources()
 + getResourcesWithCriticallyLowStock-count()
 + changeTheStock-CountOfAResource()

 */
public class StocksGUI {

	JFrame frame;
	Toolkit toolkit;

	public StocksGUI() {
		frame = new JFrame("Stocks");
		frame.setLayout(new GridLayout(3, 2));
		toolkit = Toolkit.getDefaultToolkit();
		frame.setSize(toolkit.getScreenSize());
		frame.setUndecorated(true);

		JButton exitButton = new JButton("Quit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				quitProgram();
			}
		});
		frame.add(exitButton);

		frame.setVisible(true);

	}

	private void quitProgram() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowEvent closingEvent = new WindowEvent(frame,
				WindowEvent.WINDOW_CLOSING);
		toolkit.getDefaultToolkit().getSystemEventQueue()
				.postEvent(closingEvent);
	}

}
