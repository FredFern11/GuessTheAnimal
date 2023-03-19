package animals;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.PrintStream;
import java.util.*;

import static animals.AnimalTraits.QuestionCast;
import static animals.Guessing.DiffDesc;
import static animals.LogicProcess.*;
import static animals.Manager.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinaryTree {
    public Node root;

    public BinaryTree() {}

    public static void Statistics() {
        System.out.println("The Knowledge Tree stats\n");
        System.out.println("- root node                    " + QuestionCast(binaryTree.root.value));
        System.out.println("- total number of nodes        " + NumberNodes(binaryTree.root));
        System.out.println("- total number of animals      " + ListAnimalsRecursive().length);
        System.out.println("- total number of statements   " + (NumberNodes(binaryTree.root) - (ListAnimalsRecursive().length)));
        System.out.println("- height of the tree           " + DepthTree(binaryTree.root));
        System.out.println("- minimum animal's depth       " + MinimumDepthTree(binaryTree.root));
        System.out.println("- average animal's depth       " + AverageHeight());
    }

    // return a string of all the animals (leaf nodes) seperated with a comma
    private static String ListAnimalsRecursive(Node node) {
        if (node == null)
            return null;
        if (node.left == null && node.right == null) {
            return node.value;
        } else {
            return ListAnimalsRecursive(node.left) + "," + ListAnimalsRecursive(node.right);
        }
    }

    // return a string of all the animals
    public static String[] ListAnimalsRecursive() {
        String[] animalList = ListAnimalsRecursive(binaryTree.root).split(",");
        Arrays.sort(animalList);
        return animalList;
    }

    public static int AverageHeight() {
        // *1.0 to make the division return a double (would otherwise be an integer)
        return (int) Math.round((SumSubTrees(binaryTree.root)*1.0)/NumberNodes(binaryTree.root));
    }

    // Function to find the sum of depths of all nodes in subtree of the current node
    private static int SumDepth(Node root, int d) {

        // If NULL node then return 0
        if (root == null)
            return 0;

        // Recursively find the sum of depths of all nodes in the left and right subtree
        return d + SumDepth(root.left, d++) + SumDepth(root.right, d++);
    }

    // Function to calculate the sum of depth of all the subtrees
    private static int SumSubTrees(Node root) {
        // If root is NULL return 0
        if (root == null)
            return 0;

        // Find the total depth sum of
        // current node and recursively
        return SumDepth(root, 0) + SumSubTrees(root.left) + SumSubTrees(root.right);
    }

    // return the total height of the knowledge tree
    private static int DepthTree(Node node) {
        if (node == null)
            return -1;
        else {
            // compute the depth of each subtree
            int leftDepth = DepthTree(node.left);
            int rightDepth = DepthTree(node.right);

            // use the larger one
            if (leftDepth > rightDepth)
                return (leftDepth + 1);
            else
                return (rightDepth + 1);
        }
    }

    // return the minimum depth of the tree
    private static int MinimumDepthTree(Node node) {
        if (node == null) {
            return -1;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        if (node.left == null) {
            return MinimumDepthTree(node.right) + 1;
        }
        if (node.right == null) {
            return MinimumDepthTree(node.left) + 1;
        }
        return Math.min(MinimumDepthTree(node.left), MinimumDepthTree(node.right));
    }

    // return the number of nodes
    private static int NumberNodes(Node node) {
        if (node == null) {
            return -1;
        } else {
            int sumNode = 1;
            int leftSum = 0;
            int rightSum = 0;
            if (node.left != null) {
                leftSum = NumberNodes(node.left);
            }
            if (node.right != null) {
                rightSum = NumberNodes(node.right);
            }
            return sumNode + leftSum + rightSum;
        }
    }

    private static Node AddRecursive(Node current, String value, String animalYes, String animalNo, int i) {
        i += 1;
        System.out.println(answerPath + "[" + i + "]");
        if (IsLeaf(current)) {
            System.out.println("Node that have been changed: " + current.value);
            Node node = new Node(value);
            node.left = new Node(animalNo);
            node.right = new Node(animalYes);
            return node;
        }

        System.out.println("Direction " + answerPath.get(i));
        if (answerPath.get(i) == false) {
            System.out.println("Next node to the left: " + current.left.value);
            current.left = AddRecursive(current.left, value, animalYes, animalNo, i);
        } else if (answerPath.get(i) == true) {
            System.out.println("Next node to the right: " + current.right.value);
            current.right = AddRecursive(current.right, value, animalYes, animalNo, i);
        }
        return current;
    }

    // add a node to the tree
    public static void Add(String value, String animalYes, String animalNo) {
        // 4th parameter = -1 because we add one in the AddRecursive in order to start at index 0
        binaryTree.root = AddRecursive(binaryTree.root, value, animalYes, animalNo, -1);
    }

    // Function to check if a given node is a leaf node or not
    public static boolean IsLeaf(Node node) {
        return (node.left == null && node.right == null);
    }

    // traverse the tree while asking the different questions
    public static Node TraverseTree(Node node) {
        if (IsLeaf(node)) {
            return node;
        }

        System.out.println(node.value);
        if (BinairyAnswer()) {
            System.out.println("Right");
            answerPath.add(true);
            return TraverseTree(node.right);
        } else {
            System.out.println("Left");
            answerPath.add(false);
            return TraverseTree(node.left);
        }
    }

    // part of the game process: ask the user the describe their animal and then add the new node
    public static void UpdateTree(String animalGuess) {
        String[] questionAnimal;
        String animalAnswer;
        System.out.println("I give up. What animal do you have in mind?");
        animalAnswer = AskAnimal();
        questionAnimal = DiffDesc(animalAnswer, animalGuess);
        // questionAnimal is an array that contains:
        // [0] -> question of description
        // [1] -> animal to which the description fits (answer to question is positive)
        // [2] -> animal to chich the description doesn't fit (answer to question is negative)
        binaryTree.Add(questionAnimal[0] + "?", questionAnimal[1], questionAnimal[2]);
    }

    // pretty print the binairy tree
    public static void PrintTree(PrintStream os) {
        os.println(TraversePreOrder(binaryTree.root));
    }

    private static String TraversePreOrder(Node node) {
        if (node == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(node.value + "\n");

        String pointerRight = "└──";
        String pointerLeft = (node.right != null) ? "├──" : "└──";

        TraverseNodes(stringBuilder, "", pointerLeft, node.left, node.right != null);
        TraverseNodes(stringBuilder, "", pointerRight, node.right, false);

        return stringBuilder.toString();
    }

    private static void TraverseNodes(StringBuilder stringBuilder, String padding, String pointer, Node node, Boolean hasRightSibling) {
        if (node != null) {
            stringBuilder.append(padding + pointer + node.value + "\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.right != null) ? "├──" : "└──";

            TraverseNodes(stringBuilder, paddingBoth, pointerLeft, node.left, node.right != null);
            TraverseNodes(stringBuilder, paddingBoth, pointerRight, node.right, false);
        }
    }
}
