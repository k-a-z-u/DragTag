package li.kazu.java.dragtag.view.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;
import li.kazu.java.dragtag.view.Helper;

/**
 * dialog asking for permission to overwrite files
 * 
 * @author seijikun
 *
 */
@SuppressWarnings("serial")
public class FileOverwriteDialog extends JDialog {
	
	/** UI ELEMENTS **/

	ImageIcon fileIcon = Helper.getIcon("file.png");
	
	//inFile
	private final JLabel 	lblInFileImage 	= new JLabel(fileIcon);
	private final JLabel	lblInFileSize	= new JLabel();
	private final JLabel	lblInFileName	= new JLabel();
	private final JLabel	lblInFilePath	= new JLabel();
	
	//outFile
	private final JLabel 	lblOutFileImage = new JLabel(fileIcon);
	private final JLabel	lblOutFileSize	= new JLabel();
	private final JLabel	lblOutFileName	= new JLabel();
	private final JLabel	lblOutFilePath	= new JLabel();
	
	//button
	private final JButton	btnOverwrite	= new JButton(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_OVERWRITE_BUTTON));
	private final JButton	btnAbort		= new JButton(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_ABORT_BUTTON));
	
	private boolean result = false;
	
	public FileOverwriteDialog(File in, File out){
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		initUi();
		
		btnOverwrite.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				result = true;
				setVisible(false);
			}
		});
		btnAbort.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				result = false;
				setVisible(false);
			}
		});
		
		//fill in informations
		lblInFileSize.setText(Helper.formatFileSize(in.length()));
		lblOutFileSize.setText(Helper.formatFileSize(out.length()));
		lblInFileName.setText(in.getName());
		lblOutFileName.setText(out.getName());
		lblInFilePath.setText(in.getParent());
		lblOutFilePath.setText(out.getParent());
		
		this.pack();
	}
	
	public boolean getResult(){
		return this.result;
	}
	
	private final void initUi(){
		setLayout(new GridBagLayout());
		GridBagConstraints pgbc = new GridBagConstraints();
		
		pgbc.insets = new Insets(5, 0, 0, 10);
		
		GridBagConstraints titleColumnConstraints = new GridBagConstraints();
		titleColumnConstraints.fill = GridBagConstraints.NONE;
		titleColumnConstraints.anchor = GridBagConstraints.WEST;
		titleColumnConstraints.gridx = 0;
		titleColumnConstraints.weightx = 0; titleColumnConstraints.weighty = 0;
		GridBagConstraints valueColumnConstraints = new GridBagConstraints();
		valueColumnConstraints.fill = GridBagConstraints.HORIZONTAL;
		valueColumnConstraints.gridx = 1;
		
		/* INPUT FILE */
		pgbc.gridy = 0; pgbc.gridx = 0;
		pgbc.weightx = 0; pgbc.weighty = 0;
		pgbc.fill = GridBagConstraints.NONE;
		add(lblInFileImage, pgbc);
		JPanel pnlInFileInfo = new JPanel();
		pnlInFileInfo.setLayout(new GridBagLayout());
		titleColumnConstraints.gridy = 0;
		pnlInFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_SIZE) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 0;
		pnlInFileInfo.add(lblInFileSize, valueColumnConstraints);
		titleColumnConstraints.gridy = 1;
		pnlInFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_FILE) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 1;
		pnlInFileInfo.add(lblInFileName, valueColumnConstraints);
		titleColumnConstraints.gridy = 2;
		pnlInFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_PATH) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 2;
		pnlInFileInfo.add(lblInFilePath, valueColumnConstraints);
		pgbc.gridy = 0; pgbc.gridx = 1;
		pgbc.weightx = 1; pgbc.weighty = 1;
		pgbc.anchor = GridBagConstraints.WEST;
		add(pnlInFileInfo, pgbc);
		
		/* OUTPUT FILE */
		pgbc.gridy = 1; pgbc.gridx = 0;
		pgbc.weightx = 0; pgbc.weighty = 0;
		pgbc.fill = GridBagConstraints.NONE;
		add(lblOutFileImage, pgbc);
		JPanel pnlOutFileInfo = new JPanel();
		pnlOutFileInfo.setLayout(new GridBagLayout());
		titleColumnConstraints.gridy = 0;
		pnlOutFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_SIZE) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 0;
		pnlOutFileInfo.add(lblOutFileSize, valueColumnConstraints);
		titleColumnConstraints.gridy = 1;
		pnlOutFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_FILE) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 1;
		pnlOutFileInfo.add(lblOutFileName, valueColumnConstraints);
		titleColumnConstraints.gridy = 2;
		pnlOutFileInfo.add(new JLabel(Language.get().get(LanguageConstant.OVERWRITE_DIALOG_FILEINFO_PATH) + ": "), titleColumnConstraints);
		valueColumnConstraints.gridy = 2;
		pnlOutFileInfo.add(lblOutFilePath, valueColumnConstraints);
		pgbc.gridy = 1; pgbc.gridx = 1;
		pgbc.weightx = 1; pgbc.weighty = 1;
		pgbc.anchor = GridBagConstraints.WEST;
		add(pnlOutFileInfo, pgbc);
		
		/* BUTTONS */
		JPanel pnlButton = new JPanel();
		pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlButton.add(btnOverwrite);
		pnlButton.add(btnAbort);
		pgbc.gridy = 2; pgbc.gridx = 1;
		pgbc.weightx = 0; pgbc.weighty = 0;
		pgbc.fill = GridBagConstraints.NONE;
		add(pnlButton, pgbc);
	}

}
