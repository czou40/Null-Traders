package cores.utilities;

import java.io.File;
import java.util.Scanner;

public class NameGenerator {
    private String[] initialConsonants;
    private String[] midConsonants;
    private String[] finalConsonants;
    private String[] vowels;


    public NameGenerator() {
        try {
            Scanner scanner = new Scanner(new File("src/data/phonemes.txt"));
            initialConsonants = scanner.nextLine().split(" ");
            midConsonants = scanner.nextLine().split(" ");
            finalConsonants = scanner.nextLine().split(" ");
            vowels = scanner.nextLine().split(" ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getName() {
        return getName((int) (Math.random() * 4) + 1);
    }

    public String getName(int numSyllable) {
        StringBuilder result = new StringBuilder();
        boolean startVowel = numSyllable != 1 && Math.random() <= 0.15;
        boolean endVowel = Math.random() <= 0.6;
        int random;
        if (!startVowel) {
            random = getRandom(initialConsonants.length);
            result.append(initialConsonants[random]);
        }
        random = getRandom(vowels.length);
        result.append(vowels[random]);
        if (numSyllable == 1 && !endVowel) {
            random = getRandom(finalConsonants.length);
            result.append(finalConsonants[random]);
        }
        for (int i = 2; i <= numSyllable; i++) {
            random = getRandom(midConsonants.length);
            result.append(midConsonants[random]);
            random = getRandom(vowels.length);
            result.append(vowels[random]);
            if (i == numSyllable && !endVowel) {
                random = getRandom(finalConsonants.length);
                result.append(finalConsonants[random]);
            }
        }
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    public static int getRandom(int upperBound) {
        return upperBound - 1 - (int) (Math.pow(Math.random()
                * upperBound * upperBound * upperBound, 1.0 / 3));
    }
}
