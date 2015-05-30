package li.kazu.java.dragtag.fingerprint;

import java.awt.image.BufferedImage;


/**
 * interface for fingerprint lookup results.
 * 
 * a little different than the other lookups..
 * dunno if this is a good decision..
 * 
 * @author kazu
 *
 */
public interface FPLookupResult {

	/** get the title of this entry */
	public String getTitle();

	/** get the artist of this entry */
	public String getArtist();
		
	/** get the song's album */
	public String getAlbum();
	
	/** get the year the song was produced */
	public String getYear();
	
	/** get the genre of the song */
	public String[] getGenres();
	
	
	/** does this result provide a cover image? */
	public boolean hasCover();
	
	/** big cover image (lazy loading!) */
	public byte[] getCover();
	
}
