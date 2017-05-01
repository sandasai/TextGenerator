package textGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Markov {
	
	private String substring;
	private int count;
	private TreeMap<Character,Integer> suffixes;
	
	/**
	 * Adds creates a new Markov with the given substring and count initialized to zero
	 * @param substring
	 */
	Markov(String substring) {
		count = 0;
		this.substring = substring;
		this.suffixes = new TreeMap<Character,Integer>();
	}
	
	/**
	 * Increment frequency
	 */
	public void add() {
		count++;
	}
	
	/**
	 * Increment frequency with information of its following character
	 * @param c - character that follows the substring
	 */
	public void add(char c) {
		count++;
		if (suffixes.containsKey(c))
			suffixes.put(c, suffixes.get(c) + 1);
		else 
			suffixes.put(c, 1);

	}
	public String toString() {
		String str = count + " " + substring;
		Set<Map.Entry<Character,Integer>> suffixFrequencies = suffixes.entrySet();
		if (!suffixes.isEmpty()) {
			str += " ~";
			for (Map.Entry<Character,Integer> suffixFreq : suffixFrequencies) {
				str += " " + suffixFreq.getValue() + " " + suffixFreq.getKey(); 
			}
		}
		return str;
	}
	
	/**
	 * Returns the next predicted character based of the substring associated with this Markov object.
	 * The predicted character is generated from the frequencies of seen suffix characters
	 * @return a pseudo-random character from previous suffix characters
	 */
	public char random() {
		//create List of characters
		List<Character> characters = new ArrayList<Character>(); 
		Set<Map.Entry<Character,Integer>> suffixFrequencies = suffixes.entrySet();
		for (Map.Entry<Character,Integer> suffixFreq : suffixFrequencies) {
			//add character multiple times equals to its' frequency
			for (int i = 0; i < suffixFreq.getValue(); i++) {
				characters.add(suffixFreq.getKey());
			}
		} 
		//use RNG to pick a random character from the list
		Random r = new Random();
		return characters.get(r.nextInt(characters.size()));
	}
}
