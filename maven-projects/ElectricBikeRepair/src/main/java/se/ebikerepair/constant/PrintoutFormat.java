package se.ebikerepair.constant;

public final class PrintoutFormat {

    private PrintoutFormat() {}

    public static final String BIKE_PRINTOUT_FORMAT =
            "    - Brand: %s, Model: %s (S/N: %s)%n";

    public static final String BIKE_PRINTOUT_EMPTY =
            "    (none)\n";

    public static final String CUSTOMER_PRINTOUT_FORMAT =
            "=================================%n" +
            "  Customer Information%n" +
            "=================================%n" +
            "  Name:      %s%n" +
            "  Phone:     %s%n" +
            "  Email:     %s%n" +
            "  Bikes:%n%s" +
            "=================================";

    public static final String PROBLEM_PRINTOUT_FORMAT =
            "    - Description: %s (broken bike: %s)%n";

    public static final String REPAIR_ORDER_PRINTOUT_FORMAT =
            "=================================%n" +
            "  Repair Order%n" +
            "=================================%n" +
            "  Order ID:       %s%n" +
            "  Status:         %s%n" +
            "  Created:        %s%n" +
            "  Est. Complete:  %s%n" +
            "  Total Cost:     %s%n" +
            "  Customer:       %s%n" +
            "  Problem:%n%s" +
            "=================================";
}
