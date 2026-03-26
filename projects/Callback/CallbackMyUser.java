package Callback;
public class CallbackMyUser implements CallbackUserInterface {

    @Override
    public boolean userEvent(String systemInfo) {
        System.out.println("User Event: " + systemInfo);
        System.out.println("User Event: my user event");
        return false;
    }

}
