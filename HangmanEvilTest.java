
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanEvilTest {

	private HangmanEvil hangEvil;
	private ArrayList<String> wordPool;
	private HashMap<String, ArrayList<String>> family;
	private ArrayList<String> largestFamily;
	private ArrayList<String> lines;

	@BeforeEach
	void setUp() throws Exception {
		hangEvil = new HangmanEvil();
		
		File file = new File("word1.txt");
		
		lines = new ArrayList<String>();
		
		FileReader fileReader = null;
		
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					lines.add(line);
				}
				line = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		wordPool = hangEvil.buidWordPool("word1.txt");
		family = hangEvil.buildFamilies(wordPool, "e");
		largestFamily = hangEvil.pickLargestFamily(family);
	}

	@Test
	void testBuidWordPool() {
		assertEquals(5, wordPool.size());
		assertFalse(wordPool.contains("eel.-"));
	}

	@Test
	void testBuildFamilies() {
		assertEquals(4, family.size());
		assertEquals("heel", family.get("-ee-").get(0));
	}

	@Test
	void testPickLargestFamily() {
		assertEquals(2, largestFamily.size());
		assertTrue(largestFamily.contains("help"));
		assertTrue(largestFamily.contains("belt"));
	}

}
