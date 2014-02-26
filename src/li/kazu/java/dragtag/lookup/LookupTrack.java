package li.kazu.java.dragtag.lookup;

/**
 * one Track that belongs to one Album (LookupAlbum)
 * 
 * @see LookupAlbum
 * @author kazu
 *
 */
public interface LookupTrack {

	/** get the index of this track */
	public int getIndex();
	
	/** get the artist of this track */
	public String getArtist();
	
	/** get the title of this track */
	public String getTitle();

}
