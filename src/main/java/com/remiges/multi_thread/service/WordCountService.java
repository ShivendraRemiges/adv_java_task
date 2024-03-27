package com.remiges.multi_thread.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCountService {

    //  public void countWords(BlockingQueue<String> queue) {
    //     int wordCount = 0;
    //     try {
    //         while (true) {
    //             String line = queue.take();
    //             if (line.equals("EOF")) {
    //                 break;
    //             }
    //             String[] words = line.split("\\s+");
    //             wordCount += words.length;
    //         }
    //         System.out.println("Total number of words: " + wordCount);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

     @Autowired
    private FileReaderService fileReaderService;

    public void displayData(String filePath) {
        List<String[]> sentencesWithWordCount = fileReaderService.readAndCountWords(filePath);

        for (String[] sentence : sentencesWithWordCount) {
            System.out.println("Sentence: " + sentence[0] + ", Word Count: " + sentence[1]);
        }
    }

}
