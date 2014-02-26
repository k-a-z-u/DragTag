package li.kazu.java.dragtag.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import li.kazu.java.dragtag.lookup.LookupAlbum;
import li.kazu.java.dragtag.lookup.LookupListener;
import li.kazu.java.dragtag.lookup.LookupProvider;
import li.kazu.java.dragtag.lookup.LookupQuery;
import li.kazu.java.dragtag.lookup.LookupResult;
import li.kazu.java.dragtag.lookup.LookupTrack;
import li.kazu.java.dragtag.model.InputModel;
import li.kazu.java.dragtag.model.InputModelListener;
import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.settings.Settings;
import li.kazu.java.dragtag.settings.SettingsConstants;
import li.kazu.java.dragtag.settings.SettingsModel;
import li.kazu.java.dragtag.tags.FileTags;
import li.kazu.java.dragtag.view.MainWindow;
import li.kazu.java.dragtag.view.listeners.ControlActionListener;
import li.kazu.java.dragtag.view.listeners.SearchRequestListener;
import li.kazu.java.dragtag.view.listeners.SearchSelectListener;

/**
 * the main controller which handles the interaction between
 * GUI and all models like the mp3 file or external search providers
 * 
 * @author kazu
 *
 */
public class Controller implements ControlActionListener, SearchSelectListener, InputModelListener, SearchRequestListener, LookupListener {

	private final MainWindow window;
	private final ArrayList<LookupProvider> provider = new ArrayList<LookupProvider>();
	private int activeProvider = 0;

	private File currentFile;
	private boolean isSaving = false;
	private final InputModel inputModel = new InputModel();


	public Controller(MainWindow window) {
		this.window = window;
		addDragDrop();
		updateTitleAndButtons();
		window.setBtnListener(this);
	}

	/** attach a new lookup provider */
	public void addProvider(LookupProvider prov) {
		provider.add(prov);
	}



	private void addDragDrop() {

		// what to do on DnD	
		DropTargetListener dtl = new DropTargetListener() {

			public void drop(DropTargetDropEvent e) {

				try {

					// get the transferable and all available interpretations
					Transferable tr = e.getTransferable();
					DataFlavor[] flavors = tr.getTransferDataFlavors();


					// check if one interpration is of the type "file"
					for (int i = 0; i < flavors.length; i++)

						// windows
						if (flavors[i].isFlavorJavaFileListType()) {

							// accapt this drop
							e.acceptDrop (e.getDropAction());

							// get all dropped files
							List<?> files = (List<?>) tr.getTransferData(flavors[i]);

							// load first file
							loadFile(files.get(0).toString());

							// everything fine
							e.dropComplete(true);
							return;

						} else if (flavors[i].getRepresentationClass() == java.lang.String.class) {

							// accapt this drop
							e.acceptDrop (e.getDropAction());

							// get all dropped files
							String str = tr.getTransferData(flavors[i]).toString();

							// only accept "file://"
							if (!str.startsWith("file://")) {continue;}
							
							// split files
							String[] files = str.split("\r\n");

							// load first file
							String file = files[0];
							file = file.substring(7);
							file = URLDecoder.decode(file, "UTF-8");
							loadFile(file);

							// everything fine
							e.dropComplete(true);
							return;

						}




				} catch (Throwable t) { t.printStackTrace(); }

				// error
				e.rejectDrop();

			}

			public void dropActionChanged(DropTargetDragEvent arg0) {}			
			public void dragOver(DropTargetDragEvent arg0) {}
			public void dragExit(DropTargetEvent arg0) {}
			public void dragEnter(DropTargetDragEvent arg0) {}

		};

		// create drop target
		DropTarget dt = new DropTarget();

		// set the listener
		try {dt.addDropTargetListener(dtl);} catch (TooManyListenersException e) {throw new RuntimeException(e);}

		// attach the listener
		window.setDropTarget(dt);

		// listen for save/move button
		window.getControlPanel().setListener(this);

		// register the data model
		window.getInputPanel().setModel(inputModel);

		// register for model changes (change indicator)
		inputModel.addInputModeListener(this);

		// listen for search-selects
		window.getSearchPanel().setSearchSelectListener(this);

		// listen for iunput changes 
		window.getInputPanel().setInputListener(this);

		// attach settings to gui
		window.getSettingsPanel().setSettings(SettingsModel.get());

	}



	/** load one file */
	private void loadFile(final String file) {

		// sanity check
		if (file != null) {
			File f = new File(file);
			if (!f.exists()) {
				showError("File does not exist:\n" + f.getAbsolutePath());
				return;
			}
		}
		
		new Thread() {
			public void run() {

				window.getControlPanel().setLoading(true);
			
				if (file == null) {
					window.getControlPanel().setLoading(true);
					currentFile = null;
					inputModel.clear();
					inputModel.setChanged(false);
					
				} else {
					
					currentFile = new File(file);					
					FileTags tags = new FileTags(file);
					inputModel.clear();
					inputModel.setTags(tags);					// this will inform the gui
					inputModel.setChanged(false);				// the model has the same content as the mp3 -> not changed
			
				}

				window.getControlPanel().setLoading(false);

			}
		}.start();

	}

