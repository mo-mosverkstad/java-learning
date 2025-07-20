package Wordsort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Wordsort {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        int totalWords = 0;
        HashMap<String ,Integer> wordCountMap = new HashMap<>();
        String filePath = "./src/Wordsort/MVC-description.txt";
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                String[] words = line.split("[ \\t\\n\\r,.!?:;-]+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if (wordCountMap.containsKey(word)) {
                        wordCountMap.put(word, wordCountMap.get(word) + 1);
                    }
                    else {
                        wordCountMap.put(word, 1);
                    }
                    totalWords++;
                }
            }
        }
        catch (IOException readException){
            readException.printStackTrace();

        }finally{
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }
                catch (IOException closeException){
                    bufferedReader = null;
                }
            }
        }
        // System.out.println(wordCountMap);
        final int totalWordsFinal = totalWords;
        wordCountMap
            .entrySet()
            .stream()
            .sorted((left, right) -> Integer.compare(right.getValue(), left.getValue()))
            .forEach(element -> System.out.println(String.format("%s=%d (%3.3f%%)", element.getKey(), element.getValue(), 100.0 * element.getValue() / totalWordsFinal)));
    }
}
