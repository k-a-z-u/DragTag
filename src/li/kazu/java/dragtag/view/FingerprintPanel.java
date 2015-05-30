package li.kazu.java.dragtag.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import li.kazu.java.dragtag.fingerprint.FPLookupResult;
import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.listeners.FingerprintListener;

@SuppressWarnings("serial")
public class FingerprintPanel extends JPanel {

	private final JLabel lblArtist = new JLabel();
	private final JLabel lblTitle = new JLabel();
	private final JLabel lblAlbum = new JLabel();
	private final JLabel lblYear = new JLabel();
	private final JLabel lblCover = new JLabel();

	private final Insets gridInsets = new Insets(2, 2, 2, 2);

	private final JButton btnSearch = new JButton(Helper.getIcon("fingerprint.png"));
	private final JButton btnUse = new JButton(Helper.getIcon("apply.png"));
	
	private FingerprintListener listener;
	
	/** ctor */
	public FingerprintPanel() {
		
		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.MAIN_PNL_FINGERPRINTING) ));
		setLayout(new GridBagLayout());
		
		gbc.anchor = GridBagConstraints.WEST;
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_ARTIST) ),	0, 0, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_TITLE) ),	0, 1, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_ALBUM) ),	0, 2, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_YEAR) ),	0, 3, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_GENRE) ),	0, 4, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_COVER) ),	0, 5, 0);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(lblArtist, 1, 0, 0, 1.0f);
		add(lblTitle, 1, 1, 0, 1.0f);
		add(lblAlbum, 1, 2, 0, 1.0f);
		add(lblYear, 1, 3, 0, 1.0f);
		add(lblCover, 1, 5, 0, 1.0f);
		
		add(btnSearch, 0, 6, 0);
		add(btnUse, 1, 6, 0);
		
		btnSearch.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listener.onPerformLookup();				
			}
		});
		btnUse.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listener.onUseLastFingerprint();				
			}
		});
		
		btnUse.setEnabled(false);
		
	}
	
	/** reset all values */
	public void reset() {
		lblAlbum.setText("");
		lblArtist.setText("");
		lblTitle.setText("");
		lblCover.setText("");
		lblYear.setText("");
		btnUse.setEnabled(false);
	}
	
	/** show the search button? */
	public void showSearchBtn(final boolean show) {btnSearch.setEnabled(show);}
	
	/** show the apply button? */
	public void showApplyBtn(final boolean show) {btnUse.setEnabled(show);}
	
	
	/** set the fingerprint listener */
	public void setListener(final FingerprintListener l) {
		this.listener = l;
	}
	
	/** show the given result in the UI */
	public void showResult(final FPLookupResult res) {
		lblAlbum.setText(res.getAlbum());
		lblTitle.setText(res.getTitle());
		lblArtist.setText(res.getArtist());
		lblYear.setText(res.getYear());
		lblCover.setText(res.hasCover() ? "yes" : "no");
	}
	
	private final GridBagConstraints gbc = new GridBagConstraints();
	private void add(JComponent c, int x, int y, int pad) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.ipadx = pad;
		gbc.ipady = pad;
		gbc.insets = gridInsets;
		add(c, gbc);
	}
	
	private void add(JComponent c, int x, int y, int pad, float weightX) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.ipadx = pad;
		gbc.ipady = pad;
		gbc.insets = gridInsets;
		gbc.weightx = weightX;
		add(c, gbc);
	}
	
}
