package textGeneration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyHashMap<K,V> {
	
	private LinkedList<MyEntry>[] table;
	private int size = 0;
	private float maxLoadFactor;
	
	/**
	 * Creates a new MyHashNap with given initial capacity and load factor
	 * @param capacity
	 * @param loadFactor
	 */
	@SuppressWarnings("unchecked")
	MyHashMap(int capacity, float loadFactor) {
		table = (LinkedList<MyEntry>[]) new LinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			table[i] = new LinkedList<MyEntry>();
		}
		maxLoadFactor = loadFactor;
	}
	
	/**
	 * Creates a new MyHashNap with initial capacity 11 and load factor 0.75
	 */
	MyHashMap() {
		this(11, (float)0.75);
	}
	
	/**
	 * Returns the number of items in the hash map
	 * @return the number of items in the hash map
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns whether the hash map contains any items 
	 * @return whether the hash map contains any items
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Clears out the hash map of any items
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			LinkedList<MyEntry> myEntry = table[i];
			if (myEntry != null) {
				myEntry.clear();
			}
		}
		size = 0;
	}
	
	/**
	 * Returns Human readable format of the hash map
	 * @return Human readable format of the hash map
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < table.length; i++) {
			LinkedList<MyEntry> myBucket = table[i];
			for (MyEntry myEntry : myBucket) {
				if (myEntry != null) {
					output += myEntry.key + " : " + myEntry.value + "\n";
				}	
			}
		}
		return output;
	}
	
	/**
	 * Associate the specified value with the given key or replaces the value associated with the key
	 * @param key Key for the value to lookup
	 * @param value Value to associate with the key
	 * @return  the previous value associated with the key, or null if there was no mapping
	 */
	public V put(K key, V value) {
		if (key == null || value == null)
			throw new NullPointerException();
		if ((size + 1) / table.length > maxLoadFactor)
			resize();
		MyEntry newEntry = new MyEntry(key, value);
		V previousValue = null;
		LinkedList<MyEntry> entries = hash(key);
		//remove previous entry if it exists
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).equals(newEntry)) {
				MyEntry previousEntry = entries.remove(i);
				previousValue = previousEntry.value;
				size--;
				break;
			}
		}
		entries.add(newEntry);
		size++;
		return previousValue;
	}
	
	/**
	 * Returns the value associated with a given key, or null if there is no such value
	 * @param key Key for the value to lookup
	 * @return the value associated with a given key, or null if there is no such value
	 */
	public V get(K key) {
		LinkedList<MyEntry> entries = hash(key);
		V value = null;
		for (MyEntry entry : entries) {
			if (entry.key.equals(key))
				value = entry.value;
		}
		return value;
	}
	
	/**
	 * Delete the entry with the given key from the hashtable
	 * @param key Key of the entry to remove
	 * @return previous value or null if there was no such value
	 */
	public V remove(K key) {
		V value = null;
		MyEntry previousEntry = null;
		LinkedList<MyEntry> entries = hash(key);
		for (MyEntry entry : entries) {
			if (entry.key.equals(key)) {
				value = entry.value;
				previousEntry = entry;
			}
		}
		if (value != null) {
			entries.remove(previousEntry);
			size--;
			return value;
		}
 		return null; 
	}
	
	/**
	 * Returns true if the hash map contains the key
	 * @param key Key to look for
	 * @return true if the hash map contains the key
	 */
	public boolean containsKey(K key) {
		LinkedList<MyEntry> entries = hash(key);
		boolean found = false;
		for (MyEntry entry : entries) {
			if (entry.key.equals(key)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	/**
	 * Returns true if the hash map contains the value
	 * @param value Value to look for
	 * @return true if the hash map contains the key
	 */
	public boolean containsValue(V value) {
		boolean found = false;
		for (int i = 0; i < table.length; i++) {
			for (MyEntry entry : table[i]) {
				if (entry.value.equals(value)) {
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		return found;
	}
	
	/**
	 * Returns an iterator that enumerates over the keys
	 * @return an iterator that enumerates over the keys
	 */
	public Iterator<K> keys() {
		return new MyHashMapKeyIterator();
	}
	
	/**
	 * Returns an iterator that enumerates over the values
	 * @return an iterator that enumerates over the values
	 */
	public Iterator<V> values() {
		return new MyHashMapValueIterator();
	}
	
	private class MyHashMapKeyIterator implements Iterator<K> {
		private int bucketIndex;
		private ListIterator<MyEntry> listItr;
		private int iteration = 0;
		MyHashMapKeyIterator() {
			bucketIndex = 0;
			listItr = table[bucketIndex].listIterator();
		}
		public boolean hasNext() {
			//If the list iterator on the current list has next, we are done 
			return iteration < size();
		}
		public K next() {
			if (!hasNext())
				throw new NoSuchElementException();
			if (!listItr.hasNext()) {
				bucketIndex++;
				listItr = table[bucketIndex].listIterator();
				return next();
			}
			iteration++;
			return listItr.next().key;
		}
	}
	
	private class MyHashMapValueIterator implements Iterator<V> {
		private int bucketIndex;
		private ListIterator<MyEntry> listItr;
		MyHashMapValueIterator() {
			bucketIndex = 0;
			listItr = table[bucketIndex].listIterator();
		}
		public boolean hasNext() {
			//If the list iterator on the current list has next, we are done 
			if (listItr.hasNext())
				return true;
			return hasNextHelper(bucketIndex + 1);
		}
		//Recursively checks whether any remaining buckets contain a list with any entries
		private boolean hasNextHelper(int bucket) {
			if (bucket >= (table.length - 1))
				return false;
			if (table[bucket].size() > 0)
				return true;
			return hasNextHelper(bucket++);
		}
		public V next() {
			if (!hasNext())
				throw new NoSuchElementException();
			if (!listItr.hasNext()) {
				bucketIndex++;
				listItr = table[bucketIndex].listIterator();
				return next();
			}
			return listItr.next().value;
		}
	}
	
	/**
	 * Calculates which bucket to lookup using hash function
	 * @param key
	 * @return Chain (LinkedList) of entries that correspond to the table indexed at the hash value
	 */
	private LinkedList<MyEntry> hash(K key) {
		return table[Math.abs(key.hashCode()) % table.length];
	}
		
	/**
	 * Dynamically resizes the table to a new capacity from its previous capacity. 
	 * The new capacity will be the next prime number after 2x the previous capacity 
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		LinkedList<MyEntry>[] oldTable = table;
		int newCapacity = findPrimeResizeCapacity(table.length);
		//initialize new table to use
		table = (LinkedList<MyEntry>[]) new LinkedList[newCapacity];
		for (int i = 0; i < table.length; i++) {
			table[i] = new LinkedList<MyEntry>();
		}
		//loop over previous table and each entry in bucket, rehash and add items to newTable
		for (int i = 0; i < oldTable.length; i++) {
			LinkedList<MyEntry> entries = oldTable[i];
			for (MyEntry entry : entries) {
				LinkedList<MyEntry> bucket = hash(entry.key);
				bucket.add(entry);
			}
		}
	}
	
	/**
	 * Helper method for finding the next table capacity to use
	 * @param x The previous capacity of the table
	 * @return The next table capacity to use
	 */
	private int findPrimeResizeCapacity(int x) {
		int start = x * 2;
		boolean primeFound = false;
		while(!primeFound) {
			start++;
			int sq = (int)Math.sqrt(start) + 1;
			for (int i = 2; i <= sq; i++) {
				if (start % i == 0)
					primeFound = true;
			}
		}
		return start;
	}
	
	/**
	 * MyEntry class to hold data 
	 */
	private class MyEntry {
		public K key;
		public V value;
		MyEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		public int hashCode() {
			return key.hashCode();
		}
		public boolean equals(MyEntry other) {
			return other.key.equals(this.key);
		}
	}
	
	
}
