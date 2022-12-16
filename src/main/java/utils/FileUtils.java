package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileUtils {
    public static void writeToFile(String path, String value){
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
