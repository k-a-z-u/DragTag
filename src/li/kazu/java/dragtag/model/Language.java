package li.kazu.java.dragtag.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * multilanguage provider
 * simply uses constants from properties file
 * 
 * 
 * @author kazu
 *
 */
public class Language {

	private Properties props = new Properties();
	
	/** singleton */
	private static Language INSTANCE = null;
	
	/** get instance */
	public static Language get() {
		if (INSTANCE == null) {throw new RuntimeException("init language first");}
		return INSTANCE;
	}
	
	/** setup */
	public static void init(File f) {
		INSTANCE = new Language(f);
	}
	
	/** ctor */
	private Language(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("could not find language file: " + file.getAbsolutePath());
		}
	}
	
	/** get string for the given constant */
	public String get(LanguageConstant lc) {
		String ret = props.getProperty(lc.toString());
		return (ret != null) ? (ret) : (lc.toString());
		//return lc.toString();
	}
	
}
