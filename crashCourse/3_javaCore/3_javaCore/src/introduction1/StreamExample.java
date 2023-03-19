package introduction1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StreamExample {
    public static void main(String[] args) throws IOException {
        var contents = Files.readString(Paths.get("index.txt"));
        System.out.println(contents.toString());
    }
}
