package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.settings.SettingsModel;
import li.kazu.java.dragtag.settings.SettingsOverwriteRule;

@SuppressWarnings("serial")
public class SettingsFileRulesPanel extends JPanel {

	protected final JCheckBox checkOverwrite = new JCheckBox();
	protected final JComboBox<SettingsOverwriteRule> comboOverwriteRule = new JComboBox<SettingsOverwriteRule>(SettingsOverwriteRule.getList());
	
	private final GridBagConstraints gbc = new GridBagConstraints();
	
	private SettingsModel settings = null;
	
	/** ctor */
	public SettingsFileRulesPanel(SettingsPanel parent) {
		
		setLayout(new GridBagLayout());
		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.SETTINGS_PNL_FILERULES) ));
		
		JLabel lblOverwrite = new JLabel( Language.get().get(LanguageConstant.SETTINGS_LBL_FILERULES_OVERWRITE) );
		JLabel lblOverwriteRule = new JLabel( Language.get().get(LanguageConstant.SETTINGS_LBL_FILERULES_OVERWRITE_RULE) );
		
		lblOverwrite.setToolTipText( Language.get().get(LanguageConstant.SETTINGS_LBL_FILERULES_OVERWRITE) );
		lblOverwriteRule.setToolTipText( Language.get().get(LanguageConstant.SETTINGS_LBL_FILERULES_OVERWRITE_RULE) );
		
		comboOverwriteRule.setEnabled(false);
		
		gbc.insets = SettingsPanel.insets;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridy = 0;
		add(lblOverwrite, gbc);
		gbc.gridy = 1;
		add(lblOverwriteRule, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(checkOverwrite, gbc);
		gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		add(comboOverwriteRule, gbc);
		
		checkOverwrite.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				boolean state = checkOverwrite.isSelected();
				comboOverwriteRule.setEnabled(state);
				settings.setOverwriteEnabled(state);
			}
		});
		comboOverwriteRule.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				settings.setOverwriteRule((SettingsOverwriteRule)comboOverwriteRule.getSelectedItem());
			}
		});
		
	}
	
	protected void setSettings(SettingsModel settings) {
		this.settings = settings;
		this.updateValues();
	}
	
	/** display current values */
	protected void updateValues() {
		if (settings == null) {return;}
		try {
			checkOverwrite.setSelected(settings.getOverwriteEnabled());
			if(checkOverwrite.isSelected()){
				//settings.getOverwriteRule()
				comboOverwriteRule.getModel().setSelectedItem(SettingsOverwriteRule.getRule(settings.getOverwriteRule()));
			}
		} catch (Exception e) {;}
		
	}
	
//	private void add(JComponent c, int x, int y, int pad) {
//		add(c,x,y,pad,0);
//	}
//	
//	private void add(JComponent c, int x, int y, int pad, float weightX) {
//		gbc.gridx = x;
//		gbc.gridy = y;
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.ipadx = pad;
//		gbc.ipady = pad;
//		gbc.insets = insets;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.weightx = weightX;
//		add(c, gbc);
//	}
	
}
