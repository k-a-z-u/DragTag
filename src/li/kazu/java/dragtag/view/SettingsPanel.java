package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;

import li.kazu.java.dragtag.controller.Controller;
import li.kazu.java.dragtag.settings.SettingsModel;

/**
 * settings configuration
 * 
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {

	/** save settings on lost focus */
	protected FocusListener focusListener = new FocusListener() {
		public void focusLost(FocusEvent e) {saveSettings();}
		public void focusGained(FocusEvent e) {}
	};
	
	protected static final Insets insets = new Insets(5, 5, 5, 5);
	
	private final GridBagConstraints gbc = new GridBagConstraints();

	private SettingsModel settings = null;
	private SettingsOutputPanel pnlOutput = new SettingsOutputPanel(this);
	private SettingsSearchPanel pnlSearch = new SettingsSearchPanel(this);
	private SettingsFileRulesPanel pnlFileRules = new SettingsFileRulesPanel(this);
	
	/** ctor */
	public SettingsPanel() {
		create();
	}
	
	/** create */
	private void create() {
		
		setLayout(new GridBagLayout());
		
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0; gbc.gridy = 0;
		add(pnlOutput, gbc);
		
		gbc.gridx = 1;
		add(pnlSearch, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1;
		add(pnlFileRules, gbc);
				
	}
	
	private void saveSettings() {
		if (settings == null) {return;}
		System.out.println("saving");
		//settings.setString(SettingsConstants.RENAME_PATTERN.toString(), pnlOutput.txtSubFolder.getText());
		//settings.setString(SettingsConstants.SEARCH_LIMIT.toString(), pnlSearch.txtSearchLimit.getText());
		try {
			settings.setRenamePattern( pnlOutput.txtRenamePattern.getText() );
			settings.setSearchLimit( Integer.parseInt(pnlSearch.txtSearchLimit.getText()) );
		} catch (Exception e) {
			
			//FIXME: nawt so MVC acshuly...
			Controller.showError(e);
			pnlOutput.updateValues();
			pnlSearch.updateValues();
			pnlFileRules.updateValues();
			
		}
	}
	
	/** set the settings object to work with */
	public void setSettings(SettingsModel settings) {
		this.settings = settings;
		pnlOutput.setSettings(settings);
		pnlSearch.setSettings(settings);
		pnlFileRules.setSettings(settings);
	}
	
	


	
}
