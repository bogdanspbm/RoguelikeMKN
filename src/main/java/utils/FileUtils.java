package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {
    public static void writeToFile(String path, String value) {
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromFile(String path) {
        try {
            String res = "";
            List<String> lines = Files.readAllLines(new File(path).toPath());
            for (String line : lines) {
                res += line;
            }
            return res;
        } catch (Exception e) {
            return "";
        }
    }
}
