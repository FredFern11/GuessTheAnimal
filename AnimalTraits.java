package animals;

import java.util.*;

import static animals.BinaryTree.IsLeaf;
import static animals.LogicProcess.AskAnimal;
import static animals.Manager.binaryTree;

public class AnimalTraits {
    private static Stack<String> animalTraits = new Stack<>();

    // Print path present in the list in reverse order (leaf to the root node)
    private static void PrintPath(Deque<String> path) {
        Iterator<String> itr = path.descendingIterator();
        while (itr.hasNext()) {
            animalTraits.push(itr.next());
        }
    }

    // Recursive function to print all paths from leaf-to-root node
    private static void PrintLeafRootPath(Node node, String animal, Deque<String> path) {
        // base case
        if (node == null) {
            return;
        }

        // include the current node to the path
        path.addLast(node.value);

        // if a leaf node is found and corresponds to animal node, print the path
        if (IsLeaf(node) && node.value.equals(animal)) {
            PrintPath(path);
        }

        // recur for the left and right subtree
        PrintLeafRootPath(node.left, animal, path);
        PrintLeafRootPath(node.right, animal, path);

        // backtrack: remove the current node after the left, and right subtree are done
        path.removeLast();
    }

    // The main function to print all paths from leaf-to-root node
    private static void PrintLeafRootPath(Node node, String animal) {
        // Deque to store leaf-to-root path
        Deque<String> path = new ArrayDeque<>();

        // call recursive function
        PrintLeafRootPath(node, animal, path);
    }

    private static void NavigateTree(Node node, Stack<String> listTraits) {
        String trait = "";
        if (!listTraits.empty() && listTraits.size()>1) {
            trait = listTraits.pop();
            trait = QuestionCast(trait);
        }

        if (IsLeaf(node)) {
            return;
        } else if (listTraits.contains(node.left.value)) {
            System.out.println(" - " + trait.replaceFirst("can", "can't").replaceFirst("is", "isn't").replaceFirst("has", "doesn't have"));
            NavigateTree(node.left, listTraits);
        } else if (listTraits.contains(node.right.value)) {
            System.out.println(" - " + trait);
            NavigateTree(node.right, listTraits);
        }
    }

    public static String QuestionCast(String question) {
        question = question.replace("?", ".");
        String[] traitArray = question.split(" ", 3);
        question = traitArray[1] + " " +traitArray[0] + " " + traitArray[2];
        question = question.toLowerCase(Locale.ROOT).replace("does have", "has");
        question = question.substring(0, 1).toUpperCase() + question.substring(1);
        return question;
    }

    public static void PrintAnimal() {
        System.out.println("Enter the animal:");
        String animal = AskAnimal();
        PrintLeafRootPath(binaryTree.root, animal);
        if (animalTraits.empty()) {
            System.out.println("No facts about the " + animal.split(" ")[1]);
        } else {
            System.out.println(animalTraits);
            NavigateTree(binaryTree.root, animalTraits);
        }
    }

}
