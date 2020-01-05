import java.util.ArrayList;
import java.util.Random;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Hangman {

	/**
	 * represents how many times the user has guessed
	 */
	private int guessCount;

	/**
	 * represents how many mistakes the user has made
	 */
	private int mistakeCount;

	/**
	 * represents if the user has correctly guessed a letter
	 */
	private boolean guessCorrect;

	/**
	 * represents if the game is over
	 */
	private boolean isGameOver;

	/**
	 * Get guessCount
	 * 
	 * @return the count of guess
	 */
	public int getGuessCount() {
		return guessCount;
	}

	/**
	 * Get mistakeCount
	 * 
	 * @return the count of mistake guess
	 */
	public int getMistakeCount() {
		return mistakeCount;
	}

	/**
	 * opens and reads the data in filename stores each line as an element into a
	 * list
	 * 
	 * @param fileName the name of the file to be open
	 * @return a list of lines
	 */
	public static ArrayList<String> getContent(String fileName) {

		// create file
		File file = new File(fileName);

		// to store each line as an element into a list
		ArrayList<String> lines = new ArrayList<String>();

		// define file reader
		FileReader fileReader = null;

		// define buffered reader
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			// String variable for each line
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.isEmpty()) {
					lines.add(line);
				}

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

		return lines;
	}

	/**
	 * cleans the content that has been read in
	 * Upper case letters, Abbreviations - designated by a ‘.’, An apostrophe, 
	 * A hyphen, Compound words - words with spaces, A digit 
	 * @param lines an arrayList containing each line as an element
	 * @return arrayList with only approved words
	 */
	public static ArrayList<String> cleanContent(ArrayList<String> lines) {
		ArrayList<String> newLines = new ArrayList<String>();

		for (String line : lines) {

			Pattern p = Pattern.compile("[A-Z0-9-.'\\s]+");
			Matcher m = p.matcher(line);

			if (m.find()) {
				continue;
			} else {
				newLines.add(line);
			}

		}
		return newLines;

	}

	/**
	 * the computer randomly picks a word
	 * 
	 * @param fileName the name of the file to be open
	 * @return the word picked by the computer
	 */
	public static String pickWord(String fileName) {

		Random random = new Random();

		ArrayList<String> dict = Hangman.getContent(fileName);
		dict = Hangman.cleanContent(dict);

		int dictLength = dict.size();

		int index = random.nextInt(dictLength);
		String word = dict.get(index);
		return word;
	}

	/**
	 * Returns true if the guess is correct, false otherwise
	 * 
	 * @param word   chosen by the computer
	 * @param letter input by the user
	 * @return if the guess is correct
	 */
	public boolean isCorrect(String word, String letter) {
		if (word.contains(letter)) {
			guessCorrect = true;
		} else {
			guessCorrect = false;
		}
		return guessCorrect;

	}

	/**
	 * Print current incomplete word and increment mistakeCount and guessCount, and
	 * print incorrect guesses
	 * 
	 * @param word   picked by the computer
	 * @param letter input by the user
	 */
	public void printCurrent(String word, String letter) {
		guessCount += 1;

		if (this.isCorrect(word, letter)) {
			int i = word.indexOf(letter);
			while (i >= 0) {
				HangmanGame.current.set(i, letter + " ");
				i = word.indexOf(letter, i + 1);

			}
		} else {
			HangmanGame.incorrectList.add(letter);
			System.out.println("Incorrect guesses: " + HangmanGame.incorrectList);
			mistakeCount += 1;
		}

		for (String element : HangmanGame.current) {
			System.out.print(element);

		}
		System.out.println();
		System.out.println();
	}

	/**
	 * judges if the game is over and prints out the message telling the user the
	 * result
	 * 
	 * @param word picked by the computer
	 * @return true if the game the game is over, false otherwise
	 */
	public boolean isGameOver(String word) {
		List<Character> chars = new ArrayList<>();

		for (char ch : word.toCharArray()) {
			chars.add(ch);
		}

		Set<Character> set = new HashSet<Character>(chars);

		// if the user wins within 15 guesses, the user wins, the game is over
		// if the user guesses over 15 times, the user loses, the game is over
		if (this.guessCount <= 15) {
			if (this.guessCount - this.mistakeCount == set.size()) {
				this.isGameOver = true;

				System.out.println("Congratulations! You win!");

			}
		} else {
			this.isGameOver = true;

			System.out.println("Sorry. You lose. Good luck next time!");
		}
		return isGameOver;
	}

	/**
	 * Initialize all the fields
	 */
	public Hangman() {
		this.guessCount = 0;
		this.mistakeCount = 0;
		this.guessCorrect = false;
		this.isGameOver = false;
	}

	/**
	 * Get isGameOver
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return this.isGameOver;
	}

}
