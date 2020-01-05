

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanTest {
	
	private Hangman hang;
	
	private ArrayList<String> lines;
	
	@BeforeEach
	void setUp() throws Exception {				
		hang = new Hangman();
		
		File file = new File("words.txt");
		
		lines = new ArrayList<String>();
		
		FileReader fileReader = null;
		
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			//get the first 34 lines of word.txt (contains only one valid word "a")
			while (line != null && lines.size() <= 34) {
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

	}
	
	@Test
	void testCleanContent() {
		assertEquals(Hangman.cleanContent(lines).size(), 1);
		assertEquals(Hangman.cleanContent(lines).get(0), "a");
	}

	@Test
	void testIsCorrect() {
		assertTrue(hang.isCorrect("word", "w"));
		assertFalse(hang.isCorrect("word", "a"));
	}
	
	@Test
	void testPrintCurrent() {
		HangmanGame.current = new ArrayList<String>();
		for (int i = 0; i < "word".length(); i++) {
			HangmanGame.current.add("_ ");
		}
		HangmanGame.incorrectList= new ArrayList<String>();
		
		hang.printCurrent("word", "w");
		assertEquals(hang.getGuessCount(), 1);
		
		hang.printCurrent("word", "a");
		assertEquals(hang.getGuessCount(), 2);
		assertEquals(hang.getMistakeCount(), 1);
	}
}
