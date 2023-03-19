package animals;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
    public Node left;
    public Node right;
    public String value;

    public Node()
    {super();}

    public Node(String value) {
        // value is the question (if it's a leaf node then it's the animal's name)
        this.value = value;
        // left node is the animal that doesn't match the question (no as answer)
        left = null;
        // right node is the animal that does match the question (yes as answer)
        right = null;
    }
}