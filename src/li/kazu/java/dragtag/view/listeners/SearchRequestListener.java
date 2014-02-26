package li.kazu.java.dragtag.view.listeners;

/**
 * listen for search button clicks
 * @author kazu
 *
 */
public interface SearchRequestListener {

	/** request to search for artist */
	public void onSearchRequestArtist(String artist);
	
	/** request to search for title*/
	public void onSearchRequestTitle(String title);

	/** request to search for artist/title */
	public void onSearchRequestArtistTitle(String artist, String title);
	
	/** request to search for album */
	public void onSearchRequestAlbum(String album);
	
	/** request to search for "misc" */
	public void onSearchRequestMisc(String artist, String title, String album);
	
}
