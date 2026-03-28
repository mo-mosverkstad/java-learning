package se.ebikerepair.printer;

import se.ebikerepair.model.RepairOrder;

public class Printer {
    public void print(RepairOrder repairOrder){
        System.out.println("**** Printing repair order ****");
        System.out.println(repairOrder);
    }
}
