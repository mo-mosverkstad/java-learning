package OodPrinciples.SingleResponsibilityPrinciple;

public class CustomerPrinting {

    public static void printDetail(Customer customer) {
        System.out.println("========= CUSTOMER ==================");
        System.out.println(customer);
        System.out.println("=====================================");
    }
}
