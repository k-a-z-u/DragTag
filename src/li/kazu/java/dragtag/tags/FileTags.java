package li.kazu.java.dragtag.tags;

import java.awt.image.BufferedImage;
import java.io.File;

import li.kazu.java.audio.id3.ID3;


/**
 * get ID3 tags from file using KID3 library.
 * 
 * if the file does not provide any ID3 tags (v1/v2)
 * we try to parse the filename which usualy has a
 * format like "artist - name.mp3"
 * 
 * @author kazu
 *
 */
public class FileTags implements ID3Tags {

	private String nameOnly;
	private ID3 id3;
	
	/** ctor */
	public FileTags(String fileStr) {
		File file = new File(fileStr);
		id3 = new ID3(file);
		nameOnly = file.getName();
		nameOnly = nameOnly.substring(0, nameOnly.lastIndexOf('.'));
	}
	

	@Override
	public String getArtist() {
		if (id3.isOK() && !id3.getArtist().isEmpty()) {return id3.getArtist();}
		if (nameOnly.indexOf('-') != -1) {return nameOnly.substring(0, nameOnly.indexOf('-') - 1);}
		return "";
	}

	@Override
	public String getTitle() {
		if (id3.isOK() && !id3.getTitle().isEmpty()) {return id3.getTitle();}
		if (nameOnly.indexOf('-') != -1) {return nameOnly.substring(nameOnly.indexOf('-') + 2);}
		return "";
	}

	@Override
	public String getAlbum() {
		if (id3.isOK()) {return id3.getAlbum();}
		return "";
	}

	@Override
	public String getGenre() {
		if (id3.isOK()) {return id3.getGenre();}
		return "";
	}

	@Override
	public String getYear() {
		if (id3.isOK()) {return id3.getYear();}
		return "";
	}
	
	@Override
	public BufferedImage getCoverImage() {
		if (id3.isOK() && id3.getCover() != null) {return id3.getCover().getImage();}
		return null;
	}
	
	@Override
	public byte[] getCover() {
		if (id3.isOK() && id3.getCover() != null) {return id3.getCover().getImageBytes();}
		return null;
	}

	
}
