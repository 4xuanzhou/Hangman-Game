import java.util.ArrayList;
import java.util.HashMap;

public class HangmanEvil extends Hangman {

	/**
	 * represents the largest word family
	 */
	private String largestFamily;

	/**
	 * inherits the methods in Hangman class
	 */
	public HangmanEvil() {
		super();
	}

	/**
	 * the computer picks a word and gets all the words of the same length
	 * 
	 * @param fileName the name of the file to be open
	 * @return an arrayList of all the words of the length of the word picked
	 */
	public ArrayList<String> buidWordPool(String fileName) {
		String word = super.pickWord(fileName);

		ArrayList<String> dict = Hangman.getContent(fileName);
		ArrayList<String> wordPool = new ArrayList<String>();

		// all the words of the same length of the word picked should be added to the
		// pool
		for (String each : dict) {
			if (each.length() == word.length()) {
				wordPool.add(each);
			}
		}
		return wordPool;
	}

	/**
	 * separate the words in the pool into several families according to the user's
	 * guess
	 * 
	 * @param wordPool words of the same length
	 * @param letter   the input by the user
	 * @return a hashMap of all the word families
	 */
	public HashMap<String, ArrayList<String>> buildFamilies(ArrayList<String> wordPool, String letter) {
		HashMap<String, ArrayList<String>> family = new HashMap<String, ArrayList<String>>();

		ArrayList<String> familyList;

		for (String each : wordPool) {
			StringBuilder sb = new StringBuilder();

			// build keys (family names) in the HashMap
			for (int i = 0; i < each.length(); i++) {
				if (Character.toString(each.charAt(i)).equals(letter)) {
					sb.append(letter);
				} else {
					sb.append("-");
				}

			}
			String s = sb.toString();

			// if a key doesn't exist, add the key and the word to the key, else add the
			// word to the existing key
			if (!family.containsKey(s)) {
				familyList = new ArrayList<String>();
				familyList.add(each);
				family.put(s, familyList);
			} else {
				familyList = family.get(s);
				familyList.add(each);
			}

		}
		return family;
	}

	/**
	 * pick the largest word family in the families
	 * 
	 * @param family a hashMap of all the word families
	 * @return an arrayList of the words in the largest word family
	 */
	public ArrayList<String> pickLargestFamily(HashMap<String, ArrayList<String>> family) {
		// set an initial max value 1 and compare each family size with it later
		int max = 1;

		for (String key : family.keySet()) {
			if (family.get(key).size() > max) {
				max = family.get(key).size();
				this.largestFamily = key;
			}
		}
		return family.get(largestFamily);
	}

}
