package li.kazu.java.dragtag.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.listeners.SearchRequestListener;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {


	final JButton btnSearchOther = new JButton();
	final JButton btnSearchArtist = new JButton();
	final JButton btnSearchAlbum = new JButton();
	final JButton btnSearchTitle = new JButton();
	final JButton btnSearchArtistTitle = new JButton();

	/** listen for search button clicks */
	private SearchRequestListener btnListener = null;

	/** ctor */
	public SearchPanel(final InputPanel pnlInput) {

		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.MAIN_PNL_SEARCH_OPTIONS) ));
		
		btnSearchArtist.setIcon(Helper.getIcon("artist.png"));
		btnSearchTitle.setIcon(Helper.getIcon("search.png"));
		btnSearchAlbum.setIcon(Helper.getIcon("album.png"));
		btnSearchArtistTitle.setIcon(Helper.getIcon("search.png"));
		btnSearchOther.setIcon(Helper.getIcon("other.png"));
		
		btnSearchArtist.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_ARTIST_TOOLTIP) );
		btnSearchTitle.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_TITLE_TOOLTIP) );
		btnSearchAlbum.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_ALBUM_TOOLTIP) );
		btnSearchArtistTitle.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_ARTIST_AND_TITLE_TOOLTIP) );
		btnSearchOther.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_OTHER_TOOLTIP) );

		
		// the search combinations
		btnSearchAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestAlbum(pnlInput.txtAlbum.getText()); }
		});
		btnSearchArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestArtist(pnlInput.txtArtist.getText()); }
		});
		btnSearchTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestTitle(pnlInput.txtTitle.getText()); }
		});
		btnSearchArtistTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestArtistTitle(pnlInput.txtArtist.getText(), pnlInput.txtTitle.getText()); }
		});
		btnSearchOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestMisc(pnlInput.txtArtist.getText(), pnlInput.txtTitle.getText(), pnlInput.txtAlbum.getText()); }
		});

		add(btnSearchArtist);
		add(btnSearchTitle);
		add(btnSearchArtistTitle);
		add(btnSearchAlbum);
		add(btnSearchOther);
		
	}

	protected void setButtonListener(SearchRequestListener btnListener) {
		this.btnListener = btnListener;
	}

	/** enable or disable all input fields and buttons */
	protected void setFieldsEnabled(boolean enabled) {
			
		btnSearchAlbum.setEnabled(enabled);
		btnSearchArtist.setEnabled(enabled);
		btnSearchArtistTitle.setEnabled(enabled);
		btnSearchTitle.setEnabled(enabled);
		btnSearchOther.setEnabled(enabled);
		
	}


}
