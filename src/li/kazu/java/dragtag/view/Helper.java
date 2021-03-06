package li.kazu.java.dragtag.view;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Helper {

	private static final String[] SIZE_UNIT = { " B", " kiB", " MiB", " GiB", " TiB"};
	private static final DecimalFormat FSIZE = new DecimalFormat("0.00");
	
	public static Border getBorder(String title) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border b1 = BorderFactory.createEmptyBorder(5,5,5,5);
		Border b2 = BorderFactory.createTitledBorder(loweredetched, title);
		return BorderFactory.createCompoundBorder(b1, b2);
	}
	
	public static Border getFrameBorder() {
		Border b1 = BorderFactory.createEmptyBorder(5,5,5,5);
		Border b2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		return BorderFactory.createCompoundBorder(b2, b1);
	}
	
	public static Border getRaisedBorder(int innerPadding) {
		Border b1 = BorderFactory.createEmptyBorder(innerPadding,innerPadding,innerPadding,innerPadding);
		Border b2 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		return BorderFactory.createCompoundBorder(b2, b1);
	}
	
	/** get an outlined border */
	public static Border getLineBorder() {
		return BorderFactory.createLineBorder(Color.gray);
	}
	
	/** get the icon behind the given name */
	public static ImageIcon getIcon(String name) {
		return new ImageIcon(Helper.class.getResource( "icons/" + name ));
	}
	
	
	/** implode the given array */
	public static String implode(String[] array, char delimiter) {
		
		StringBuilder sb = new StringBuilder();
		for (String s :array) {
			sb.append(s).append(delimiter);
		}
		return (sb.length() == 0) ? ("") : (sb.substring(0, sb.length()-1));
	
	}
	
	/** create a formatted filesize string */
	public static String formatFileSize(long size){
		float tmpSize = size;
		int idx = 0;
		while(tmpSize > 1024){
			tmpSize /= 1024.0f;
			++idx;
		}
		return FSIZE.format(tmpSize) + SIZE_UNIT[idx];
	}

}
