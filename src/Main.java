import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

class FileReaderThread extends Thread {
    private static final Logger logger = Logger.getLogger(FileReaderThread.class.getName());
    private String fileName;
    private BlockingQueue<String> queue;

    public FileReaderThread(String fileName, BlockingQueue<String> queue) {
        this.fileName = fileName;
        this.queue = queue;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                synchronized (queue) {
                    queue.put(line); // Put each line into the queue
                    logger.log(Level.INFO, "Read line: " + line);
                    queue.notify(); // Notify the processing thread
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
}

class LineProcessorThread extends Thread {
    private static final Logger logger = Logger.getLogger(LineProcessorThread.class.getName());
    private BlockingQueue<String> queue;

    public LineProcessorThread(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        queue.wait(2000); // Wait until there is data in the queue
                    }
                    String line = queue.take(); // Take a line from the queue
                    // Process the line (count words)
                    int wordCount = line.split("\\s+").length;
                    logger.log(Level.INFO, "Line: " + line + " | Word Count: " + wordCount);
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Thread interrupted", e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        FileReaderThread readerThread = new FileReaderThread("/home/shivendrak/Documents/Remiges_WorkSpace/dnkn/multi_thread/src/input.txt", queue);
        LineProcessorThread processorThread = new LineProcessorThread(queue);

        readerThread.start();
        processorThread.start();
    }
}