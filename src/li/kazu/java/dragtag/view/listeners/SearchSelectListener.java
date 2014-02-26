package li.kazu.java.dragtag.view.listeners;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupTrack;

/**
 * result within the search-result has been selected
 * 
 * @author kazu
 *
 */
public interface SearchSelectListener {

	
	/** album/track combination has been selected */
	public void onSelect(LookupAlbum album, LookupTrack track);
	
}
