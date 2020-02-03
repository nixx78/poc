package lv.nixx.poc.sandbox.wordfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFinder {

    private String dictionaryFileName;

    public WordFinder(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    public String longestWord(String word) {

        if (word.length() < 12)
            throw new IllegalArgumentException("Word length must be at least 12 characters");

        List<Character> wordSet = word.chars().mapToObj(t -> (char) t).collect(Collectors.toList());

        String longestWord = "";

        try (Stream<String> stream = Files.lines(Paths.get(dictionaryFileName))) {

            longestWord = stream.filter(dWord -> isBelongToCharset(dWord, wordSet))
                    .max(Comparator.comparingInt(String::length)).orElse("");


        } catch (IOException e) {
            e.printStackTrace();
        }

        return longestWord;
    }

    private boolean isBelongToCharset(String dWord, List<Character> charset) {
        Set<Character> localCharset = new HashSet<>(charset);

        boolean isBelongToCharset = true;
        for (char c : dWord.toCharArray()) {
            if (!localCharset.remove(c)) {
                isBelongToCharset = false;
                break;
            }
        }

        return isBelongToCharset;
    }

}
