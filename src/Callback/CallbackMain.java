package Callback;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CallbackMain {

    private static final String[] users = {"CallbackMyUser"};

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        play(registerCallbackObject(users));
    }

    public static void play(List<CallbackUserInterface> userObjs) {
        for (var obj : userObjs) {
            CallbackScenario.playComputer(obj);
        }

    }

    public static List<CallbackUserInterface> registerCallbackObject(String[] users) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        List<CallbackUserInterface> userObjs = new ArrayList<>();
        for (var user : users) {
            CallbackUserInterface obj = (CallbackUserInterface) Class.forName(user).getDeclaredConstructor().newInstance();
            userObjs.add(obj);
        }
        return userObjs;
    }
}
