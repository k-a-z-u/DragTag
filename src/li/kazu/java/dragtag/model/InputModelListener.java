package li.kazu.java.dragtag.model;

/**
 * listen for changes within the input model which is mapped to 
 * the gui
 * 
 * @author kazu
 *
 */
public interface InputModelListener {

	/** input data changed */
	public void onInputChanged(InputModel model);
	
}
