package li.kazu.java.dragtag.lookup.discogs;

import li.kazu.java.dragtag.lookup.LookupProvider;
import li.kazu.java.dragtag.lookup.LookupQuery;
import li.kazu.java.dragtag.lookup.LookupResult;
import li.kazu.java.dragtag.misc.URLGenerator;

/**
 * perform lookups using discogs
 * @author kazu
 *
 */
public class DiscogsSearch implements LookupProvider {

	/** ctor */
	public DiscogsSearch() {}

	@Override
	public LookupResult search(LookupQuery query, int maxResults) {
						
		if (query.isEmpty()) {return null;}
		
		// create search URL
		URLGenerator gen = new URLGenerator("http://api.discogs.com/database/search");
		if (query.getArtist() != null)		{gen.addParam("artist",  query.getArtist());}
		if (query.getAlbumName() != null)	{gen.addParam("release_title",  query.getAlbumName());}
		//if (query.getTrackTitle() != null)	{gen.addParam("title",  query.getTrackTitle());}
		if (query.getTrackTitle() != null)	{gen.addParam("q",  query.getTrackTitle());} // "q" for title = better than "title"?
		if (query.getOther() != null)		{gen.addParam("q",  query.getOther());}		// search for anything
		
		
		// return asynchronous parser
		return new DiscogsResult(gen.getAsString(), maxResults);
		
	}
	
}
