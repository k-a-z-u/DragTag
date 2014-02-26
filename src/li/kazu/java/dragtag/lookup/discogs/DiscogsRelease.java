package li.kazu.java.dragtag.lookup.discogs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupAlbumListener;
import li.kazu.java.dragtag.lookup.LookupTrack;
import li.kazu.java.dragtag.misc.Helper;

public class DiscogsRelease implements LookupAlbum {

	private JSONObject result;
	private String artist;
	private String title;
	private String year;
	private ArrayList<String> genres = new ArrayList<String>();
	private ArrayList<LookupTrack> tracks = new ArrayList<LookupTrack>();
	private String urlCover;
	private LookupAlbumListener listener;
	
	private BufferedImage imgThumb = null;
	private byte[] imgCover = null;
	
	/** create discogs release by url */
	public DiscogsRelease(String url, DiscogsResult res) {
		parse(url, res);
	}

	/** set the listener to inform when this dataset changes (e.g. thumb-image loaded) */
	public void setListener(LookupAlbumListener listener) {
		this.listener = listener;
		if (listener != null) {listener.onAlbumDataChanged(this);}
	}

	private void parse(String url, final DiscogsResult res) {

		// get root element
		String data = Helper.getString(url);
		result = (JSONObject) JSONValue.parse(data);

		// sanity check
		if (result == null) {return;}
		
		// get artist
		if (result.containsKey("artists")) {
			JSONArray artists = (JSONArray)result.get("artists");
			JSONObject first = (JSONObject) artists.get(0);
			artist = first.get("name").toString();
		}

		// get title
		if (result.containsKey("title")) {
			title = result.get("title").toString();
		}

		System.out.println(result);
		
		// get genres
		if (result.containsKey("genres")) {
			JSONArray jsonGenres = (JSONArray)result.get("genres");
			for (int i = 0; i < jsonGenres.size(); ++i) {genres.add(jsonGenres.get(i).toString());}	
		}
		// get styles (= quasi genres)
		if (result.containsKey("styles")) {
			JSONArray jsonStyles = (JSONArray)result.get("styles");
			for (int i = 0; i < jsonStyles.size(); ++i) {genres.add(jsonStyles.get(i).toString());}	
		}

		// get the year
		if (result.containsKey("year")) {
			year = result.get("year").toString();
		}

		// get the big image
		if (result.containsKey("images")) {
			JSONArray jsonImages = (JSONArray) result.get("images");
			System.out.println(jsonImages);
			urlCover = ((JSONObject) jsonImages.get(0)).get("uri").toString();
		}

		// get the thumb image
		if (result.containsKey("thumb")) {
			final String urlThumb = result.get("thumb").toString();

			// load image in background
			new Thread() {
				public void run() {
										
					imgThumb = Helper.getImage( fixImgURLs(urlThumb) );
					if (listener != null) {listener.onAlbumDataChanged(DiscogsRelease.this);}
					
				}
			}.start();

		}

		// tracks
		if (result.containsKey("tracklist")) {
			
			// get all tracks
			JSONArray jsonTracks = (JSONArray) result.get("tracklist");
			for (int i = 0; i < jsonTracks.size(); ++i) {

				// get the track
				JSONObject jsonTrack = (JSONObject) jsonTracks.get(i);
				String trackArtist = null;

				// check whether the artist of the track is different from the album
				if (jsonTrack.containsKey("artists")) {
					JSONArray artists = (JSONArray) jsonTrack.get("artists");
					JSONObject first = (JSONObject) artists.get(0);
					trackArtist = first.get("name").toString();
				} else {
					trackArtist = getArtist();
				}

				// create track and add to list
				DiscogsTrack t = new DiscogsTrack(trackArtist, jsonTrack.get("title").toString(), i+1);
				System.out.println(t);
				tracks.add(t);

			}

		}
		
		

	}
	
	/** modify image URLs to prevent autehtication */
	private String fixImgURLs(String url) {
		
		// fix:
		// without this we would need to authenticate with the REST service
		return url.replaceAll("api.discogs.com", "s.pixogs.com");
		
	}
	

	@Override
	public String getArtist() {return artist;}

	@Override
	public String getAlbum() {return title;}

	@Override
	public String[] getGenres() {return genres.toArray(new String[0]);}

	@Override
	public String getYear() {return year;}


	@Override
	public BufferedImage getCoverThumb() {return imgThumb;}

	@Override
	public byte[] getCover() {
		// lazy loading
		System.out.println("cover URL: " + urlCover);
		if (urlCover == null) {return null;}
		if (imgCover == null) {imgCover = Helper.getBytes( fixImgURLs(urlCover) );}
		return imgCover;
	}

	@Override
	public List<LookupTrack> getTracks() {return tracks;}

	@Override
	public String toString() {return getArtist() + ":" + getAlbum() + ":" + getGenres()[0] + ":" + getYear();}

}
