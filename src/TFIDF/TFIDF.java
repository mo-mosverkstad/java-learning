package TFIDF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TFIDF {
    public static void main(String[] args) {
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // String filePath = "./Test01/src/Wordsort/words.txt";

        String[] filePaths = {
            "./Test01/src/Wordsort/boat-types.txt", 
            "./Test01/src/Wordsort/MVC-description.txt",
            "./Test01/src/Wordsort/religionvsspirituality.txt",
            "./Test01/src/Wordsort/thelastofus.txt",
            "./Test01/src/Wordsort/strengthenmuscletendons.txt"
        };

        int documentCount = filePaths.length;

        List<HashMap<String, Triple<Integer, Double, Double>>> TFMaps = new LinkedList<>();
        HashMap<String, Pair<Integer, Double>> IDFMap = new HashMap<>();

        for (String filePath : filePaths) {
            int totalWords = 0;
            HashMap<String, Triple<Integer, Double, Double>> TFMap = new HashMap<>();
            BufferedReader bufferedReader = null;
            try{
                bufferedReader = new BufferedReader(new FileReader(filePath));
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    String[] words = line.split("[ \\t\\n\\r,.!?:;-]+");
                    for (String word : words) {
                        word = word.toLowerCase();
                        if (TFMap.containsKey(word)) {
                            Triple<Integer, Double, Double> triple = TFMap.get(word);
                            triple.setLeft(triple.getLeft() + 1);
                        }
                        else {
                            TFMap.put(word, new Triple<Integer, Double, Double>(1, 0.0, 0.0));
                            if (IDFMap.containsKey(word)) {
                                Pair<Integer, Double> pair = IDFMap.get(word);
                                pair.setLeft(pair.getLeft() + 1);
                            }
                            else {
                                IDFMap.put(word, new Pair<>(1, 0.0));
                            }
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
            for (HashMap.Entry<String, Triple<Integer, Double, Double>> entry : TFMap.entrySet()) {
                Triple<Integer, Double, Double> triple = entry.getValue();
                triple.setMiddle((double) triple.getLeft() / totalWords);
            }
            TFMaps.add(TFMap);
        }

        for (HashMap.Entry<String, Pair<Integer, Double>> entry : IDFMap.entrySet()) {
            Pair<Integer, Double> pair = entry.getValue();
            pair.setRight(Math.log((double) documentCount / pair.getLeft()));
        }

        for (HashMap<String, Triple<Integer, Double, Double>> wordCountMap : TFMaps) {
            for (HashMap.Entry<String, Triple<Integer, Double, Double>> entry : wordCountMap.entrySet()) {
                String word = entry.getKey();
                Triple<Integer, Double, Double> triple = entry.getValue();
                triple.setRight(IDFMap.get(word).getRight() * triple.getMiddle());
            }
        }
        

        System.out.println("TFMaps: ");
        for (HashMap<String, Triple<Integer, Double, Double>> wordCountMap : TFMaps) {
            System.out.println("---");
            wordCountMap
                .entrySet()
                .stream()
                .sorted((left, right) -> Double.compare(right.getValue().getRight(), left.getValue().getRight()))
                .limit(10)
                .forEach(System.out::println);
        }

        /* 
        System.out.println("\nIDFMaps: ");
        IDFMap
            .entrySet()
            .stream()
            .sorted((left, right) -> Integer.compare(right.getValue().getLeft(), left.getValue().getLeft()))
            .forEach(System.out::println);
        */
    }
}
