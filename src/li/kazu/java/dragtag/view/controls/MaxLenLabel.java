package li.kazu.java.dragtag.view.controls;

import javax.swing.JLabel;

/**
 * label with maximum text-length limit
 * @author kazu
 *
 */
@SuppressWarnings("serial")
public class MaxLenLabel extends JLabel {

	private int maxLen = 0;
	
	/** ctor */
	public MaxLenLabel(int maxLen) {
		this.maxLen = maxLen;
	}
	
	/** ctor */
	public MaxLenLabel(int maxLen, String str) {
		this.maxLen = maxLen;
		setText(str);
	}
	
	@Override
	public void setText(String text) {
		this.setToolTipText(text);
		if (text.length() > maxLen) {
			text = text.substring(0, maxLen) + "..";
		}
		super.setText(text);
	}
	
}
