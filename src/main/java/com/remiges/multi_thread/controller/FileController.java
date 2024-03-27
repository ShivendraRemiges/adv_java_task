package com.remiges.multi_thread.controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.multi_thread.service.FileReaderService;
import com.remiges.multi_thread.service.WordCountService;

@RestController
public class FileController {

    @Autowired
    private FileReaderService fileReaderService;

    @Autowired
    private WordCountService wordCountingService;

    // @GetMapping("/processFile")
    // public void processFile() {
    //     String filePath = "/home/shivendrak/Documents/Remiges_WorkSpace/multi_thread/src/main/resources/multi_thread.txt";
    //     int queueCapacity = 10;
    //     ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(queueCapacity);

    //     ExecutorService executor = Executors.newFixedThreadPool(2);

    //     executor.submit(() -> fileReaderService.readLines(filePath, queue));
    //     executor.submit(() -> wordCountingService.countWords(queue));

    //     executor.shutdown();
    //     try {
    //         executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    @GetMapping("/displayFileData")
    public void displayFileData() {
        String filePath = "/home/shivendrak/Documents/Remiges_WorkSpace/multi_thread/src/main/resources/multi_thread.txt";

        // Create a thread to display data
        new Thread(() -> wordCountingService.displayData(filePath)).start();
    }
}
