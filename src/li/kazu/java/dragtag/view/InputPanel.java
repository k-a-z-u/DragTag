package li.kazu.java.dragtag.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import li.kazu.java.dragtag.model.InputModel;
import li.kazu.java.dragtag.model.InputModelListener;
import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.controls.ImagePanel;
import li.kazu.java.dragtag.view.listeners.ControlActionListener;
import li.kazu.java.dragtag.view.listeners.SearchRequestListener;


/**
 * the panel holding the input files for the currently loaded
 * song. artist, title, genre, cover, ...
 * 
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class InputPanel extends JPanel implements InputModelListener {

	/* attributes */
	private JPanel pnlContent = new JPanel();
	
	private final GridBagConstraints gbc = new GridBagConstraints();
	
	protected final JTextField txtArtist = new JTextField();
	protected final JTextField txtTitle = new JTextField();
	protected final JTextField txtAlbum = new JTextField();
	protected final JTextField txtYear = new JTextField();
	protected final JComboBox<String> cmbGenre = new JComboBox<String>();
	
	private final JButton btnSearchArtist = new JButton();
	private final JButton btnSearchAlbum = new JButton();
	private final JButton btnSearchTitle = new JButton();
	
	private final Insets gridInsets = new Insets(2, 2, 2, 2);
	private final ImagePanel image = new ImagePanel();
	protected ControlActionListener inputListener = null;
	
	private InputModel model = null;
	
	private SearchRequestListener btnListener = null;
	
	/** ctor */
	public InputPanel() {
		create();
	}
	
	/** set the listener for button clicks */
	public void setBtnListener(SearchRequestListener btnListener) {
		this.btnListener = btnListener;
	}
	
	/** set the model that holds and stores the input fields */
	public void setModel(InputModel model) {this.model = model; model.addInputModeListener(this);}
	
	/** get the input model */
	public InputModel getModel() {return this.model;}
	
	private void create() {
		
		setLayout(new BorderLayout());
		add(pnlContent, BorderLayout.NORTH);
		//scrl.setBorder(null);
		//add(scrl);
		
		// tab order
		IndexedFocusTraversalPolicy pol = new IndexedFocusTraversalPolicy();
		pol.addIndexedComponent(txtArtist);
		pol.addIndexedComponent(txtTitle);
		pol.addIndexedComponent(txtAlbum);
		pol.addIndexedComponent(txtYear);
		pol.addIndexedComponent(cmbGenre);
		setFocusTraversalPolicy(pol);
		setFocusTraversalPolicyProvider(true);
		
		setBorder(Helper.getBorder( Language.get().get(LanguageConstant.MAIN_PNL_TAG_EDIT) ));
	
		pnlContent.setLayout(new GridBagLayout());
		image.setBorder(Helper.getLineBorder());
		cmbGenre.setEditable(false);
		
		gbc.anchor = GridBagConstraints.EAST;
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_ARTIST) ),	0, 0, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_TITLE) ),	0, 1, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_ALBUM) ),	0, 2, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_YEAR) ),	0, 3, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_GENRE) ),	0, 4, 0);
		add(new JLabel( Language.get().get(LanguageConstant.MAIN_LBL_COVER) ),	0, 5, 0);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		add(txtArtist,	1, 0, 5, 1.0f);
		add(txtTitle,	1, 1, 5, 1.0f);
		add(txtAlbum,	1, 2, 5, 1.0f);
		add(txtYear,	1, 3, 5, 1.0f);
		add(cmbGenre,	1, 4, 5, 1.0f);
		
		add(btnSearchArtist,	2, 0, 0);
		add(btnSearchTitle,		2, 1 ,0);
		add(btnSearchAlbum, 	2, 2, 0);

		btnSearchArtist.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_ARTIST_TOOLTIP) );
		btnSearchTitle.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_TITLE_TOOLTIP) );
		btnSearchAlbum.setToolTipText( Language.get().get(LanguageConstant.MAIN_BTN_SEARCH_ALBUM_TOOLTIP) );
		
		JPanel pnlImage = new JPanel();
		pnlImage.add(image);
		add(pnlImage, 1, 5, 0);
		
		btnSearchArtist.setIcon(Helper.getIcon("artist.png"));
		btnSearchTitle.setIcon(Helper.getIcon("search.png"));
		btnSearchAlbum.setIcon(Helper.getIcon("album.png"));
		
		// the search combinations
		btnSearchAlbum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestAlbum(txtAlbum.getText()); }
		});
		btnSearchArtist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestArtist(txtArtist.getText()); }
		});
		btnSearchTitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { btnListener.onSearchRequestTitle(txtTitle.getText()); }
		});
		
		// model changes
		txtAlbum.addKeyListener(keyListener);
		txtArtist.addKeyListener(keyListener);
		txtTitle.addKeyListener(keyListener);
		//cmbGenre.getEditor().getEditorComponent().addKeyListener(keyListener);
		txtYear.addKeyListener(keyListener);
		cmbGenre.addItemListener(itemListener);
		
	}
	
	
	private ItemListener itemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() != ItemEvent.SELECTED) {return;}
			updateModel();
		}
	};
	
	/** key-pressed -> update model */
	private KeyListener keyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {updateModel();}
		@Override
		public void keyPressed(KeyEvent e) {}
	};
	
	
	/** insert current values into the model */
	private void updateModel() {
		model.setArtist(txtArtist.getText());
		model.setTitle(txtTitle.getText());
		model.setAlbum(txtAlbum.getText());
		model.setYear(txtYear.getText());
		if (cmbGenre.getSelectedItem() != null) {
			model.setGenre(cmbGenre.getSelectedItem().toString());
		}
		model.setChanged(true);
	}
	
	private void add(JComponent c, int x, int y, int pad) {
		add(c,x,y,pad,0);
	}
	
	private void add(JComponent c, int x, int y, int pad, float weightX) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.ipadx = pad;
		gbc.ipady = pad;
		gbc.insets = gridInsets;
		gbc.weightx = weightX;
		pnlContent.add(c, gbc);
	}
	

	
	/** set the listener to inform about input events */
	public void setInputListener(ControlActionListener l) {inputListener = l;}


	@Override
	public void onInputChanged(InputModel model) {
		
		System.out.println("changed");
		
		// update only if realy changed
		// (used to fix this issue: user types -> model changed -> model updates textbox -> cursor repositioned at the end of text)
		if (!model.getArtist().equals(txtArtist.getText()))	{txtArtist.setText(model.getArtist());}
		if (!model.getTitle().equals(txtTitle.getText()))	{txtTitle.setText(model.getTitle());}
		if (!model.getAlbum().equals(txtAlbum.getText()))	{txtAlbum.setText(model.getAlbum());}
		if (!model.getYear().equals(txtYear.getText()))		{txtYear.setText(model.getYear());}
		
		ComboBoxModel<String> mod = new DefaultComboBoxModel<String>(model.getGenres());
		cmbGenre.removeItemListener(itemListener);		// prevent stack overflow
		cmbGenre.setModel(mod);
		cmbGenre.setSelectedItem(model.getGenre());
		cmbGenre.addItemListener(itemListener);		// prevent stack overflow
		
		
		
		if (model.getCoverImage() != null) {
			Image img2 = model.getCoverImage().getScaledInstance(92, 92, Image.SCALE_SMOOTH);
			image.setImage(img2);
		} else {
			image.setImage(null);
		}
		
	}
	
	/** enable or disable all input fields and buttons */
	protected void setFieldsEnabled(boolean enabled) {
	
		txtArtist.setEnabled(enabled);
		txtTitle.setEnabled(enabled);
		txtAlbum.setEnabled(enabled);
		cmbGenre.setEnabled(enabled);
		txtYear.setEnabled(enabled);
		
		btnSearchAlbum.setEnabled(enabled);
		btnSearchArtist.setEnabled(enabled);
		//btnSearchArtistTitle.setEnabled(enabled);
		btnSearchTitle.setEnabled(enabled);
		//btnSearchOther.setEnabled(enabled);
		
	}
	

	
}
