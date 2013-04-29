package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.ResourceSearch;

@SuppressWarnings("serial")
public class ResourceSearchFrame extends JFrame {
	private ResourceSearch resourceSearch;
	private Resource selected;
	/**
	 * Creates a new instance of ResourceSearchFrame based on services
	 * @param services
	 */
	public ResourceSearchFrame(Services services) {

		this.resourceSearch = new ResourceSearch(services);
		setLayout(new BorderLayout());
		add(resourceSearch, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		JButton info = new JButton("Info");
		info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource res = resourceSearch.getSelected();
				if (res != null) {
					ResourceInfoFrame infoFrame = new ResourceInfoFrame(res);
					infoFrame.setVisible(true);
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		JButton select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSelected(resourceSearch.getSelected());
				setVisible(false);
			}
		});
		
		bottomPanel.add(info, BorderLayout.WEST);
		bottomPanel.add(select, BorderLayout.CENTER);
		
		add(bottomPanel, BorderLayout.SOUTH);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setTitle("Select resource");
		setSize(300, 200);
	}
	/**
	 * Gives Selected resource
	 * @return Selected resource
	 */
	public Resource getSelected() {
		return selected;
	}
	/**
	 * Sets selected resource
	 * @param selected
	 */
	public void setSelected(Resource selected) {
		this.selected = selected;
	}
}
