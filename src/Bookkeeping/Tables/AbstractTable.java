package Bookkeeping.Tables;

import java.util.ArrayList;
import java.util.List;

import Bookkeeping.Element.Element;

public abstract class AbstractTable extends Element {
    private static final char NEWLINE_CHARACTER = '\n';

    private static final String NULL_KEYWORD = "null";
    private static final String SPACING_STRING = "  ";
    private static final String SEPARATOR_STRING = "-";
    private static final String EMPTY_TABLE_DISPLAY_FORMAT = "<Empty Table(%s)>";

    public static final String INVALID_ROW_DATA_ERROR = "Row data is not valid for this table";

    protected final List<CollationEntry> collation = new ArrayList<>();

    protected AbstractTable(String name){
        super(name);
    }

    abstract protected List<List<Object>> getRows();

    public final int rowSize(){
        if (getRows().isEmpty()) return 0;
        return getRows().size();
    }

    public final int columnSize(){
        if (collation.isEmpty()) return 0;
        return collation.size();
    }

    public final void addColumn(CollationEntry collationEntry){
        collation.add(collationEntry);
    }

    public final List<CollationEntry> getCollation(){
        return collation;
    }

    public final boolean isValidRow(List<Object> rowData){
        if (rowData.size() != columnSize()) return false;
        for (int i = 0; i < columnSize(); i++){
            if (!collation.get(i).validate(rowData.get(i))) return false;
        }
        return true;
    }

    public final void appendRow(List<Object> rowData) throws IllegalArgumentException{
        if (!isValidRow(rowData)) throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);
        getRows().add(rowData);
    }

    abstract public void updateRow(int index, List<Object> rowData) throws IllegalArgumentException;

    @Override
    public final String toString() {
        if (getRows().isEmpty()) {
            return String.format(EMPTY_TABLE_DISPLAY_FORMAT, name);
        }

        int rowCount = rowSize();
        int colCount = columnSize();

        int[] colWidths = new int[colCount];
        for (int j = 0; j < colCount; j++) {
            String header = collation.get(j).name();
            colWidths[j] = header.length();
            for (int i = 0; i < rowCount; i++) {
                Object cell = getRows().get(i).get(j);
                int len = (cell != null ? cell.toString() : NULL_KEYWORD).length();
                if (len > colWidths[j]) {
                    colWidths[j] = len;
                }
            }
        }

        StringBuilder content = new StringBuilder();
        content.append("----- Table(").append(name).append(") -----\n");

        // Header
        content.append(pad("Row index", 10));
        for (int j = 0; j < colCount; j++) {
            content.append(pad(collation.get(j).name(), colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Separator
        content.append(pad("---------", 10));
        for (int j = 0; j < colCount; j++) {
            content.append(SEPARATOR_STRING.repeat(colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Rows
        for (int i = 0; i < rowCount; i++) {
            content.append(pad(String.valueOf(i), 10));
            for (int j = 0; j < colCount; j++) {
                Object cell = getRows().get(i).get(j);
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
