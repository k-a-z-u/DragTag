package li.kazu.java.dragtag.misc;

/**
 * 
 * @author http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
 * slightly modified to provide better results for song searches
 */
public class LevenshteinDistance {

	private static float minimum(float a, float b, float c) {
		return Math.min(Math.min(a, b), c);
	}

	/** compare the two given strings */
	public static int computeLevenshteinDistance(String str1, String str2) {
		
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();
		
		float[][] distance = new float[str1.length() + 1][str2.length() + 1];
		
		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				distance[i][j] = minimum(
						distance[i - 1][j] + 1.0f,
						distance[i][j - 1] + 1.0f,
						distance[i - 1][j - 1]*2
								+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? -0.05f
										: 2.5f));
			}
		}

		int ret =  (int) distance[str1.length()][str2.length()];
		if (ret < 0) {ret = 0;}
		return ret;
		
	}

}
