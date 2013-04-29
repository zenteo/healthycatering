package edu.hist.team3.catering.gui.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage image;
	private boolean loading;
	/**
	 * Creates a new instance of ImagePanel
	 */
	public ImagePanel() {
		image = null;
	}
	/**
	 * Creates a new instance of ImagePanel based on buffered image
	 * @param image
	 */
	public ImagePanel(BufferedImage image) {
		this.image = image;
	}
	/**
	 * Tells you if it is loading
	 * @return yes/no
	 */
	public boolean isLoading() {
		return loading;
	}
	/**
	 * Sets if it is loading
	 * @param value
	 */
	public void setLoading(boolean value) {
		this.loading = value;
	}
	/**
	 * Gives image
	 * @return image
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * Sets image
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
		else {
			super.paintComponent(g);
			if (isLoading()) {
				g.drawString("Loading...", getWidth() / 2, getHeight() / 2);
			}
		}
	}
}