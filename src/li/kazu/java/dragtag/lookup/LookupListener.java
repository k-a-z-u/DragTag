package li.kazu.java.dragtag.lookup;


/**
 * the listener to inform about lookup changes
 * 
 * @author kazu
 *
 */
public interface LookupListener {

	/**
	 * lookup data of provided LookupResult has changed -> re-render 
	 * @see LookupResult
	 * @param result the result that contains the changed data
	 */
	public void onLookupDataChange(LookupResult result);
	
	/**
	 * lookup has finished processing
	 * @param result the result that has finished processing
	 */
	public void onLookupDone(LookupResult result);
	
}
