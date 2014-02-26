package li.kazu.java.dragtag.controller;

import java.io.File;

import li.kazu.java.dragtag.model.InputModel;
import li.kazu.java.dragtag.settings.SettingsModel;


/**
 * some helper methods for saving files
 * @author kazu
 *
 */
public class SaveHelper {

	/** get the target file-name using the provided input model */
	public static File getSaveFile(InputModel mdl) throws Exception {

		// get target folder from settings
		File root = SettingsModel.get().getOuputFolder();
		
		// get filename format from settings
		String rep = SettingsModel.get().getRenamePattern();

		// perform replacements
		rep = rep.replaceAll("%artist%", mdl.getArtist());
		rep = rep.replaceAll("%title%", mdl.getTitle());
		rep = rep.replaceAll("%genre%", mdl.getGenre());
		rep = rep.replaceAll("%year%", mdl.getYear());
		rep = rep.replaceAll("%album%", mdl.getAlbum());
		rep = rep + ".mp3";

		// make safe
		rep = rep.replaceAll("[\"\\!\\?\\:\\;]", "");

		// get output file
		return new File(root, rep);

	}
	
}
