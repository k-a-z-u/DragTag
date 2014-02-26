package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.settings.SettingsModel;

@SuppressWarnings("serial")
public class SettingsSearchPanel extends JPanel {

	protected final JTextField txtSearchLimit = new JTextField();
	private SettingsModel settings;
	
	
	/** ctor */
	public SettingsSearchPanel(SettingsPanel parent) {
		
		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.SETTINGS_PNL_SEARCH) ));
	
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel lblSearchLimit = new JLabel( Language.get().get(LanguageConstant.SETTINGS_LBL_SEARCH_LIMIT) );
		lblSearchLimit.setToolTipText( Language.get().get(LanguageConstant.SETTINGS_LBL_SEARCH_LIMIT_DESC) );
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = SettingsPanel.insets;
		
		gbc.gridx = 0; gbc.gridy = 0;
		add(lblSearchLimit, gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		add(txtSearchLimit, gbc);
		
		txtSearchLimit.addFocusListener(parent.focusListener);
		
	}
	
	protected void setSettings(SettingsModel settings) {
		this.settings = settings;
		updateValues();
	}
	
	protected void updateValues() {
		if (settings == null) {return;}
		try {
			txtSearchLimit.setText(settings.getSearchLimit()+"");
		} catch (Exception e) {;}
	}
	
}
