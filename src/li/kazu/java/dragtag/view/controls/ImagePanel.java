package li.kazu.java.dragtag.view.controls;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/**
 * small control to display images
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class ImagePanel extends JComponent {

	/* attributes */
	private Image image;
	private Dimension dim = new Dimension();

	/** ctor */
	public ImagePanel() {
		setLayout(null);
	}
	
	/** set the image */
	public void setImage(Image img) {
		this.image = img;
		if (img != null) {
			dim.width = image.getWidth(null);
			dim.height = image.getHeight(null);
		} else {
			dim.width = 0;
			dim.height = 0;
		}
		this.setVisible(false);
		this.setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return dim;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {g.drawImage(image, 0, 0, null);}
	}

}
