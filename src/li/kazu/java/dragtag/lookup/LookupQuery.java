package li.kazu.java.dragtag.lookup;

/**
 * contains all parameters to search for using a specific provider
 * @author kazu
 *
 */
public class LookupQuery {

	private String artist;
	private String album;
	private String title;
	private String other;
	
	/** ctor. attributes may be null! */
	public LookupQuery(String artist, String album, String title, String other) {
		this.artist = artist;
		this.album = album;
		this.title = title;
		this.other = other;
	}
	
	/** get the artist to use for searching (or null) */
	public String getArtist() {return artist;}
	
	/** get the album to search for (or null) */
	public String getAlbumName() {return album;}
	
	/** get the track-title to search for (or null) */
	public String getTrackTitle() {return title;}

	/** search for anything unspecified */
	public String getOther() {return other;}
	
	
	/** is this query empty? */
	public boolean isEmpty() {
		return	(artist == null || artist.isEmpty()) && 
				(album == null || album.isEmpty()) &&
				(title == null || title.isEmpty()) &&
				(other == null || other.isEmpty());
	}
	
}
