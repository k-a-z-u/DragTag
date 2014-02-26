package li.kazu.java.dragtag.settings;

/**
 * error within settings
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class SettingsException extends RuntimeException {

	public SettingsException(String str) {
		super(str);
	}
	
	public SettingsException(String str, Throwable t) {
		super(str, t);
	}

}
