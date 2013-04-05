package edu.hist.team3.catering.gui;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
/*
 * Delivery GUI
--
--
+ getDeliveres()
+ computeDeliveryRoute()
+ changeDeliveryStatus()

 */
public class DeliveryGUI {
	JFrame frame;
	Toolkit toolkit;
	public DeliveryGUI(){
		frame = new JFrame("Delivery");
		frame.setLayout(new GridLayout(4, 1));
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
		WindowEvent closingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
	}

}
