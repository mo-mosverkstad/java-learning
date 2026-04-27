package se.ebikerepair.integration;

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
        System.out.println("\033[32m**** Printing repair order FROM PRINTER ****\033[0m");
        System.out.println(repairOrder);
    }
}
