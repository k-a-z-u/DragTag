package li.kazu.java.dragtag.lookup;

import java.util.List;

/**
 * lookup-result containing multiple entries
 * @author kazu
 *
 */
public interface LookupResult {

	/**
	 * get all entries within this result
	 * @see LookupAlbum
	 * @return all found albums
	 */
	public List<LookupAlbum> getEntries();
	
	/**
	 * set the listener to inform when lookup-result changes (loading in background)
	 * @see LookupListener
	 * @param listener the listener to inform
	 */
	public void setLookupListener(LookupListener listener);
	
	
}
