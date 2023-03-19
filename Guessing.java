package animals;

import java.io.IOException;
import java.util.*;

import static animals.BinaryTree.*;
import static animals.LogicProcess.*;
import static animals.Manager.*;

public class Guessing {
    public static String[] DiffDesc(String animalAnswer, String answerGuess) {
        String[] questionArray;
        String question;
        Scanner scanner = new Scanner(System.in);
        String[] description = new String[2];
        String animalYes = null;
        String animalNo = null;

        System.out.println("Specify a fact that distinguishes " + answerGuess + " from " + animalAnswer + ".\"");
        System.out.println("The sentence should be of the format: \"It can/has/is ...\"");
        description[0] = scanner.nextLine().toLowerCase();
        description[0] = description[0].substring(0, 1).toUpperCase() + description[0].substring(1);

        while (!(description[0].contains("It is") || description[0].contains("It can") || description[0].contains("It has"))) {
            System.out.println("The examples of a statement:\n" +
                    " - It can fly\n" +
                    " - It has horn\n" +
                    " - It is a mammal\n" +
                    "The sentence should be of the format: 'It can/has/is ...'.");
            description[0] = scanner.nextLine();
        }

        System.out.println("Is the statement correct for " + animalAnswer + "?");
        description[1] = scanner.nextLine().toLowerCase(Locale.ROOT);
        while (!(description[1].equals("yes") || description[1].equals("no"))) {
            System.out.println("yes or no");
            description[1] = scanner.nextLine().toLowerCase(Locale.ROOT);
        }
        System.out.println("I have learned the following facts about animals:");

        // Using "The" for desciption
        String animalDesc1 = animalAnswer.replaceFirst("a ", "The ").replaceFirst("an ", "The ");
        String animalDesc2 = answerGuess.replaceFirst("a ", "The ").replaceFirst("an ", "The ");

        if (description[1].equals("yes")) {
            animalYes = animalAnswer;
            animalNo = answerGuess;
            System.out.println("- " + description[0].replaceFirst("It", animalDesc1) + ".");
            System.out.println("- " + description[0].replaceFirst("It", animalDesc2).replaceFirst("can", "can't").replaceFirst("is", "isn't").replaceFirst("has", "doesn't have") + ".");
        } else if (description[1].equals("no")) {
            animalYes = answerGuess;
            animalNo = animalAnswer;
            System.out.println("- " + description[0].replaceFirst("It", animalDesc2) + ".");
            System.out.println("- " + description[0].replaceFirst("It", animalDesc1).replaceFirst("can", "can't").replaceFirst("is", "isn't").replaceFirst("has", "doesn't have") + ".");
        }

        questionArray = description[0].split(" ", 3);
        question = questionArray[1] + " it " + questionArray[2];
        question = question.substring(0, 1).toUpperCase() + question.substring(1);

        if (question.contains("Has it")) {
            question = question.replace("Has it", "Does it have");
        }

        System.out.println("I can distinguish these animals by asking the question: \n- " + question + "?");
        return new String[]{question, animalYes, animalNo};
    }

    public static void FinalQuestion(Node leafNode) throws IOException {
        // ask if animal guessed is the right one
        System.out.println("Is it " + leafNode.value + "?");
        if (!BinairyAnswer()) {
            UpdateTree(leafNode.value);
        }
        // ask if want to play again
        PlayAgain();
    }

    public static void PlayAgain() throws IOException {
        PrintTree(System.out);
        System.out.println("Nice! I've learned so much about animals!\n\nWould you like to play again?");
        if (BinairyAnswer()) {
            System.out.println(System.lineSeparator());
            Game();
        } else {
            Home();
        }
    }

    public static void Game() throws IOException {
        answerPath = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wonderful! I've learned so much about animals!\nLet's play a game!\n" +
                "You think of an animal, and I guess it.\nPress enter when you're ready.");
        scanner.nextLine();
        Node leafNode = TraverseTree(binaryTree.root);
        FinalQuestion(leafNode);
    }
}