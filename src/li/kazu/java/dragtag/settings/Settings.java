package li.kazu.java.dragtag.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Settings {

	/* attributes */
	private final Properties props = new Properties();
	private File file = null;
	private static Settings INSTANCE = null;
	
	/** set the file to use for settings */
	public static void init(File file, boolean createIfNeeded) {
		INSTANCE = new Settings(file, createIfNeeded);		
	}

	/** singleton access */
	public static Settings get()  {
		if (INSTANCE == null) {throw new SettingsException("init() settings first!");}
		return INSTANCE;
	}
	
	/** hidden ctor */
	private Settings(File file, boolean createIfNeeded) {
		this.file = file;
		load(createIfNeeded);
	}


	
	/** load settings */
	private void load(boolean createIfNeeded) {
		try {
			if (!file.exists() && !createIfNeeded) {throw new SettingsException("settings file not found: " + file.getAbsolutePath());}
			if (!file.exists() && createIfNeeded) {file.createNewFile();}
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
			fis.close();
		} catch (Exception e) {e.printStackTrace();}
	}

	/** save settings */
	private void save() {
		try {
			FileOutputStream out = new FileOutputStream(file);
			props.store(out, "");
			out.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/** get string by key */
	public String getString(String key) {return props.getProperty(key);}
	
	/** get string by key. throws SettingsException if null */
	public String getStringEx(String key) {
		if (!props.containsKey(key)) {throw new SettingsException("key not found: " + key);}
		return props.getProperty(key);
	}
	
	/** set string for key and save */
	public void setString(String key, String value) {props.setProperty(key, value); save();}
	
	
	
	/** get file by key */
	public File getFile(String key) {return new File(getString(key));}
	
	/** get file by key. throws SettingsException if null */
	public File getFileEx(String key) {return new File(getStringEx(key));}
	
	/** set file for key and save */
	public void setFile(String key, File file) {setString(key, file.getAbsolutePath());}
	
	

	/** get int by key */
	public int getInt(String key) {return Integer.parseInt(getString(key));}
	
	/** get int by key. throws SettingsException if null */
	public int getIntEx(String key) {return Integer.parseInt(getStringEx(key));}
	
	/** set int for key and save */
	public void setInt(String key, int val) {setString(key, val + "");}
	
	
	/** get boolean by key */
	public boolean getBool(String key){return Boolean.parseBoolean(getString(key));}
	
	/** get boolean by key. throws SettingsException if null */
	public boolean getBoolEx(String key){return Boolean.parseBoolean(getStringEx(key));}
	
	/** set boolean for key and save */
	public void setBool(String key, boolean val){setString(key, Boolean.toString(val));}
	

}
