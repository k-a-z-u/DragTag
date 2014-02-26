package li.kazu.java.dragtag.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupListener;
import li.kazu.java.dragtag.lookup.LookupResult;
import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.listeners.SearchSelectListener;

/**
 * the panel containing the complete search result
 * 
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class SearchResultPanel extends JPanel implements LookupListener {

	private final GridBagConstraints gbc = new GridBagConstraints();
	private final JPanel pnlEntries = new JPanel();
	private final JScrollPane scrl = new JScrollPane(pnlEntries);
	private SearchSelectListener listener;
	private final MainWindow mw;
	private final Insets entryInsets = new Insets(5, 5, 5, 5);
	
	public SearchResultPanel(MainWindow mw) {
		this.mw = mw;
		create();
	}
	
	/** set the listener to inform when a search-result is selected */
	public void setSearchSelectListener(SearchSelectListener listener) {this.listener = listener;}

	private void create() {
		this.setLayout(new GridLayout(1,1));
		this.setBorder(Helper.getBorder( Language.get().get(LanguageConstant.MAIN_PNL_SEARCH_RESULT) ));
		this.add(scrl);
		pnlEntries.setLayout(new GridBagLayout());
		//pnlEntries.setBackground(Color.gray);
		scrl.setBorder(null);
	}

	private void add(JComponent c, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.ipadx = 10;
		gbc.ipady = 10;
		gbc.insets = entryInsets;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		pnlEntries.add(c, gbc);
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400,0);
	}

	@Override
	public void onLookupDataChange(LookupResult result) {

		//pnlEntries.setVisible(false);
		pnlEntries.removeAll();

		if (result != null) {

			mw.getControlPanel().setLoading(true);
			
			for (int i = 0; i < result.getEntries().size(); ++i) {
				LookupAlbum album = result.getEntries().get(i);
				JPanel pnl = new SearchResultEntry(mw, album, listener);
				add(pnl, 0, i);
			}

		} else {
			//this.invalidate();
			//this.repaint();
		}

		//this.revalidate();
		
		this.invalidate();

	}

	@Override
	public void onLookupDone(LookupResult result) {
		mw.getControlPanel().setLoading(false);		
	}

}
