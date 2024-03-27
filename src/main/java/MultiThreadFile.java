import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadFile {

    public static void main(String[] args) {
        String filePath = "/home/shivendrak/Documents/Remiges_WorkSpace/multi_thread/src/main/resources/multi_thread.txt";
        
        Logger logger = LoggerFactory.getLogger(MultiThreadFile.class);


        // Shared data structure to store sentences and word counts
        List<String[]> sentencesWithWordCount = new ArrayList<>();

        // Create and start thread to read and count words
        Thread readThread = new Thread(() -> {
            synchronized (sentencesWithWordCount) {
                readAndCountWords(filePath, sentencesWithWordCount);
            }
            logger.debug("thread one running");
        });
        readThread.start();

        // Create and start thread to display data
        Thread displayThread = new Thread(() -> {
            synchronized (sentencesWithWordCount) {
                displayData(sentencesWithWordCount);
                
            }
            logger.debug("thread two running");
        });
        displayThread.start();
    }

    public static void readAndCountWords(String filePath, List<String[]> sentencesWithWordCount) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                sentencesWithWordCount.add(new String[]{line, String.valueOf(words.length)});
            }
             } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayData(List<String[]> sentencesWithWordCount) {
        for (String[] sentence : sentencesWithWordCount) {
            System.out.println("Sentence: " + sentence[0] + ", Word Count: " + sentence[1]);
        }
    }

}
