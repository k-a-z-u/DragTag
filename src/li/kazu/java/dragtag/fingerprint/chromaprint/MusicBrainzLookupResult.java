package li.kazu.java.dragtag.fingerprint.chromaprint;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import li.kazu.java.dragtag.fingerprint.FPLookupResult;

public class MusicBrainzLookupResult implements FPLookupResult {

	private String artist;
	private String title;
	private String album;
	private String year;
	private String coverURL;
	private byte[] cover;	// LAZY LOADED
	
	/** ctor */
	protected MusicBrainzLookupResult(final String artist, final String title, final String album, final String year, final String coverURL) {
		this.artist = artist;
		this.title = title;
		this.album = album;
		this.year = year;
		this.coverURL = coverURL;
	}
	
	@Override
	public String getTitle() {return title;}
	
	@Override
	public String getArtist() {return artist;}

	@Override
	public String getAlbum() {return album;}

	@Override
	public String getYear() {return year;}

	@Override
	public String[] getGenres() {return null;}

	@Override
	public boolean hasCover() {return coverURL != null;}
	
	@Override
	public byte[] getCover() {
		if (!hasCover()) {return null;}
		if (cover == null) {cover = loadURL(coverURL);}
		return cover;
	}
	
	/** load the data behind the given URL */
	private static byte[] loadURL(final String surl) {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final URL url = new URL(surl);
			final URLConnection con = url.openConnection();
			final InputStream is = con.getInputStream();
			final byte buffer[] = new byte[4096];
			while(true) {
				int read = is.read(buffer);
				if (read < 0) {break;}
				baos.write(buffer, 0, read);
			}
			return baos.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
