package Bookkeeping.System;

public class ViewCommand implements Command{

    private final BookkeepingController bookkeepingController;
    ViewCommand(BookkeepingController bookkeepingController){
        this.bookkeepingController = bookkeepingController;
    }

	@Override
	public String execute() {
        System.out.println(this.bookkeepingController.getCurrent());
		return null;
	}
    
}
