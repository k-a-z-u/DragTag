package li.kazu.java.dragtag.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.listeners.ControlActionListener;

/**
 * this panel hold the main actions to save the currently loaded
 * file, rename it (move to destination folder), close the current file,
 * etc.
 * 
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class CtrlPanel extends JPanel {

	private JLabel lblLoad;
	private JButton btnSave;
	private JButton btnRename;
	private JButton btnCloseFile;
	private JButton btnExit;
	private ControlActionListener listener;
	
	
	/** ctor */
	public CtrlPanel() {
		create();
	}
	
	/** set the listener to inform on button actions */
	public void setListener(ControlActionListener listener) {this.listener = listener;}
	
	/** create the control panel */
	private void create() {
		
		lblLoad = new JLabel(Helper.getIcon("loading.gif"));
		btnSave = new JButton( Language.get().get(LanguageConstant.MAIN_BTN_SAVE_FILE_TEXT) );
		btnRename = new JButton( Language.get().get(LanguageConstant.MAIN_BTN_RENAME_FILE_TEXT) );
		btnCloseFile = new JButton( Language.get().get(LanguageConstant.MAIN_BTN_CLOSE_FILE_TEXT) );
		btnExit = new JButton( Language.get().get(LanguageConstant.MAIN_BTN_EXIT_TEXT) );
		
		this.setLayout(new BorderLayout());
		this.setBorder(Helper.getBorder( Language.get().get(LanguageConstant.MAIN_PNL_CONTROL) ));
			
		JPanel pnlButtons = new JPanel(new FlowLayout());
		add(pnlButtons, BorderLayout.WEST);
		add(lblLoad, BorderLayout.EAST);
		lblLoad.setVisible(false);
		
		pnlButtons.add(btnSave);
		pnlButtons.add(btnRename);
		pnlButtons.add(btnCloseFile);
		pnlButtons.add(btnExit);
		
		btnSave.setIcon(Helper.getIcon("save.png"));
		btnRename.setIcon(Helper.getIcon("move.png"));
		btnCloseFile.setIcon(Helper.getIcon("close.png"));
		btnExit.setIcon(Helper.getIcon("exit.png"));
		
		btnSave.setToolTipText(Language.get().get(LanguageConstant.MAIN_BTN_SAVE_FILE_TOOLTIP));
		btnRename.setToolTipText(Language.get().get(LanguageConstant.MAIN_BTN_RENAME_FILE_TOOLTIP));
		btnCloseFile.setToolTipText(Language.get().get(LanguageConstant.MAIN_BTN_CLOSE_FILE_TOOLTIP));
		btnExit.setToolTipText(Language.get().get(LanguageConstant.MAIN_BTN_EXIT_TOOLTIP));
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listener != null) {listener.onSave();}
			}
		});
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listener != null) {listener.onMove();}
			}
		});
		btnCloseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listener != null) {listener.onCloseFile();}				
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listener != null) {listener.onExit();}
			}
		});
		
	}
	
	/** show/hide loading indicator */
	public void setLoading(boolean loading) {
		lblLoad.setVisible(loading);
	}
	
	/** set the button states */
	public void setButtonStates(boolean btnSave, boolean btnMove, boolean btnCloseFile) {
		this.btnSave.setEnabled(btnSave);
		this.btnRename.setEnabled(btnMove);
		this.btnCloseFile.setEnabled(btnCloseFile);
	}


}
