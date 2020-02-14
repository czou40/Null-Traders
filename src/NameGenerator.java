import java.io.File;
import java.util.Scanner;

public class NameGenerator {
    private String[] initialConsonants;
    private String[] midConstants;
    private String[] finalConstants;
    private String[] vowels;


    public NameGenerator() {
        try {
            Scanner scanner = new Scanner(new File("src/data/phonemes.txt"));
            initialConsonants = scanner.nextLine().split(" ");
            midConstants = scanner.nextLine().split(" ");
            finalConstants = scanner.nextLine().split(" ");
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
            random = (int) (Math.random() * initialConsonants.length);
            result.append(initialConsonants[random]);
        }
        random = (int) (Math.random() * vowels.length);
        result.append(vowels[random]);
        if (numSyllable == 1 && !endVowel) {
            random = (int) (Math.random() * finalConstants.length);
            result.append(finalConstants[random]);
        }
        for (int i = 2; i <= numSyllable; i++) {
            random = (int) (Math.random() * midConstants.length);
            result.append(midConstants[random]);
            random = (int) (Math.random() * vowels.length);
            result.append(vowels[random]);
            if (i == numSyllable && !endVowel) {
                random = (int) (Math.random() * finalConstants.length);
                result.append(finalConstants[random]);
            }
        }
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}
