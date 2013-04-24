package edu.hist.team3.catering.gui.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private BufferedImage image;
	private boolean loading;

	public ImagePanel() {
		image = null;
	}
	
	public ImagePanel(BufferedImage image) {
		this.image = image;
	}
	
	public boolean isLoading() {
		return loading;
	}
	
	public void setLoading(boolean value) {
		this.loading = value;
	}
	
	public BufferedImage getImage() {
		return image;
	}

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