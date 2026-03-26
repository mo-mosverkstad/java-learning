package Callback;
public class CallbackScenario {
    public static void playComputer(CallbackUserInterface user) {
        boolean continueFlag = true;
        String systemInfo = powerOn();

        while(continueFlag) {
            if (user == null) {
                break;
            }
            continueFlag = user.userEvent(systemInfo);
        }
        powerOff();
    }

    private static String powerOn() {
        System.out.println("Power On");
        return "This is the system information:\n CPU: 1\n Memory: 2\n Hard Disk: 3\n";
    }

    private static void powerOff() {
        System.out.println("Power Off");
    }
}
