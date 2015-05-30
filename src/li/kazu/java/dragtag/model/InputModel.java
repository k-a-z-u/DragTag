package li.kazu.java.dragtag.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import li.kazu.java.dragtag.fingerprint.FPLookupResult;
import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupTrack;
import li.kazu.java.dragtag.tags.ID3Tags;

/**
 * the model that is bound to the GUI.
 * 
 * connects GUI input to the MP3's data
 * and search-results
 * 
 * @author kazu
 *
 */
public class InputModel implements ID3Tags {

	private String artist;
	private String title;
	private String album;
	private String genre;
	private String[] genres = new String[] {"", "Electronic", "Rock", "Pop"};
	private String year;
	private byte[] cover;

	private boolean changed = false;
	private final ArrayList<InputModelListener> listeners = new ArrayList<InputModelListener>();

	/** ctor */
	public InputModel() {;}

	/** attach listener */
	public void addInputModeListener(InputModelListener listener) {listeners.add(listener);}


	/** update from file-tags */
	public void setTags(ID3Tags tags) {
		this.artist = tags.getArtist();
		this.title = tags.getTitle();
		this.album = tags.getAlbum();
		this.year = tags.getYear();
		this.genre = tags.getGenre();
		if (!tags.getGenre().isEmpty()) {
			this.genres = new String[] {tags.getGenre()};	// else: use default genre list for selection
		}
		this.cover = tags.getCover();
		setChanged(true);
	}

	/** set result from lookup */
	public void setFromSearch(LookupAlbum album, LookupTrack track) {
		
		// load new data from search-result
		if (album.getAlbum() != null)	{this.album = album.getAlbum();}
		if (album.getArtist() != null)	{this.artist = album.getArtist();}
		if (album.getYear() != null)	{this.year = album.getYear();}
		if (album.getGenres() != null)	{this.genres = album.getGenres(); this.genre = album.getGenres()[0];}
		if (track.getArtist() != null)	{this.artist = track.getArtist();}
		if (track.getTitle() != null)	{this.title = track.getTitle();}
		if (album.getCover() != null)	{this.cover = album.getCover();}
		setChanged(true);
		
	}

	/** set result from lookup */
	public void setFromSearch(final FPLookupResult res) {
		
		// load new data from search-result
		if (res.getAlbum() != null)		{this.album = res.getAlbum();}
		if (res.getYear() != null)		{this.year = res.getYear();}
		if (res.getGenres() != null)	{this.genres = res.getGenres(); this.genre = res.getGenres()[0];}
		if (res.getArtist() != null)	{this.artist = res.getArtist();}
		if (res.getTitle() != null)		{this.title = res.getTitle();}
		if (res.getCover() != null)		{this.cover = res.getCover();}
		setChanged(true);
		
	}
	
	public void setArtist(String artist)	{this.artist = artist;}
	public void setTitle(String title)		{this.title = title;}
	public void setAlbum(String album)		{this.album = album;}
	public void setYear(String year)		{this.year = year;}
	public void setGenre(String genre)		{this.genre = genre;}
	public void setCover(byte[] cover)		{this.cover = cover;}


	/** inform listeners */
	public void setChanged(boolean changed) {
		this.changed = changed;
		for (InputModelListener listener : listeners) {listener.onInputChanged(this);}
		System.out.println(toString());
	}

	/** get the changed-state */
	public boolean isChanged() {return changed;}


	@Override
	public String getArtist() {return artist;}

	@Override
	public String getTitle() {return title;}

	@Override
	public String getAlbum() {return album;}

	public String[] getGenres() {return genres;}
	
	@Override
	public String getGenre() {return genre;}

	@Override
	public String getYear() {return year;}

	@Override
	public byte[] getCover() {return cover;}

	@Override
	public BufferedImage getCoverImage() {
		if (cover == null) {return null;}
		try {
			return ImageIO.read(new ByteArrayInputStream(cover));
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}

	@Override
	public String toString() {
		String str = artist + ":" + title + ":" + album + ":" + year + ":";
		str += (genres != null && genres.length > 0) ? (genres[0]) : ("");
		return str;
	}

	/** clear all values */
	public void clear() {
		album = "";
		artist = "";
		title = "";
		year = "";
		genres = new String[0];
		cover = null;
	}

}
