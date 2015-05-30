package li.kazu.java.dragtag.fingerprint.chromaprint;

/**
 * calculated Chromaprint fingerprint
 * @author kazu
 *
 */
public class ChromaprintFingerprint {


	/** the calculated fingerprint string */
	String fingerprint;

	/** the song's length */
	int duration;

	/** ctor */
	ChromaprintFingerprint(final String fingerprint, final int duration) {
		this.fingerprint = fingerprint;
		this.duration = duration;
	}
	
	/** ctor */
	ChromaprintFingerprint() {;}
	
	@Override public String toString() {return duration + ":" + fingerprint;}
	
	public int getDuration() {return duration;}
	
	public String getFingerprint() {return fingerprint;}


}
