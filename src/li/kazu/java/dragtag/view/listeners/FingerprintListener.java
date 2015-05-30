package li.kazu.java.dragtag.view.listeners;


public interface FingerprintListener {

	/** use the given fingerprint lookup as song details */
	public void onUseLastFingerprint();
	
	/** trigger a lookup */
	public void onPerformLookup();
	
}
