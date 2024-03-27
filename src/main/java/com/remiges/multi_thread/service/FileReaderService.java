package com.remiges.multi_thread.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Service;

@Service
public class FileReaderService {
    

    // public void readLines(String filePath, BlockingQueue<String> queue) {
    //     try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             queue.put(line);
    //         }
    //         // Add a sentinel value to indicate end of file
    //         queue.put("EOF");
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

     public List<String[]> readAndCountWords(String filePath) {
        List<String[]> sentencesWithWordCount = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                sentencesWithWordCount.add(new String[]{line, String.valueOf(words.length)});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sentencesWithWordCount;
    }

}
