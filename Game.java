import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Game {
    private static int gameNumber = 1;
    private final String secretNumber;
    private final List<String> guesses;
    private final List<String> results;

    public Game(String secretNumber) {
        this.secretNumber = secretNumber;
        this.guesses = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        String guess;

        do {
            System.out.print("Enter your guess (4 digits): ");
            guess = scanner.nextLine();

            if (guess.equals("хватит")) {
                break;
            }

            try {
                checkWordLength(guess);
                String result = calculateResult(guess);
                guesses.add(guess);
                results.add(result);
                System.out.println("Result: " + result);
            } catch (WordLengthException e) {
                System.out.println(e.getMessage());
            }
        } while (!guess.equals("хватит"));

        saveGameToFile();
    }

    private void checkWordLength(String word) throws WordLengthException {
        if (word.length() > 7) {
            throw new WordLengthException("Word length should not exceed 7 characters");
        }
    }

    private String calculateResult(String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            char digit = guess.charAt(i);

            if (digit == secretNumber.charAt(i)) {
                bulls++;
            } else if (secretNumber.contains(String.valueOf(digit))) {
                cows++;
            }
        }

        return bulls + " " + (bulls == 1 ? "бык" : "быка") + " " +
                cows + " " + (cows == 1 ? "корова" : "коровы");
    }

    private void saveGameToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("games.txt", true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm");
            String timestamp = LocalDateTime.now().format(formatter);

            writer.println("Game №" + gameNumber + " " + timestamp + " Загаданная строка " + secretNumber);

            for (int i = 0; i < guesses.size(); i++) {
                writer.println("  Запрос: " + guesses.get(i) + " Ответ: " + results.get(i));
            }

            writer.println("  Строка была угадана за " + guesses.size() + " попыток.");
            writer.println();

            gameNumber++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
