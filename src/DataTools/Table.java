package DataTools;

import java.util.List;
import java.util.ArrayList;

public class Table {
    private final List<Pair<String, Column>> columns = new ArrayList<>();
    public Table() {}

    public void addColumn(String columnName, Column column){
        columns.add(new Pair<String, Column>(columnName, column));
    }

    public void appendRow(Object[] rowData) throws IllegalArgumentException{
        if (rowData.length != columns.size()) {
            throw new IllegalArgumentException("Row data length does not match column count");
        }
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).getRight().append(rowData[i]);
        }
    }

    @Override
    public String toString() {
        if (columns.isEmpty()) return "<Empty Table>";
        int rowCount = columns.get(0).getRight().size();
        int colCount = columns.size();

        // Calculate max width for each column (header + cell contents)
        int[] colWidths = new int[colCount];
        for (int j = 0; j < colCount; j++) {
            String header = columns.get(j).getLeft();
            colWidths[j] = header.length();
            for (int i = 0; i < rowCount; i++) {
                Object cell = columns.get(j).getRight().get(i);
                int len = (cell != null ? cell.toString() : "null").length();
                if (len > colWidths[j]) colWidths[j] = len;
            }
        }

        // Build the table string
        StringBuilder content = new StringBuilder();

        // Header
        for (int j = 0; j < colCount; j++) {
            content.append(pad(columns.get(j).getLeft(), colWidths[j])).append("  ");
        }
        content.append("\n");

        // Separator
        for (int j = 0; j < colCount; j++) {
            content.append("-".repeat(colWidths[j])).append("  ");
        }
        content.append("\n");

        // Rows
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Object cell = columns.get(j).getRight().get(i);
                content.append(pad(cell != null ? cell.toString() : "null", colWidths[j])).append("  ");
            }
            content.append("\n");
        }

        return content.toString();
    }

    private String pad(String text, int width) {
        if (text == null) text = "null";
        return String.format("%-" + width + "s", text);
    }
}
