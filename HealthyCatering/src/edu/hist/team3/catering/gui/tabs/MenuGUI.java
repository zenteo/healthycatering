package edu.hist.team3.catering.gui.tabs;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
/*
 * 
 *
 *Menu GUI
 *--
 *--
 *+ findResources()
 *+ editResources()
 *+ addResources()
 *+ removeResource()
 *+ findDishes()
 *+ editDises()
 *+ addDish()
 *+ removeDish()
 *+ findMenus()
 *+ addMenu()
 *+ editMenu()
 *+ removeMenu()
 */
public class MenuGUI {

	JFrame frame;
	Toolkit toolkit;

	public MenuGUI() {
		frame = new JFrame("Menu");
		frame.setLayout(new GridLayout(4, 4));
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