	@Override
	public void onSave() {

		if (currentFile == null) {return;}

		isSaving = true;
		updateTitleAndButtons();
		window.getControlPanel().setLoading(true);

		new Thread() {
			public void run() {

				try {

					AudioFile f = AudioFileIO.read(currentFile);
					Tag tag = new ID3v23Tag();
					f.setTag(tag);
					tag.setField(FieldKey.ARTIST, inputModel.getArtist());
					tag.setField(FieldKey.TITLE, inputModel.getTitle());
					tag.setField(FieldKey.ALBUM, inputModel.getAlbum());

					if (!inputModel.getYear().isEmpty()) {tag.setField(FieldKey.YEAR, inputModel.getYear());}
					tag.setField(FieldKey.GENRE, inputModel.getGenre());

					if (inputModel.getCover() != null) {
						final int TYPE_FRONT_COVER = 3;
						Artwork a = ArtworkFactory.getNew();
						a.setBinaryData(inputModel.getCover());
						a.setPictureType(TYPE_FRONT_COVER);
						//a.setMimeType("JPG");
						tag.setField(a);
					}

					f.commit();

					// mark model as saved
					inputModel.setChanged(false);	
					System.out.println("saved");	

				} catch (Exception e) {e.printStackTrace(); showError(e);}

				isSaving = false;
				updateTitleAndButtons();
				window.getControlPanel().setLoading(false);

			}
		}.start();

	}

	/* on errors */
	public static void showError(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "an error occured", JOptionPane.ERROR_MESSAGE);
	}

	/* on errors */
	private void showError(String str) {
		JOptionPane.showMessageDialog(null, str, "an error occured", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onMove() {
		
		try {
		File out = SaveHelper.getSaveFile(inputModel);
		
		if (out.exists()) {
			String s = "output file already exists!\n" + out.getAbsolutePath() + "\n";
			s += "existing file's size: " + out.length() + "\n";
			s += "new file's size: " + currentFile.length();
			JOptionPane.showMessageDialog(null, s); return;
		}
		
		// create parent folders if needed
		out.getParentFile().mkdirs();
		
		// rename
		currentFile.renameTo(out);
		
		// update
		loadFile(out.getAbsolutePath());
		
		} catch (Exception e) {
			showError(e);
		}
		
	}

	public void onExit() {
		System.exit(0);
	}

	@Override
	public void onSelect(final LookupAlbum album, final LookupTrack track) {

		// set tags and cover from search result (may take some time to load the cover)
		new Thread() {
			public void run() {
				window.getControlPanel().setLoading(true);
				inputModel.setFromSearch(album, track);
				window.getControlPanel().setLoading(false);
			}
		}.start();

	}

	@Override
	public void onInputChanged(InputModel model) {
		updateTitleAndButtons();
	}

	@Override
	public void onCloseFile() {
		loadFile(null);
	}

	/** update window's title and button states */
	private void updateTitleAndButtons() {

		String title = "FastTag";
		if (currentFile != null) {
			title += " - " + currentFile.getAbsolutePath();
		} else {
			title += " - " + Language.get().get(LanguageConstant.TITLE_DRAG);
		}
		title += inputModel.isChanged() ? "*" : "";
		window.setTitle(title);

		boolean btnSave =		!isSaving && currentFile != null && inputModel.isChanged();
		boolean btnMove =		!isSaving && currentFile != null && !inputModel.isChanged();
		boolean btnCloseFile =	!isSaving && currentFile != null;
		boolean pnlInput =		!isSaving && currentFile != null;

		window.getControlPanel().setButtonStates(btnSave, btnMove, btnCloseFile);
		window.setFieldsEnabled(pnlInput);

	}


	
	private void startSearch(LookupQuery query) {
		window.setFieldsEnabled(false);
		window.getSearchPanel().onLookupDataChange(null);
		LookupProvider p = provider.get(activeProvider);
		try {
			LookupResult res = p.search(query, Settings.get().getIntEx(SettingsConstants.SEARCH_LIMIT.toString()));
			if (res != null) {
				res.setLookupListener(this);
			}
		} catch (Exception e) {
			showError(Language.get().get(LanguageConstant.ERR_MISSING_SEARCH_LIMIT));
		}
	}

	@Override
	public void onSearchRequestArtist(String artist) {
		startSearch( new LookupQuery(artist, null, null, null) );
	}

	@Override
	public void onSearchRequestTitle(String title) {
		startSearch( new LookupQuery(null, null, title, null) );		
	}

	@Override
	public void onSearchRequestArtistTitle(String artist, String title) {
		startSearch( new LookupQuery(artist, null, title, null) );
	}

	@Override
	public void onSearchRequestAlbum(String album) {
		startSearch( new LookupQuery(null, album, null, null) );
	}

	@Override
	public void onSearchRequestMisc(String artist, String title, String album) {
		startSearch( new LookupQuery(null, null, null, "\"" + artist + "\" \"" + title + "\"") );
	}

	
	@Override
	public void onLookupDataChange(LookupResult result) {
		window.getSearchPanel().onLookupDataChange(result);		
	}

	@Override
	public void onLookupDone(LookupResult result) {
		window.getSearchPanel().onLookupDone(result);
		window.setFieldsEnabled(true);
	}


}
