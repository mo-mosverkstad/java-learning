package se.ebikerepair.printer;

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
        System.out.println("**** Printing repair order ****");
        System.out.println(repairOrder);
    }
}
