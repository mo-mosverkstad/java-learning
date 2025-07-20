package OodPrinciples.SingleResponsibilityPrinciple;

public record Customer(String firstName, String lastName, String address, String phoneNumber) {

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", firstName, lastName, address, phoneNumber);
    }

}
