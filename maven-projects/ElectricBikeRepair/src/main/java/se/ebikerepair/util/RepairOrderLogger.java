package se.ebikerepair.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.model.AbstractRepairOrderObserver;

public class RepairOrderLogger extends AbstractRepairOrderObserver {
    private static final String LOG_FILE_NAME = "ebikerepair-repairorder.log";
    private PrintWriter logFile;

    public RepairOrderLogger(){
        try {
            logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
        } catch (IOException ex) {
            System.out.println("Could not create logger.");
            ex.printStackTrace();
        }
    }

    @Override
    protected void doHandleRepairOrderUpdate() throws Exception {
        RepairOrderDTO repairOrderDTO = getRepairOrderDTO();
        logFile.println(createTime());
        logFile.println(repairOrderDTO);
        logFile.println();
    }

    @Override
    protected void handleErrors(Exception e) {
        System.out.println("Failed to log repair order: " + e.getMessage());
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
