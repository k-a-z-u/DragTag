package li.kazu.java.dragtag.misc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * small helper class to create URLs with
 * additional query parameters.
 * 
 * @author kazu
 *
 */
public class URLGenerator {

	private final String base;
	private HashMap<String, String> params = new HashMap<String, String>();
	private String charset = "UTF-8";
	
	/** ctor */
	public URLGenerator(String base) {
		this.base = base;
	}
	
	/** add one parameter to the url */
	public void addParam(String key, String val) {
		params.put(key, val);
	}
	
	/** encode url content */
	private String encode(String val) {
		try {return URLEncoder.encode(val, charset);} catch (UnsupportedEncodingException e) {return "";}
	}
	
	
	/** get final url as string */
	public String getAsString() {
		StringBuilder sb = new StringBuilder(base);
		boolean first = true;
		for (String key : params.keySet()) {
			String val = encode(params.get(key));
			sb.append(first ? "?" : "&");
			sb.append(key).append("=").append(val);
			first = false;
		}
		return sb.toString();
	}

}
