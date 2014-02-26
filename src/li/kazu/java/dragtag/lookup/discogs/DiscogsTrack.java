package li.kazu.java.dragtag.lookup.discogs;

import li.kazu.java.dragtag.lookup.LookupTrack;

public class DiscogsTrack implements LookupTrack {

	private String artist;
	private String title;
	private int index;
	
	public DiscogsTrack(String artist, String title, int index) {
		this.artist = artist;
		this.title = title;
		this.index = index;
		
		// some cleanups
		this.artist = this.artist.replaceAll("\\([0-9]\\)", "");		// remove e.g. (1) within artist name
		this.artist = this.artist.trim();
		
	}
	
	@Override
	public int getIndex() {return index;}
	
	@Override
	public String getArtist() {return artist;}
	
	@Override
	public String getTitle() {return title;}
	
	
	@Override
	public String toString() {return "\t" + index + ") " + getArtist() + " : " + getTitle();}
	
}
