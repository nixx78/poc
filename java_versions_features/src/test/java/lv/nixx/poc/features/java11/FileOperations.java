package lv.nixx.poc.features.java11;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperations {

    @Test
    public void sample() throws Exception {

        Path path = Files.writeString(Files.createFile(Path.of("./test.txt")), "text in file");

//        Path path = Files.writeString(Files.createTempFile("test", ".txt"), "text in file");
        System.out.println(path);
        String s = Files.readString(path);
        System.out.println(s);
    }
}
