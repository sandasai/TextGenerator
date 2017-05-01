package textGeneration;

import java.util.Iterator;

public class SuffixCounter {
	private static MyHashMap<String,Markov> subStrings;
	
	/**
	 * A program that counts the suffix characters that follow substrings in an input string.
	 * Used a pretest for TextGeneration
	 * @param args  0 - input string
	 * 				1 - the length of the substring
	 */
	public static void main(String[] args) {
		subStrings = new MyHashMap<String,Markov>();
		
		String str = args[0];
		int order = Integer.parseInt(args[1]);
		for (int i = 0; i <= str.length() - order - 1; i++) {
			String substr = str.substring(i, i + order);
			if (!subStrings.containsKey(substr)) {
				subStrings.put(substr, new Markov(substr));
			}
			subStrings.get(substr).add(str.charAt(i + order));
		}
		System.out.println(distinctKeys());
	}
	
	public static String distinctKeys() {
		String str = subStrings.size() + " distinct keys\n";
		Iterator<String> keys = subStrings.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			str += subStrings.get(key) + "\n";
		}
		return str;
	}
}
