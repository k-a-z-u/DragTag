package li.kazu.java.dragtag.lookup;

/**
 * a provider that allows us to perform album lookups
 * 
 * @author kazu
 *
 */
public interface LookupProvider {

	/**
	 * search for the provided attributes
	 * @see LookupQuery
	 * @see LookupResult
	 * @param query the elements to search for
	 * @param maxResults the results limit
	 * @return the result of the query or NULL on error!
	 */
	public LookupResult search(LookupQuery query, int maxResults);
	
}
