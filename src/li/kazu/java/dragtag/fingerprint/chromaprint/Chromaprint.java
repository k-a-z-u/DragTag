package li.kazu.java.dragtag.fingerprint.chromaprint;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * perform song lookups based on chromaprint audio fingerprinting
 * and MusicBrainz AcoustID lookup
 * 
 * @author kazu
 *
 */
public class Chromaprint {

	/** the client's ID. default from the chromaprint website */
	private static final String CLIENT_ID = "ULjKruIh";
	
	/** details lookup URL */
	private static final String URL_LOOKUP = "http://api.acoustid.org/v2/lookup";
	
	/** lookup song details for the given fingerprint */
	public static MusicBrainzLookupResult get(final ChromaprintFingerprint fp) throws Exception {
		
		final String surl = 
				URL_LOOKUP +
				"?client=" + CLIENT_ID +
				"&meta=" + "recordings+releases+compress" +
				"&duration=" + fp.getDuration() + 
				"&fingerprint=" + fp.getFingerprint();
		
		System.out.println(surl);
		final URL url = new URL(surl);
		final URLConnection con = url.openConnection();
		final JSONObject json = (JSONObject) JSONValue.parse(new InputStreamReader(con.getInputStream()));
		
		// get the first result
		final JSONArray jResults = (JSONArray) json.get("results");
		final JSONObject jResult = (JSONObject) jResults.get(0);
		
		// get the first recording
		final JSONArray jRecordings = (JSONArray) jResult.get("recordings");
		final JSONObject jRecording = (JSONObject) jRecordings.get(0);
		
		// get the artist
		final String artist = getArtistStr(jRecording);
		
		// get the title
		final String title = getTitle(jRecording);
		
		// get the release's title
		final String release = getReleaseTitle(jRecording);
		
		// get the release's year
		final String year = getReleaseYear(jRecording);
		
		// get its releaseID
		final String releaseID = getReleaseID(jRecording);
		
		// get the cover image url behind the release
		final String coverURL = getCoverURL(releaseID);
		
		// done
		return new MusicBrainzLookupResult(artist, title, release, year, coverURL);
		
	}
		
	/** try to get the cover for the given releaseID */
	private static String getCoverURL(final String releaseID) {
		
		try {
			
			if (releaseID == null) {return null;}
			
			final String surl = "http://coverartarchive.org/release/" + releaseID;
			final URL url = new URL(surl);
			final URLConnection con = url.openConnection();
			final JSONObject json = (JSONObject) JSONValue.parse(new InputStreamReader(con.getInputStream()));
		
			final JSONArray images = (JSONArray) json.get("images");
			final JSONObject image = (JSONObject) images.get(0);
			final String imageURL = (String) image.get("image");
			
			return imageURL;
			
		} catch (final Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
		
	}
	

//	/** get the image behind the given URL */
//	private static BufferedImage loadImage(final String surl) throws Exception {
//		final URL url = new URL(surl);
//		final URLConnection con = url.openConnection();
//		return ImageIO.read(con.getInputStream());
//	}

	

	
	/** try to get (the first) release ID from the lookup result */
	private static String getReleaseID(final JSONObject recording) {
		try {
			final JSONArray releases = (JSONArray) recording.get("releases");
			final JSONObject release = (JSONObject) releases.get(0);
			return (String) release.get("id");
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** get the recording's release's title */
	private static String getReleaseTitle(final JSONObject recording) {
		try {
			final JSONArray releases = (JSONArray) recording.get("releases");
			final JSONObject release = (JSONObject) releases.get(0);
			return (String) release.get("title");
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** get the recording's release's year */
	private static String getReleaseYear(final JSONObject recording) {
		try {
			final JSONArray releases = (JSONArray) recording.get("releases");
			final JSONObject release = (JSONObject) releases.get(0);
			final JSONObject date = (JSONObject) release.get("date");
			return date.get("year").toString();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** get the recording's title */
	private static String getTitle(final JSONObject recording) {
		try {
			return (String) recording.get("title");
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** join all artists depending on their joinphrase (see json) */
	private static String getArtistStr(final JSONObject jRecording) {
		try {
			final JSONArray artists = (JSONArray) jRecording.get("artists");
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < artists.size(); ++i) {
				final JSONObject artist = (JSONObject) artists.get(i);
				sb.append(artist.get("name"));
				if (artist.get("joinphrase") != null) {
					sb.append(artist.get("joinphrase"));
				}
			}
			return sb.toString();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
