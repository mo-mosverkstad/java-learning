package Bookkeeping.System;

public class AppendCommand implements Command{

    private final BookkeepingController bookkeepingController;
    AppendCommand(BookkeepingController bookkeepingController){
        this.bookkeepingController = bookkeepingController;
    }

	@Override
	public String execute() {
        
		return null;
	}

}
