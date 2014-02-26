package li.kazu.java.dragtag.lookup;

/**
 * listen for changes of a looked-up-album
 * @author kazu
 *
 */
public interface LookupAlbumListener {

	/**
	 * called when the data of the album changed:
	 * e.g. new tracks found or thumb/cover image loaded asynchronously
	 * @see LookupAlbum
	 * @param album the album with the changed data
	 */
	public void onAlbumDataChanged(LookupAlbum album);

}
