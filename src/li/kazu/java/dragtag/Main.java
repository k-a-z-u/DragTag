package li.kazu.java.dragtag;

import java.io.File;

import javax.swing.UIManager;

import li.kazu.java.dragtag.controller.Controller;
import li.kazu.java.dragtag.fingerprint.chromaprint.Chromaprint;
import li.kazu.java.dragtag.fingerprint.chromaprint.FingerprinterChromaprint;
import li.kazu.java.dragtag.lookup.discogs.DiscogsSearch;
import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.settings.Settings;
import li.kazu.java.dragtag.settings.SettingsConstants;
import li.kazu.java.dragtag.view.MainWindow;

/**
 * everything starts here
 * @author kazu
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
//		FingerprinterChromaprint fc = new FingerprinterChromaprint();
//		FingerprinterChromaprint.Fingerprint fp = fc.getFingerprint(new File("/mnt/ssss/mp3s/spotify/3.mp3"));
//		Chromaprint.get(fp);
//		System.exit(0);
		
		Settings.init(new File("settings.conf"), true);
		Language.init(new File("language.ini"));
		
		// initialize default settings
		if (Settings.get().getString(SettingsConstants.SEARCH_LIMIT.toString()) == null) {
			Settings.get().setString(SettingsConstants.SEARCH_LIMIT.toString(), "3");
		}
		if (Settings.get().getString(SettingsConstants.RENAME_PATTERN.toString()) == null) {
			Settings.get().setString(SettingsConstants.RENAME_PATTERN.toString(), "%artist%/%artist% - %title%");
		}
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
		Controller c = new Controller(new MainWindow());
		c.addProvider(new DiscogsSearch());
		
	}

}
