package li.kazu.java.dragtag.fingerprint.chromaprint;

import java.io.File;
import java.io.InputStream;

import li.kazu.java.dragtag.fingerprint.Fingerprinter;


/**
 * fingerprint audio files using chromaprint.
 * needs a local installation called "fpcalc"
 * @author kazu
 *
 */
public class FingerprinterChromaprint implements Fingerprinter {

	
	/** calculate the fingerprint for the given file. needs "fpcalc" to be within the system's path! */
	public static ChromaprintFingerprint getFingerprint(final File f) throws Exception {
	
		final String cmd[] = {"fpcalc", f.getAbsolutePath()};
		final Process p = Runtime.getRuntime().exec(cmd);
		final InputStream is = p.getInputStream();
		final byte buf[] = new byte[32*1024];
		
		// read response
		int offset = 0;
		while(true) {
			int read = is.read(buf, offset, buf.length-offset);
			if (read < 0) {break;}
			offset += read;
		}
		
		// output
		final ChromaprintFingerprint fp = new ChromaprintFingerprint();
		
		// split
		final String res = new String(buf, 0, offset);
		for (final String line : res.split("\n")) {
			final String arr[] = line.split("=", 2);
			final String key = arr[0];
			final String val = arr[1];
			if ("DURATION".equals(key)) {fp.duration = Integer.parseInt(val);}
			if ("FINGERPRINT".equals(key)) {fp.fingerprint = val;}
		}
		
		// done
		return fp;
		
	}

}
