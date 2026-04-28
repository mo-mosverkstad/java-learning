package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.RepairOrder;

/**
 * Prints repair orders to standard output.
 */
public class Printer {
    /**
     * Prints a repair order to standard output.
     *
     * @param repairOrder the repair order to print
     */
    public void print(RepairOrder repairOrder){
        System.out.println(String.format("%s**** Printing repair order FROM PRINTER ****%s", PrintoutFormat.ANSI_GREEN, PrintoutFormat.ANSI_RESET));
        System.out.println(repairOrder);
    }
}
