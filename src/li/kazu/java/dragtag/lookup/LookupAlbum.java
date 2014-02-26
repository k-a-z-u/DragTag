package li.kazu.java.dragtag.lookup;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * one album that is result of a lookup
 * @author kazu
 *
 */
public interface LookupAlbum {

	/** get the artist of this entry */
	public String getArtist();
		
	/** get the song's album */
	public String getAlbum();
	
	/** get the year the song was produced */
	public String getYear();
	
	/** get the genre of the song */
	public String[] getGenres();
	
	/** small cover image */
	public BufferedImage getCoverThumb();
	
	/** big cover image (LAZY LOADING!) */
	public byte[] getCover();

	/**
	 * get all tracks on this album
	 * @see LookupTrack
	 * @return all tracks on this album
	 */
	public List<LookupTrack> getTracks();
	
	
	/**
	 * set the listener to inform when the data of the album changes
	 * e.g. new tracks found or cover-image loaded in background
	 * @see LookupAlbumListener
	 * @param listener the listener to inform
	 */
	public void setListener(LookupAlbumListener listener);
	
}
