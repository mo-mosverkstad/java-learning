package se.ebikerepair.constant;

/**
 * Constants class containing format strings for printout of various entities.
 * This class cannot be instantiated.
 */
public final class PrintoutFormat {

    private static final int MAX_LINE_WIDTH = 80;

    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String ANSI_BLUE = "\033[34m";

    private PrintoutFormat() {}

    /**
     * Wraps text to fit within maxWidth, prefixing continuation lines with the given indent.
     *
     * @param text the text to wrap
     * @param indent number of leading spaces for continuation lines
     * @return the wrapped text
     */
    public static String wrapText(String text, int indent) {
        int contentWidth = MAX_LINE_WIDTH - indent;
        if (text.length() <= contentWidth) {
            return text;
        }
        String pad = " ".repeat(indent);
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + contentWidth, text.length());
            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }
            if (start > 0) {
                sb.append(System.lineSeparator()).append(pad);
            }
            sb.append(text, start, end);
            start = end;
            if (start < text.length() && text.charAt(start) == ' ') {
                start++;
            }
        }
        return sb.toString();
    }

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

    public static final String CUSTOMER_INLINE_PRINTOUT_FORMAT =
            "  - Name:      %s%n" +
            "  - Phone:     %s%n" +
            "  - Email:     %s%n" +
            "  - Bikes:%n%s";

    public static final String PROBLEM_PRINTOUT_FORMAT =
            "    - Description: %s%n" +
            "    - Broken bike: %s%n";

    public static final String DIAGNOSTIC_TASK_PRINTOUT_FORMAT =
            "      --------------------------------%n" +
            "      [%s] %s%n" +
            "            %s%n" +
            "      [%s] Cost: %s | Est. days: %d | Result: %s%n";

    public static final String DIAGNOSTIC_REPORT_PRINTOUT_FORMAT =
            "    Days: %s%n" +
            "    Cost: %s%n" +
            "    Description: %s%n%s";

    public static final String REPAIR_TASK_COLLECTION_PRINTOUT_FORMAT =
            "    Days: %s%n" +
            "    Cost: %s%n%s";

    public static final String PROPOSED_REPAIR_TASK_PRINTOUT_FORMAT =
            "    --------------------------------%n" +
            "    [%s] %s%n" +
            "        %s%n" +
            "         Cost: %s | Est. days: %d%n";

    public static final String REPAIR_ORDER_PRINTOUT_FORMAT =
            "=================================%n" +
            "  Repair Order%n" +
            "=================================%n" +
            "  Order ID:       %s%n" +
            "  Status:         %s%n" +
            "  Created:        %s%n" +
            "  Est. Complete:  %s%n" +
            "  -- Costs --%n" +
            "  %s%n" +
            "  Total Cost:     %s%n" +
            "********************************%n" +
            "  Customer:%n%s" +
            "********************************%n" +
            "  Problem:%n%s" +
            "********************************%n" +
            "  Diagnostic Report:%n%s" +
            "********************************%n" +
            "  Proposed Repair Tasks:%n%s" +
            "=================================";
}
