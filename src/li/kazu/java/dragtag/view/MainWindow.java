package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.listeners.SearchRequestListener;


@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	/* attributes */
	private InputPanel pnlInp = new InputPanel();
	private SearchResultPanel pnlSearchResult = new SearchResultPanel(this);
	private CtrlPanel pnlCtrl = new CtrlPanel();
	private JTabbedPane tabs = new JTabbedPane();
	private SettingsPanel pnlSettings = new SettingsPanel();
	private SearchPanel pnlSearch = new SearchPanel(pnlInp);
	
	/** ctor */
	public MainWindow() {
		create();
	}
	
	/** set a new button listener */
	public void setBtnListener(SearchRequestListener btnListener) {
		pnlInp.setBtnListener(btnListener);
		pnlSearch.setButtonListener(btnListener);
	}
	
	/** enable/disable fields and buttons */
	public void setFieldsEnabled(boolean enabled) {
		pnlInp.setFieldsEnabled(enabled);
		pnlSearch.setFieldsEnabled(enabled);
	}
	
	/** create */
	private void create() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1,0,0));
				
		// create the main panel
		JPanel pnlMain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.gridheight = 1; gbc.weighty = 1.0;
		pnlMain.add(pnlInp, gbc);
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridheight = 1; gbc.weighty = 0.0;
		pnlMain.add(pnlSearch, gbc);
		gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1; gbc.gridheight = 2; gbc.weighty = 1.0;
		pnlMain.add(pnlSearchResult, gbc);
		gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.gridheight = 1; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlMain.add(pnlCtrl, gbc);
		
		// setup the tabs
		tabs.add( Language.get().get(LanguageConstant.TAB_MAIN), pnlMain );
		tabs.add( Language.get().get(LanguageConstant.TAB_SETTINGS), pnlSettings );
		
		// tab icons
		tabs.setIconAt(0, Helper.getIcon("move.png"));
		tabs.setIconAt(1, Helper.getIcon("settings.png"));
		
		// setup the whole window
		add(tabs);
		
		setIconImage(Helper.getIcon("app.png").getImage());
		setSize(800, 500);
		setVisible(true);
		
		
		
	}
	
	/** get the search panel */
	public SearchResultPanel getSearchPanel() {return pnlSearchResult;}
	
	/** get the input panel */
	public InputPanel getInputPanel() {return pnlInp;}
	
	/** get the control panel */
	public CtrlPanel getControlPanel() {return pnlCtrl;}
	
	/** get the settings panel */
	public SettingsPanel getSettingsPanel() {return pnlSettings;}
		
	
}
