package li.kazu.java.dragtag.settings;

import java.io.File;
import java.util.regex.Pattern;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;

public class SettingsModel {

	private static final SettingsModel INSTANCE = new SettingsModel();
	
	public static SettingsModel get() {
		return INSTANCE;
	}
	
	private SettingsModel() {
		;
	}
	
	public String getRenamePattern() throws Exception {
		try {
			return Settings.get().getStringEx(SettingsConstants.RENAME_PATTERN.toString());
		} catch (Exception e) {
			throw new Exception(Language.get().get(LanguageConstant.ERR_MISSING_FILENAME_FORMAT));
		}
	}
	
	public void setRenamePattern(String str) throws Exception {
		Pattern p = Pattern.compile("'([%artist%|%title%|%album| /-])+'");
		if (!p.matcher(str).matches()) {
			throw new Exception(Language.get().get(LanguageConstant.ERR_INVALID_PATTERN));
		}
		Settings.get().setString(SettingsConstants.RENAME_PATTERN.toString(), str);
	}
	
	
	public File getOuputFolder() throws Exception {
		try {
			return Settings.get().getFileEx(SettingsConstants.ROOT_FOLDER.toString());
		} catch (Exception e) {
			throw new Exception(Language.get().get(LanguageConstant.ERR_MISSING_OUTPUT_PATH));
		}	
	}
	
	public void setOuputFolder(File file) {
		Settings.get().setString(SettingsConstants.ROOT_FOLDER.toString(), file.getAbsolutePath());
	}
	
	
	public int getSearchLimit(){
		return Settings.get().getIntEx(SettingsConstants.SEARCH_LIMIT.toString());
	}
	
	public void setSearchLimit(int i) {
		Settings.get().setString(SettingsConstants.SEARCH_LIMIT.toString(), i + "");
	}
	
	
}
