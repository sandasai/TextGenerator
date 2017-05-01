package textGeneration;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MyHashMapTest {
	
	//Numbers 0-9 inclusive
	MyHashMap<Integer,String> testHashMap1;
	Hashtable<Integer,String> trueHashTab1;
	
	//10000 Random numbers between -100000, 100000 inclusive 
	MyHashMap<Integer,String> testHashMap2;
	Hashtable<Integer,String> trueHashTab2;

	@Before
	public void setup() {
		testHashMap1 = new MyHashMap<Integer,String>();
		trueHashTab1 = new Hashtable<Integer,String>();
		for (int i = 0; i < 10; i++) {
			testHashMap1.put(i, Integer.toString(i));
			trueHashTab1.put(i, Integer.toString(i));
		}
		
		testHashMap2 = new MyHashMap<Integer,String>();
		trueHashTab2 = new Hashtable<Integer,String>();
		Random r = new Random();
		for (int i = 0; i < 10000; i++) {
			int x = r.nextInt(100000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			testHashMap2.put(x, Integer.toString(x));
			trueHashTab2.put(x, Integer.toString(x));
		}
	}
	
	@Test
	public void size() {
		assertEquals(trueHashTab1.size(), testHashMap1.size());
	}
	@Test
	public void size2() {
		assertEquals(trueHashTab2.size(), testHashMap2.size());
	}
	@Test
	public void clear() {
		trueHashTab1.clear();
		testHashMap1.clear();
		assertEquals(trueHashTab1.size(), testHashMap1.size());
		assertEquals(trueHashTab1.isEmpty(), testHashMap1.isEmpty());
	}
	@Test
	public void clear2() {
		trueHashTab2.clear();
		testHashMap2.clear();
		assertEquals(trueHashTab2.size(), testHashMap2.size());
		assertEquals(trueHashTab2.isEmpty(), testHashMap2.isEmpty());
	}
	@Test
	public void geting() {
		//will also test entries not in the maps
		for (int i = 0; i < 20; i++) {
			assertEquals(trueHashTab1.get(i), testHashMap1.get(i));
		}
	}
	@Test
	public void getting2() {
		Random r = new Random();
		for (int i = 0 ; i < 100000; i++) {
			int x = r.nextInt(1000000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			assertEquals(trueHashTab2.get(x), testHashMap2.get(x));
		}
	}
	
	@Test
	public void removing() {
		for (int i = -3; i < 5; i++) {
			assertEquals(trueHashTab1.remove(i), testHashMap1.remove(i));
		}
		for (int i = -3; i < 5; i++) {
			assertEquals(trueHashTab1.remove(i), testHashMap1.remove(i));
		}
	}
	@Test
	public void removing2() {
		Random r = new Random();
		for (int i = 0 ; i < 100000; i++) {
			int x = r.nextInt(1000000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			assertEquals(trueHashTab2.remove(x), testHashMap2.remove(x));
		}
	}

	@Test
	public void removingAllThenSizing() {
		for (int i = 0; i < 10; i++) {
			assertEquals(trueHashTab1.remove(i), testHashMap1.remove(i));
		}
		assertEquals(trueHashTab1.size(), testHashMap1.size());
	}
	
	@Test
	public void removingAllThenSizing2() {
		Random r = new Random();
		for (int i = 0 ; i < 5000; i++) {
			int x = r.nextInt(1000000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			assertEquals(trueHashTab2.remove(x), testHashMap2.remove(x));
		}
		assertEquals(trueHashTab2.size(), testHashMap2.size());
	}
	
	@Test
	public void removingAndPutting() {
		for (int i = 0; i < 10; i++) {
			assertEquals(trueHashTab1.remove(i), testHashMap1.remove(i));
		}
		for (int i = 1; i <= 1024; i *= 2) {
			assertEquals(trueHashTab1.put(i, Integer.toString(i)), testHashMap1.put(i, Integer.toString(i)));
		}
		for (int i = 0; i <= 1024; i++) {
			assertEquals(trueHashTab1.remove(i), testHashMap1.remove(i));
		}
	}
	
	@Test
	public void removingAndPutting2() {
		Random r = new Random();
		//removing
		for (int i = 0 ; i < 5000; i++) {
			int x = r.nextInt(1000000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			assertEquals(trueHashTab2.remove(x), testHashMap2.remove(x));
		}
		assertEquals(trueHashTab2.size(), testHashMap2.size());
		//putting
		for (int i = 0; i < 10000; i++) {
			int x = r.nextInt(10000);
			if (r.nextBoolean() == true) {
				x *= -1;
			}
			testHashMap2.put(x, Integer.toString(i));
			trueHashTab2.put(x, Integer.toString(i));
		}
		assertEquals(trueHashTab2.size(), testHashMap2.size());
	}
	
	@Test
	public void containsKey() {
		for (int i = -10; i < 20; i++) {
			assertEquals(trueHashTab1.containsKey(i), testHashMap1.containsKey(i));
		}
		for (int i = -100000; i <= 100000; i++) {
			assertEquals(trueHashTab2.containsKey(i), testHashMap2.containsKey(i));
		}
	}
	
	@Test
	public void contains() {
		for (int i = -10; i < 20; i++) {
			String str = Integer.toString(i);
			assertEquals(trueHashTab1.containsValue(str), testHashMap1.containsValue(str));
		}
		for (int i = -1000; i <= 1000; i++) {
			String str = Integer.toString(i);
			assertEquals(trueHashTab2.containsValue(str), testHashMap2.containsValue(str));
		}
	}
	@Test
	public void keys() {
		HashSet<Integer> set = new HashSet<Integer>();
		Iterator<Integer> keys = testHashMap1.keys();
		int count = 0;
		while(keys.hasNext()) {
			count++;
			int nextInt = keys.next();
			assertTrue(!set.contains(nextInt));
			set.add(nextInt);
			assertTrue(between(nextInt, 0, 9));
		}
		assertEquals(10, count);
	}
	
	@Test
	public void values() {
		HashSet<String> set = new HashSet<String>();
		Iterator<String> keys = testHashMap1.values();
		while(keys.hasNext()) {
			String nextStr = keys.next();
			assertTrue(!set.contains(nextStr));
			set.add(nextStr);
		}
	}
	
	private boolean between(int x, int a, int b) {
		return x >= a && x <= b;
	}
}
