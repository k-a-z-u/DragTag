package li.kazu.java.dragtag.lookup.discogs;

import java.util.ArrayList;
import java.util.List;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupListener;
import li.kazu.java.dragtag.lookup.LookupResult;
import li.kazu.java.dragtag.misc.Helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DiscogsResult implements LookupResult {

	/* store all found entries here */
	private final ArrayList<LookupAlbum> results = new ArrayList<LookupAlbum>();
	protected LookupListener listener = null;
	private final int MAX;

	/** create asynchronous parser */
	public DiscogsResult (String url, int maxResults) {
		System.out.println("searching: " + url);
		this.MAX = maxResults;
		parse(url);
	}

	/** load and parse URL */
	private void parse(final String url) {

		new Thread() {
			@Override
			public void run() {

				// load data from url
				String jsonData =  Helper.getString(url);

				// get the root-object and process all contained "results"
				JSONObject jsonObj =  (JSONObject) JSONValue.parse(jsonData);
				JSONArray jsonResults = (JSONArray) jsonObj.get("results");

				System.out.println(jsonResults);
				System.out.println(jsonResults.size());
				
				// get number of entries to fetch
				int max = (jsonResults.size() > MAX) ? (MAX) : (jsonResults.size());
				int found = 0;
				
				// get the entries
				for (int i = 0; i < jsonResults.size(); ++i) {

					// get the result
					JSONObject jsonEntry = (JSONObject) jsonResults.get(i);

					// skip, if type is not "release"
					if (!jsonEntry.get("type").toString().equals("release")) {continue;}
		
					// as these results do not provide many information,
					// we have to fetch detailed information about each "release" using
					// the provided URI
					String detailsURL = jsonEntry.get("resource_url").toString();
					DiscogsRelease dr = new DiscogsRelease(detailsURL, DiscogsResult.this);			
					results.add(dr);

					// inform listener
					if (listener != null) {listener.onLookupDataChange(DiscogsResult.this);}

					// LIMIT
					if (++found >= max) {break;}
					
				}
				
				// lookup complete
				if (listener != null) {listener.onLookupDone(DiscogsResult.this);}

			}
		}.start();

	}

	@Override
	public List<LookupAlbum> getEntries() {return results;}

	@Override
	public void setLookupListener(LookupListener listener) {
		this.listener = listener;
		listener.onLookupDataChange(this);
	}

}
