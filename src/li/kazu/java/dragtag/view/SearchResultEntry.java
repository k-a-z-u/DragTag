package li.kazu.java.dragtag.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupAlbumListener;
import li.kazu.java.dragtag.lookup.LookupTrack;
import li.kazu.java.dragtag.misc.LevenshteinDistance;
import li.kazu.java.dragtag.view.controls.ImagePanel;
import li.kazu.java.dragtag.view.controls.MaxLenLabel;
import li.kazu.java.dragtag.view.listeners.SearchSelectListener;

/**
 * represents one entry within the search result.
 * 
 * an entry usually represents a CD / an album and thus
 * might have a cover-image and provides a list of several
 * tracks, identified by artist - name.
 * 
 * the GUI provides some callbacks e.g. when the user
 * clicked on one of those tracks (artist - name).
 * 
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class SearchResultEntry extends JPanel implements LookupAlbumListener {

	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private ImagePanel image = new ImagePanel();
	private JPanel pnlMain = new JPanel();
	private JPanel pnlImage = new JPanel(new GridLayout(1,1,0,0));
	private JPanel pnlTracks = new JPanel();
	//private Font fontBold = pnlTracks.getFont().deriveFont(Font.BOLD);
	private final SearchSelectListener listener;
	private final MainWindow mw;
	
	/**
	 * create a new search result entry from the given album.
	 * the album data might be unavailable by now! (background fetch)
	 * the album must provide the "onAlbumDataChanged" event.
	 * @param mw the window this entry belongs to
	 * @param album the album to display
	 * @param listener the listener to inform when the user selected an entry
	 */
	public SearchResultEntry(MainWindow mw, LookupAlbum album, SearchSelectListener listener) {
		this.mw = mw;
		this.listener = listener;
		album.setListener(this);
	}

		
	/** get entry for one track */
	private MaxLenLabel getTrack(LookupTrack track) {
		final String str = track.getIndex() + ": " + track.getArtist() + " - " + track.getTitle();
		final MaxLenLabel lblTrack = new MaxLenLabel(40, str);
		return lblTrack;
	}

	@Override
	public void onAlbumDataChanged(LookupAlbum album) {
		synchronized (this) {
			create(album);
		}
	}
	
	/** album data has changed -> modify the GUI to match the album data */
	private void create(final LookupAlbum album) {

		// remove all previous entries
		this.removeAll();
		pnlTracks.removeAll();

		setLayout(new BorderLayout(0,0));
		setBorder(Helper.getRaisedBorder(5));
		setOpaque(true);

		// the main layout (3 parts)
		add(pnlImage, BorderLayout.LINE_END);
		add(pnlMain, BorderLayout.LINE_START);
		add(pnlTracks, BorderLayout.SOUTH);

		pnlImage.add(image);
		pnlTracks.setBackground(Color.white);
		pnlMain.setBorder(Helper.getRaisedBorder(0));
		pnlImage.setBorder(Helper.getRaisedBorder(0));
		pnlTracks.setBorder(Helper.getRaisedBorder(0));

		// set main details as artist, album, year and genre
		pnlMain.removeAll();
		pnlMain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 3, 0, 3);
		gbc.ipady = 0;
		gbc.gridx = 0; gbc.gridy = 0; pnlMain.add(new JLabel("artist:"), gbc);
		gbc.gridx = 1; gbc.gridy = 0; pnlMain.add(new MaxLenLabel(30, album.getArtist()), gbc);
		gbc.gridx = 0; gbc.gridy = 1; pnlMain.add(new JLabel("album:"), gbc);
		gbc.gridx = 1; gbc.gridy = 1; pnlMain.add(new MaxLenLabel(30, album.getAlbum()), gbc);
		gbc.gridx = 0; gbc.gridy = 2; pnlMain.add(new JLabel("year:"), gbc);
		gbc.gridx = 1; gbc.gridy = 2; pnlMain.add(new MaxLenLabel(30, album.getYear()), gbc);
		gbc.gridx = 0; gbc.gridy = 3; pnlMain.add(new JLabel("genre:"), gbc);
		gbc.gridx = 1; gbc.gridy = 3; pnlMain.add(new MaxLenLabel(30, Helper.implode(album.getGenres(), ',')), gbc);

		// get the thumb image
		BufferedImage imgThumb = album.getCoverThumb();
		if (imgThumb != null) {
			Image img2 = imgThumb.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			image.setImage(img2);
		}

		int minDist = 99999;
		JLabel minLbl = null;
		
		// add all tracks
		pnlTracks.setLayout(new GridLayout(album.getTracks().size(), 1, 0, 0));
		for (int i = 0; i < album.getTracks().size(); ++i) {

			// the track		
			final LookupTrack track = album.getTracks().get(i);

			// get matching quote to color the entries
			int dist = 0;
			dist += LevenshteinDistance.computeLevenshteinDistance(mw.getInputPanel().getModel().getArtist(), track.getArtist());
			dist += LevenshteinDistance.computeLevenshteinDistance(mw.getInputPanel().getModel().getTitle(), track.getTitle());
			int color = (int) Math.pow(dist-5, 1.4);
			if (color < 0) {color = 0;}
			if (color > 210) {color = 210;}
			Color cEntry = new Color(color, color, color);
					
			// the gui element
			final MaxLenLabel lblTrack = getTrack(track);
			lblTrack.setCursor(HAND_CURSOR);
			lblTrack.setToolTipText("copy this track's details\n" + lblTrack.getToolTipText());
			pnlTracks.add(lblTrack);
			lblTrack.setForeground(cEntry);

			// store the best-match here
			if (dist < minDist) {
				minDist = dist;
				minLbl = lblTrack;
			}
			
			lblTrack.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent arg0)	{lblTrack.setOpaque(false); lblTrack.setBackground(null);}
				@Override
				public void mousePressed(MouseEvent arg0)	{lblTrack.setOpaque(true); lblTrack.setBackground(Color.LIGHT_GRAY);}
				@Override
				public void mouseExited(MouseEvent arg0)	{}
				@Override
				public void mouseEntered(MouseEvent arg0)	{}
				@Override
				public void mouseClicked(MouseEvent arg0)	{ if (listener != null) {listener.onSelect(album, track);} }
			});

		}
		
		// make best match bold
		if (minLbl != null) {
			minLbl.setFont(minLbl.getFont().deriveFont(Font.BOLD));
		}

		this.invalidate();

	}


}
