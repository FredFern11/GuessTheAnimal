package animals;

import java.util.*;

public class LogicProcess {
    static String AskAnimal() {
        // ask for animal
        String vowel = "aeiouy";
        String animal = new String();
        Scanner scanner = new Scanner(System.in);
        // get the animal
        animal = scanner.nextLine();
        // transform animal in an array of words
        LinkedList<String> animalList = new LinkedList(List.of(animal.trim().toLowerCase(Locale.ROOT).split(" ")));

        // checking special cases
        if (animal.equalsIgnoreCase("a unicorn")) {
            animal = "a unicorn";
        }
        else if (animalList.contains("xeme")) {
            animal = "an xeme";
        }
        // use the right undetermined article
        else if (animalList.size() != 1) {
            if (animalList.get(0).equals("a") || animalList.get(0).equals("the") || animalList.get(0).equals("an")) {
                animalList.pop();
            }
            // check if the first letter is a vowel
            if (vowel.contains(animalList.get(0).charAt(0) + "")) {
                animal = "an";
            } else {
                animal = "a";
            }

            // build animal string with article and each word
            for (String element: animalList) {
                animal += " " + element;
            }
        }
        else {
            if (vowel.contains(animalList.get(0).charAt(0) + "")) {
                animal = "an ";
            } else {
                animal = "a ";
            }
            animal += animalList.get(0);
        }

        return animal;
    }

    static Boolean BinairyAnswer() {
        Scanner scanner = new Scanner(System.in);
        String affirmativeAnswer = "y, yes, yeah, yep, sure, right, affirmative, correct, indeed, you bet, exactly, you said it,";
        String negativeAnswer = "n, no, no way, nah, nope, negative, i don't think so, yeah no,";
        String answer = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        String[] misunderstood = {"I'm not sure I caught you: was it yes or no?",
                "Funny, I still don't understand, is it yes or no?",
                "Oh, it's too complicated for me: just tell me yes or no.",
                "Could you please simply say yes or no?",
                "Oh, no, don't try to confuse me: say yes or no."};

        if (answer.contains("!")) {
            answer = answer.replaceFirst("!", "");
        } else if (answer.contains(".")) {
            answer = answer.replaceFirst("\\.", "");
        }

        // answer matches animal
        if (affirmativeAnswer.contains(answer + ",")) {
//            return animal;
            return true;
        }
        // answer doesn't match animal -> need to start over
        else if (negativeAnswer.contains(answer + ",")) {
//            GetAnimal();
            return false;
        }
        // answer wasn't understood
        else {
            while (!negativeAnswer.contains(answer + ",") && !affirmativeAnswer.contains(answer + ",")) {
                Random random = new Random();
                System.out.println(misunderstood[random.nextInt(4)]);
                answer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                if (answer.contains("!")) {
                    answer = answer.replaceFirst("!", "");
                } else if (answer.contains(".")) {
                    answer = answer.replaceFirst("\\.", "");
                }
            }

            // answer matches animal
            if (affirmativeAnswer.contains(answer = ",")) {
                return true;
            }
            // answer doesn't match animal -> need to start over
            else if (negativeAnswer.contains(answer + ",")) {
                return false;
            }
        }
        return false;
    }
}
