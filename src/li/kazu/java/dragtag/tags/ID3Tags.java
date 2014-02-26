package li.kazu.java.dragtag.tags;

import java.awt.image.BufferedImage;


/**
 * interface for (reading) ID3 tags from a file
 * 
 * @author kazu
 *
 */
public interface ID3Tags {

	/** get the song's artist */
	public String getArtist();
	
	/** get the song's title */
	public String getTitle();
	
	/** get the song's album */
	public String getAlbum();

	/** get the song's genre as string */
	public String getGenre();
	
	/** get the song's publishing year */
	public String getYear();

	/** get the song's cover as buffered image (if any) */
	public BufferedImage getCoverImage();
	
	/** get the song's cover as byte[] (if any) */
	public byte[] getCover();
	
}
