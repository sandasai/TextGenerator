package textGeneration;

import java.util.Iterator;

public class FrequencyCounter {
	private static MyHashMap<String,Markov> subStrings;
	
	/**
	 * Counts the frequency of substrings with length x appear in the input string.
	 * Used a pretest for TextGeneration.
	 * @param args  0 - input string
	 * 				1 - length of the substrings
	 */
	public static void main(String[] args) {
		subStrings = new MyHashMap<String,Markov>();
		
		try {
			String str = args[0];
			int order = Integer.parseInt(args[1]);
			for (int i = 0; i <= str.length() - order; i++) {
				String substr = str.substring(i, i + order);
				if (!subStrings.containsKey(substr)) {
					subStrings.put(substr, new Markov(substr));
				}
				subStrings.get(substr).add();
			}
			System.out.println(distinctKeys());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Two arguments are required for this program - an input string and an integer");
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
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
