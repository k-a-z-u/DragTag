package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.settings.SettingsModel;

@SuppressWarnings("serial")
public class SettingsOutputPanel extends JPanel {

	private final JButton btnRoot = new JButton();
	protected final JTextField txtRootFolder = new JTextField();
	protected final JTextField txtRenamePattern = new JTextField();
	
	private final GridBagConstraints gbc = new GridBagConstraints();
	
	private SettingsModel settings = null;
	
	/** ctor */
	public SettingsOutputPanel(SettingsPanel parent) {
		
		setLayout(new GridBagLayout());
		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.SETTINGS_PNL_OUTPUT) ));
		
		JLabel lblOutputFolder = new JLabel( Language.get().get(LanguageConstant.SETTINGS_LBL_OUTPUT_PATH) );
		JLabel lblOutputFormat = new JLabel( Language.get().get(LanguageConstant.SETTINGS_LBL_OUTPUT_FORMAT) );
		
		lblOutputFolder.setToolTipText( Language.get().get(LanguageConstant.SETTINGS_LBL_OUTPUT_PATH_DESC) );
		lblOutputFormat.setToolTipText( Language.get().get(LanguageConstant.SETTINGS_LBL_OUTPUT_FORMAT_DESC) );
		
		txtRootFolder.setEnabled(false);
		
		btnRoot.setIcon(Helper.getIcon("folder.png"));
		btnRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRootFolder();
			}
		});
		
		gbc.insets = SettingsPanel.insets;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridy = 0;
		add(lblOutputFolder, gbc);
		gbc.gridy = 1;
		add(lblOutputFormat, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(txtRootFolder, gbc);
		gbc.gridx = 2; gbc.weightx = 0;
		add(btnRoot, gbc);
		gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		add(txtRenamePattern, gbc);
		
		txtRenamePattern.addFocusListener(parent.focusListener);
		
	}
	
	protected void setSettings(SettingsModel settings) {
		this.settings = settings;
		this.updateValues();
	}
	
	/** display current values */
	protected void updateValues() {
		if (settings == null) {return;}
		try {
			txtRenamePattern.setText(settings.getRenamePattern());
			txtRootFolder.setText(settings.getOuputFolder().getAbsolutePath());
		} catch (Exception e) {;}
		
	}
	
	private void setRootFolder() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) {return;}
		File root = fc.getSelectedFile();
		settings.setOuputFolder(root);
		txtRootFolder.setText(root.getAbsolutePath());
		System.out.println("OK");
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
