package animals;

import java.io.IOException;
import java.util.*;

import static animals.AnimalTraits.PrintAnimal;
import static animals.BinaryTree.*;
import static animals.Guessing.Game;
import static animals.LogicProcess.AskAnimal;
import static animals.Storage.*;

public class Manager {

    // Knowledge tree
    static BinaryTree binaryTree = new BinaryTree();
    // store all the answers to the questions to put as input in the add algorithm
    static LinkedList<Boolean> answerPath = new LinkedList<>();
    // arguments of main function
    static String[] programArguments = new String[2];

    public static void Preprocessing(String[] args) throws IOException {
        // Greeting
        System.out.println(Hello());
        // Accessing program parameters and greetings only happen at starting
        if (args.length == 2) {programArguments = args;}
        // preparation (only instructions and greeting)
        if (LookFile(programArguments[1]) != null) {
            Deserialize("animals." + programArguments[1]);
        } else if (binaryTree.root == null) {
            // Asking for root animal
            System.out.println("I want to learn about animals.\nWhich animal do you like most?");
            String animalGuess = AskAnimal();

            // Initializing tree with root animal
            Node rootNode = new Node(animalGuess);
            binaryTree.root = rootNode;
        }
    }

    public static void Home() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the animal expert system!\n\nWhat do you want to do:\n\n1. Play the guessing game\n2. List of all animals\n" +
                "3. Search for an animal\n4. Calculate statistics\n5. Print the knowledge tree\n0. Exit");

        switch (scanner.nextLine()) {
            case "1":
                // Play the guessing game
                Game();
                break;

            case "2":
                // List of all animals
                for (String element : ListAnimalsRecursive()) {
                    System.out.println("- " + element);
                }
                break;

            case "3":
                // Search for an animal
                PrintAnimal();
                break;

            case "4":
                // Calculate statistics (height, number of nodes...)
                Statistics();
                break;

            case "5":
                // Print knowledge tree
                PrintTree(System.out);
                break;

            case "0":
                // Exit
                Bye();
                break;
        }
        System.out.println(" ");
        Home();
    }

    static String Hello() {
        // Get date
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format

        //evaluate which greeting to use depending on the hour of the day
        if (hour >= 18) {
            return "Good evening\n";
        } else if (hour >= 12) {
            return "Good afternoon\n";
        } else if (hour >= 5) {
            return "Good morning\n";
        } else {
            return "something malfunctioned, maybe you live in a different universe\n";
        }
    }

    static void Bye() throws IOException {
        Random random = new Random();
        String[] bye = {"Bye", "See you next time", "See you later", "Talk to you later", "See ya!"
                , "See you later alligator", "Later!", "Catch you later", "Catch you on the flip side",
                "Have a good day", "Have a good one", "Take it easy", "Take care", "Toodaloo!", "Bye bye!", "Hasta la vista, baby!"
                , "Adios, amigos!", "Au revoir!", "Adieu!", "Farewell!", "Until the next time", "Thank you and Goodbye!"};
        System.out.println(bye[random.nextInt(bye.length-1)]);
        Serialize("animals." + programArguments[1]);
        System.exit(11);
    }

}
