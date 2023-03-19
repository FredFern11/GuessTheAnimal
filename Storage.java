package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

import static animals.Guessing.Game;
import static animals.Manager.binaryTree;

public class Storage {
    // Look if file exists (if not: start a new knoledge tree)
    public static String LookFile(String extension) {
        String fileName = "animals." + extension;
        File file = new File(fileName);
        if (file.exists()) {
            return fileName;
        } else {
            return null;
        }
    }

    // Write root node in a file to store
    public static void Serialize(String fileName) throws IOException {
        ObjectMapper objectMapper = CreateMapper(fileName);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), binaryTree.root);
    }

    // Load the root node from a file
    public static void Deserialize(String fileName) throws IOException {
        ObjectMapper objectMapper = CreateMapper(fileName);
        binaryTree.root = objectMapper.readValue(new File(fileName), Node.class);
    }

    // Interpret the arguments of the main function and return the appropriate OobjectMapper
    public static ObjectMapper CreateMapper(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (fileName.contains(".json")) {
            objectMapper = new JsonMapper();
        } else if (fileName.contains(".yaml")) {
            objectMapper = new YAMLMapper();
        } else if (fileName.contains(".xml")) {
            objectMapper = new XmlMapper();
        } else {
            Game();
        }
        return objectMapper;
    }
}
