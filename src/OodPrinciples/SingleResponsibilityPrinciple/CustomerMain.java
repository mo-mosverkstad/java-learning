package OodPrinciples.SingleResponsibilityPrinciple;

public class CustomerMain {

    public static void main(String[] args) {
        Customer customer = new Customer("John", "Doe", "123 Main Street", "555-555-5555");
        CustomerPrinting.printDetail(customer);
    }
}
