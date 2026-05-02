package se.ebikerepair.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


/**
 * Singleton logger that writes exception information to a log file.
 * Uses eager initialization to ensure a single instance throughout the application.
 */
public class LogHandler {
    private static final String LOG_FILE_NAME = "ebikerepair.log";
    private static final LogHandler INSTANCE = new LogHandler();
    private PrintWriter logFile;

    /**
     * Returns the singleton LogHandler instance.
     *
     * @return the single LogHandler instance
     */
    public static LogHandler getLogger() {
        return INSTANCE;
    }

    /**
     * Creates the LogHandler and opens the log file for appending.
     */
    private LogHandler() {
        try {
            logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
        } catch (IOException ex) {
            System.out.println("Could not create logger.");
            ex.printStackTrace();
        }
    }

    /**
     * Writes a log entry describing a thrown exception.
     * 
     * @param exception The exception that shall be logged.
     */
    public void logException(Exception exception) {
        StringBuilder logMsgBuilder = new StringBuilder();
        logMsgBuilder.append(createTime());
        logMsgBuilder.append(", Exception was thrown: ");
        logMsgBuilder.append(exception.getMessage());
        logFile.println(logMsgBuilder);
        exception.printStackTrace(logFile);
        logFile.println("\n");
    }

    /**
     * Returns the current date and time formatted as a medium-style localized string.
     *
     * @return the formatted timestamp
     */
    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}