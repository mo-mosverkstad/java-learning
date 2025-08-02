package Bookkeeping.Tables;

import java.util.ArrayList;
import java.util.List;

public class OrderedFlatTable implements TableInterface{
    private static final char NEWLINE_CHARACTER = '\n';

    private static final String NULL_KEYWORD = "null";
    private static final String SPACING_STRING = "  ";
    private static final String SEPARATOR_STRING = "-";
    private static final String EMPTY_TABLE_DISPLAY_FORMAT = "<Empty Table(%s)>";

    private static final String INVALID_ROW_DATA_ERROR = "Row data is not valid for this table";
    private static final String NEGATIVE_ROW_INDEX_ERROR = "Row index cannot be negative";

    private String name;
    private final List<List<Object>> rows = new ArrayList<>();
    private final List<CollationEntry> collation = new ArrayList<>();
    public OrderedFlatTable(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int rowSize(){
        if (rows.isEmpty()) return 0;
        return rows.size();
    }

    public int columnSize(){
        if (collation.isEmpty()) return 0;
        return collation.size();
    }

    public void addColumn(CollationEntry collationEntry){
        collation.add(collationEntry);
    }

    public List<CollationEntry> getCollation(){
        return collation;
    }

    public boolean isValidRow(List<Object> rowData){
        if (rowData.size() != columnSize()) return false;
        for (int i = 0; i < columnSize(); i++){
            if (!collation.get(i).validate(rowData.get(i))) return false;
        }
        return true;
    }

    public void appendRow(List<Object> rowData) throws IllegalArgumentException{
        if (!isValidRow(rowData)) throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);
        rows.add(rowData);
    }

    public void updateRow(int index, List<Object> rowData) throws IllegalArgumentException {
        if (index < 0) throw new IllegalArgumentException(NEGATIVE_ROW_INDEX_ERROR);
        if (!isValidRow(rowData)) throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);

        while (index >= rows.size()) {
            // Fill with nulls to match column size
            List<Object> emptyRow = new ArrayList<>();
            for (int i = 0; i < columnSize(); i++) {
                emptyRow.add(null);
            }
            rows.add(emptyRow);
        }

        rows.set(index, rowData);
    }


    @Override
    public String toString() {
        if (rows.isEmpty()) {
            return String.format(EMPTY_TABLE_DISPLAY_FORMAT, name);
        }

        int rowCount = rowSize();
        int colCount = columnSize();

        int[] colWidths = new int[colCount];
        for (int j = 0; j < colCount; j++) {
            String header = collation.get(j).name();
            colWidths[j] = header.length();
            for (int i = 0; i < rowCount; i++) {
                Object cell = rows.get(i).get(j);
                int len = (cell != null ? cell.toString() : NULL_KEYWORD).length();
                if (len > colWidths[j]) {
                    colWidths[j] = len;
                }
            }
        }

        StringBuilder content = new StringBuilder();
        content.append("----- Table(").append(name).append(") -----\n");

        // Header
        content.append(pad("Row", colWidths[0]));
        for (int j = 0; j < colCount; j++) {
            content.append(pad(collation.get(j).name(), colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Separator
        content.append(pad("----", colWidths[0]));
        for (int j = 0; j < colCount; j++) {
            content.append(SEPARATOR_STRING.repeat(colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Rows
        for (int i = 0; i < rowCount; i++) {
            content.append(pad(String.valueOf(i + 1), colWidths[0]));
            for (int j = 0; j < colCount; j++) {
                Object cell = rows.get(i).get(j);
                content.append(pad(cell != null ? cell.toString() : NULL_KEYWORD, colWidths[j])).append(SPACING_STRING);
            }
            content.append(NEWLINE_CHARACTER);
        }

        return content.toString();
    }


    private String pad(String text, int width) {
        if (text == null) text = NULL_KEYWORD;
        return String.format("%-" + width + "s", text);
    }
}
