import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

	/**
	 * represent what the current word looks like
	 */
	public static ArrayList<String> current;

	/**
	 * display the list of incorrect guesses
	 */
	public static ArrayList<String> incorrectList;

	/**
	 * represent the filtered words to be chosen by evil computer after the user
	 * guessing
	 */
	public ArrayList<String> pool2;

	/**
	 * Main class for a Human vs. Computer version of HangmanGame. Creates a single
	 * instance of Hangman and HangmanEvil respectively. Launches the game and gets
	 * user input for interacting with and playing against the Computer.
	 * 
	 * @author Xuanzhou Du &amp; Shuxin He
	 */

	public static void main(String[] args) {
		System.out.println("Hangman is a 2-player word-guessing game generally played by having one player "
				+ "think of a word and the other player trying to guess that word letter by letter.\n"
				+ "The computer will pick a random word from a dictionary. A row of underscores represent "
				+ "each letter of the word (e.g. ‘dog’ would be shown to the user as _ _ _).\n"
				+ "You guess one letter at a time. Every correct letter that they guess is shown in its correct location(s).\n"
				+ "You have 15 chances to guess.\n" + "You are not allowed to guess a letter twice.\n"
				+ "This game has two versions: traditional and evil.\n"
				+ "In the traditional version of the game, the computer has to stick to the original word as the user guesses;\n"
				+ "In the evil version, the computer keeps changing the word in order to make the user’s task harder.\n"
				+ "You won't be told which version you are playing until the game is over.\n" + "");

		String fileName = "words.txt";

		Scanner scanner = new Scanner(System.in);

		boolean playAgain = false;
		while (playAgain == false) {

			String word = Hangman.pickWord(fileName);

			// stores all the guesses for later check
			ArrayList<String> guessHistory = new ArrayList<String>();

			HangmanGame.current = new ArrayList<String>();
			for (int i = 0; i < word.length(); i++) {
				current.add("_ ");
			}

			// generate a random number between 0 and 1
			Random random = new Random();
			int mode = random.nextInt(2);

			// 0 represents HangmanTraditional
			if (mode == 0) {

				Hangman hang = new Hangman();

				HangmanGame.incorrectList = new ArrayList<String>();

				while (hang.isGameOver() == false) {

					while (true) {
						System.out.println("Guess a letter:");

						// print the underscores for the word before the first guess
						if (hang.getGuessCount() == 0) {
							for (String element : HangmanGame.current) {
								System.out.print(element);

							}
							System.out.println();
						}

						String humanInput = scanner.next();

						// take the first character of the user input if the user enters more than one
						// letter
						String input = Character.toString(humanInput.charAt(0));

						// if the user guesses the same letter twice, give them a message.
						if (guessHistory.contains(input)) {
							System.out.println("You can't guess the same letter twice. Please enter another one.");
						} else if (Character.isLetter(humanInput.charAt(0)) && !guessHistory.contains(input)) {
							// ensure the input is a valid letter
							// store every guess to check for duplicate guess
							guessHistory.add(input);

							hang.printCurrent(word, input);

							// if the input is valid, stop asking
							break;
						}

					}

					// judge if the game is over
					hang.isGameOver(word);
				}
				System.out.println("You played the traditional version.");
				System.out.println("Your total guess count: " + hang.getGuessCount());

			}

			// 1 represents HangmaneEvil
			if (mode == 1) {

				HangmanGame hangmanGame = new HangmanGame();

				HangmanEvil hangEvil = new HangmanEvil();

				ArrayList<String> pool = hangEvil.buidWordPool(fileName);

				HangmanGame.incorrectList = new ArrayList<String>();

				while (hangEvil.isGameOver() == false) {
					while (true) {
						System.out.println("Guess a letter:");

						// print the underscores for the word before the first guess
						if (hangEvil.getGuessCount() == 0) {
							for (String element : HangmanGame.current) {
								System.out.print(element);
							}
							System.out.println();
						}

						String humanInput = scanner.next();

						// take the first character of the user input if the user enters more than one
						// letter
						String input = Character.toString(humanInput.charAt(0));

						// if the user guesses the same letter twice, give them a message.
						if (guessHistory.contains(input)) {
							System.out.println("You can't guess the same letter twice. Please enter another one.");
						} else if (Character.isLetter(humanInput.charAt(0)) && !guessHistory.contains(input)) {
							// ensure the input is a valid letter
							// store every guess to check for duplicate guess
							guessHistory.add(input);

							hangEvil.printCurrent(word, input);

							// if the input is valid, stop asking
							break;
						}

						// for the first guess, the computer should pick a largest family as pool2
						if (hangEvil.getGuessCount() == 1) {
							HashMap<String, ArrayList<String>> family = hangEvil.buildFamilies(pool, input);
							hangmanGame.pool2 = hangEvil.pickLargestFamily(family);
						}

						// for the second and following guesses, the computer should use the pool2 to
						// generate families and keep picking the largest family
						HashMap<String, ArrayList<String>> family = hangEvil.buildFamilies(hangmanGame.pool2, input);
						hangmanGame.pool2 = hangEvil.pickLargestFamily(family);

					}
					// judge if the game is over
					hangEvil.isGameOver(word);
				}
				System.out.println("You played the evil version.");
				System.out.println("Your total guess count: " + hangEvil.getGuessCount());
			}

			boolean askAgain = true;
			while (askAgain == true) {
				System.out.println("Play again? Please type \"y\" or \"n\".");
				String answer = scanner.next();
				if (answer.equals("n") || answer.equals("N")) {
					System.out.println("Bye!");
					playAgain = true;
					// break;
					askAgain = false;
				}

				if (answer.equals("y") || answer.equals("Y")) {
					System.out.println("Another round!");
					// break;
					askAgain = false;
				}
			}

		}
		scanner.close();

	}

}
