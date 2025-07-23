package DataTools.DataToolsCLI;

import DataTools.Tables.Table;

public final class ViewCommand implements CommandInterface{
    private static final String NO_ARGUMENTS_ACCEPTED_PROBLEM_FORMAT = "PROBLEM: No arguments are accepted to view command! Number of arguments: %d";

    private final Table table;
    public ViewCommand(Table table){
        this.table = table;
    }

    @Override
    public void execute(String[] arguments){
        if (arguments.length != 0) {
            System.out.println(String.format(NO_ARGUMENTS_ACCEPTED_PROBLEM_FORMAT, arguments.length));
            return;
        }
        System.out.println(table);
    }
}
