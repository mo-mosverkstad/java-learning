package Exception;
public class ExceptionTest {

    public static void main(String[] args) {

        try {
            String result = doSomething(true);
            result = doSomething2(false);
            System.out.println(result);
        } catch (ExceptionMyException | NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Finally");
        }
        
    }

    private static String doSomething(boolean flag) throws ExceptionMyException {
        if (flag) {
            return "Hello World!";
        }
        ExceptionMyException e = new ExceptionMyException("It is my exception.");
        throw e;
    }

    private static String doSomething2(boolean flag) throws NullPointerException  {
        if (flag) {
            return "Hello World!";
        }
        throw new NullPointerException("It is my null exception.");
    }
}
