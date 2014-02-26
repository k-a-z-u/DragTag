package li.kazu.java.dragtag.misc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

/**
 * some additional helper methods
 * @author kazu
 *
 */
public class Helper {

	
	/** read URLs content and return it as string */
	public static String getString(String urlStr) {
		try {
			URL u = new URL(urlStr);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			byte[] buf = new byte[4096];
			StringBuilder sb = new StringBuilder();
			while(true) {
				int read = is.read(buf);
				if (read == -1) {break;}
				sb.append(new String(buf, 0, read));
			}
			is.close();
			return sb.toString();
		} catch (Exception e) {e.printStackTrace();}
		return "";
	}
	
	/** read url into byte[] */
	public static byte[] getBytes(String urlStr) {
		try {
			URL u = new URL(urlStr);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			byte[] buf = new byte[4096];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(true) {
				int read = is.read(buf);
				if (read == -1) {break;}
				baos.write(buf, 0, read);
			}
			is.close();
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	/** load image from url-string */
	public static BufferedImage getImage(String urlStr) {
		try {
					
			URL u = new URL(urlStr);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			BufferedImage img = ImageIO.read(is);
			is.close();
			
			return img;
			
		} catch(Exception e) {e.printStackTrace();}
		return null;
	}
	
}

